/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumgedu.gt.db;

import miumg.edu.gt.gestor_de_inventarios.entity.Venta;
import miumg.edu.gt.gestor_de_inventarios.entity.Usuario;
import miumg.edu.gt.gestor_de_inventarios.entity.Factura;
import miumg.edu.gt.gestor_de_inventarios.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import miumgedu.gt.db.exceptions.IllegalOrphanException;
import miumgedu.gt.db.exceptions.NonexistentEntityException;

/**
 *
 * @author danyt
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) {
        if (venta.getFacturaList() == null) {
            venta.setFacturaList(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idcliente = venta.getIdcliente();
            if (idcliente != null) {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getIdcliente());
                venta.setIdcliente(idcliente);
            }
            Usuario idusuario = venta.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdusuario());
                venta.setIdusuario(idusuario);
            }
            List<Factura> attachedFacturaList = new ArrayList<Factura>();
            for (Factura facturaListFacturaToAttach : venta.getFacturaList()) {
                facturaListFacturaToAttach = em.getReference(facturaListFacturaToAttach.getClass(), facturaListFacturaToAttach.getIdfactura());
                attachedFacturaList.add(facturaListFacturaToAttach);
            }
            venta.setFacturaList(attachedFacturaList);
            em.persist(venta);
            if (idcliente != null) {
                idcliente.getVentaList().add(venta);
                idcliente = em.merge(idcliente);
            }
            if (idusuario != null) {
                idusuario.getVentaList().add(venta);
                idusuario = em.merge(idusuario);
            }
            for (Factura facturaListFactura : venta.getFacturaList()) {
                Venta oldIdventaOfFacturaListFactura = facturaListFactura.getIdventa();
                facturaListFactura.setIdventa(venta);
                facturaListFactura = em.merge(facturaListFactura);
                if (oldIdventaOfFacturaListFactura != null) {
                    oldIdventaOfFacturaListFactura.getFacturaList().remove(facturaListFactura);
                    oldIdventaOfFacturaListFactura = em.merge(oldIdventaOfFacturaListFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta persistentVenta = em.find(Venta.class, venta.getIdventa());
            Cliente idclienteOld = persistentVenta.getIdcliente();
            Cliente idclienteNew = venta.getIdcliente();
            Usuario idusuarioOld = persistentVenta.getIdusuario();
            Usuario idusuarioNew = venta.getIdusuario();
            List<Factura> facturaListOld = persistentVenta.getFacturaList();
            List<Factura> facturaListNew = venta.getFacturaList();
            List<String> illegalOrphanMessages = null;
            for (Factura facturaListOldFactura : facturaListOld) {
                if (!facturaListNew.contains(facturaListOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaListOldFactura + " since its idventa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idclienteNew != null) {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getIdcliente());
                venta.setIdcliente(idclienteNew);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
                venta.setIdusuario(idusuarioNew);
            }
            List<Factura> attachedFacturaListNew = new ArrayList<Factura>();
            for (Factura facturaListNewFacturaToAttach : facturaListNew) {
                facturaListNewFacturaToAttach = em.getReference(facturaListNewFacturaToAttach.getClass(), facturaListNewFacturaToAttach.getIdfactura());
                attachedFacturaListNew.add(facturaListNewFacturaToAttach);
            }
            facturaListNew = attachedFacturaListNew;
            venta.setFacturaList(facturaListNew);
            venta = em.merge(venta);
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
                idclienteOld.getVentaList().remove(venta);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
                idclienteNew.getVentaList().add(venta);
                idclienteNew = em.merge(idclienteNew);
            }
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getVentaList().remove(venta);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getVentaList().add(venta);
                idusuarioNew = em.merge(idusuarioNew);
            }
            for (Factura facturaListNewFactura : facturaListNew) {
                if (!facturaListOld.contains(facturaListNewFactura)) {
                    Venta oldIdventaOfFacturaListNewFactura = facturaListNewFactura.getIdventa();
                    facturaListNewFactura.setIdventa(venta);
                    facturaListNewFactura = em.merge(facturaListNewFactura);
                    if (oldIdventaOfFacturaListNewFactura != null && !oldIdventaOfFacturaListNewFactura.equals(venta)) {
                        oldIdventaOfFacturaListNewFactura.getFacturaList().remove(facturaListNewFactura);
                        oldIdventaOfFacturaListNewFactura = em.merge(oldIdventaOfFacturaListNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venta.getIdventa();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getIdventa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Factura> facturaListOrphanCheck = venta.getFacturaList();
            for (Factura facturaListOrphanCheckFactura : facturaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Venta (" + venta + ") cannot be destroyed since the Factura " + facturaListOrphanCheckFactura + " in its facturaList field has a non-nullable idventa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente idcliente = venta.getIdcliente();
            if (idcliente != null) {
                idcliente.getVentaList().remove(venta);
                idcliente = em.merge(idcliente);
            }
            Usuario idusuario = venta.getIdusuario();
            if (idusuario != null) {
                idusuario.getVentaList().remove(venta);
                idusuario = em.merge(idusuario);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Venta findVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
