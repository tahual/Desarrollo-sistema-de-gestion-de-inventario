/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumgedu.gt.db;

import miumg.edu.gt.gestor_de_inventarios.entity.Usuario;
import miumg.edu.gt.gestor_de_inventarios.entity.Producto;
import miumg.edu.gt.gestor_de_inventarios.entity.Ordencompra;
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
public class OrdencompraJpaController implements Serializable {

    public OrdencompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ordencompra ordencompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idproducto = ordencompra.getIdproducto();
            if (idproducto != null) {
                idproducto = em.getReference(idproducto.getClass(), idproducto.getIdproducto());
                ordencompra.setIdproducto(idproducto);
            }
            Usuario idusuario = ordencompra.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdusuario());
                ordencompra.setIdusuario(idusuario);
            }
            em.persist(ordencompra);
            if (idproducto != null) {
                idproducto.getOrdencompraList().add(ordencompra);
                idproducto = em.merge(idproducto);
            }
            if (idusuario != null) {
                idusuario.getOrdencompraList().add(ordencompra);
                idusuario = em.merge(idusuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ordencompra ordencompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordencompra persistentOrdencompra = em.find(Ordencompra.class, ordencompra.getIdordencompra());
            Producto idproductoOld = persistentOrdencompra.getIdproducto();
            Producto idproductoNew = ordencompra.getIdproducto();
            Usuario idusuarioOld = persistentOrdencompra.getIdusuario();
            Usuario idusuarioNew = ordencompra.getIdusuario();
            if (idproductoNew != null) {
                idproductoNew = em.getReference(idproductoNew.getClass(), idproductoNew.getIdproducto());
                ordencompra.setIdproducto(idproductoNew);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
                ordencompra.setIdusuario(idusuarioNew);
            }
            ordencompra = em.merge(ordencompra);
            if (idproductoOld != null && !idproductoOld.equals(idproductoNew)) {
                idproductoOld.getOrdencompraList().remove(ordencompra);
                idproductoOld = em.merge(idproductoOld);
            }
            if (idproductoNew != null && !idproductoNew.equals(idproductoOld)) {
                idproductoNew.getOrdencompraList().add(ordencompra);
                idproductoNew = em.merge(idproductoNew);
            }
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getOrdencompraList().remove(ordencompra);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getOrdencompraList().add(ordencompra);
                idusuarioNew = em.merge(idusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordencompra.getIdordencompra();
                if (findOrdencompra(id) == null) {
                    throw new NonexistentEntityException("The ordencompra with id " + id + " no longer exists.");
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
            Ordencompra ordencompra;
            try {
                ordencompra = em.getReference(Ordencompra.class, id);
                ordencompra.getIdordencompra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordencompra with id " + id + " no longer exists.", enfe);
            }
            Producto idproducto = ordencompra.getIdproducto();
            if (idproducto != null) {
                idproducto.getOrdencompraList().remove(ordencompra);
                idproducto = em.merge(idproducto);
            }
            Usuario idusuario = ordencompra.getIdusuario();
            if (idusuario != null) {
                idusuario.getOrdencompraList().remove(ordencompra);
                idusuario = em.merge(idusuario);
            }
            em.remove(ordencompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ordencompra> findOrdencompraEntities() {
        return findOrdencompraEntities(true, -1, -1);
    }

    public List<Ordencompra> findOrdencompraEntities(int maxResults, int firstResult) {
        return findOrdencompraEntities(false, maxResults, firstResult);
    }

    private List<Ordencompra> findOrdencompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ordencompra.class));
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

    public Ordencompra findOrdencompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ordencompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdencompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordencompra> rt = cq.from(Ordencompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
