/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumgedu.gt.db;

import miumg.edu.gt.gestor_de_inventarios.entity.Venta;
import miumg.edu.gt.gestor_de_inventarios.entity.Producto;
import miumg.edu.gt.gestor_de_inventarios.entity.Factura;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import miumgedu.gt.db.exceptions.NonexistentEntityException;

/**
 *
 * @author danyt
 */
public class FacturaJpaController implements Serializable {

    public FacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idproducto = factura.getIdproducto();
            if (idproducto != null) {
                idproducto = em.getReference(idproducto.getClass(), idproducto.getIdproducto());
                factura.setIdproducto(idproducto);
            }
            Venta idventa = factura.getIdventa();
            if (idventa != null) {
                idventa = em.getReference(idventa.getClass(), idventa.getIdventa());
                factura.setIdventa(idventa);
            }
            em.persist(factura);
            if (idproducto != null) {
                idproducto.getFacturaList().add(factura);
                idproducto = em.merge(idproducto);
            }
            if (idventa != null) {
                idventa.getFacturaList().add(factura);
                idventa = em.merge(idventa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getIdfactura());
            Producto idproductoOld = persistentFactura.getIdproducto();
            Producto idproductoNew = factura.getIdproducto();
            Venta idventaOld = persistentFactura.getIdventa();
            Venta idventaNew = factura.getIdventa();
            if (idproductoNew != null) {
                idproductoNew = em.getReference(idproductoNew.getClass(), idproductoNew.getIdproducto());
                factura.setIdproducto(idproductoNew);
            }
            if (idventaNew != null) {
                idventaNew = em.getReference(idventaNew.getClass(), idventaNew.getIdventa());
                factura.setIdventa(idventaNew);
            }
            factura = em.merge(factura);
            if (idproductoOld != null && !idproductoOld.equals(idproductoNew)) {
                idproductoOld.getFacturaList().remove(factura);
                idproductoOld = em.merge(idproductoOld);
            }
            if (idproductoNew != null && !idproductoNew.equals(idproductoOld)) {
                idproductoNew.getFacturaList().add(factura);
                idproductoNew = em.merge(idproductoNew);
            }
            if (idventaOld != null && !idventaOld.equals(idventaNew)) {
                idventaOld.getFacturaList().remove(factura);
                idventaOld = em.merge(idventaOld);
            }
            if (idventaNew != null && !idventaNew.equals(idventaOld)) {
                idventaNew.getFacturaList().add(factura);
                idventaNew = em.merge(idventaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factura.getIdfactura();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getIdfactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            Producto idproducto = factura.getIdproducto();
            if (idproducto != null) {
                idproducto.getFacturaList().remove(factura);
                idproducto = em.merge(idproducto);
            }
            Venta idventa = factura.getIdventa();
            if (idventa != null) {
                idventa.getFacturaList().remove(factura);
                idventa = em.merge(idventa);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
