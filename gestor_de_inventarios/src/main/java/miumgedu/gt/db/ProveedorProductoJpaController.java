/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumgedu.gt.db;

import miumg.edu.gt.gestor_de_inventarios.entity.ProveedorProducto;
import miumg.edu.gt.gestor_de_inventarios.entity.Proveedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import miumgedu.gt.db.exceptions.NonexistentEntityException;
import miumgedu.gt.db.exceptions.PreexistingEntityException;

/**
 *
 * @author danyt
 */
public class ProveedorProductoJpaController implements Serializable {

    public ProveedorProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProveedorProducto proveedorProducto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(proveedorProducto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProveedorProducto(proveedorProducto.getProveedor()) != null) {
                throw new PreexistingEntityException("ProveedorProducto " + proveedorProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProveedorProducto proveedorProducto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            proveedorProducto = em.merge(proveedorProducto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Proveedor id = proveedorProducto.getProveedor();
                if (findProveedorProducto(id) == null) {
                    throw new NonexistentEntityException("The proveedorProducto with id " + id + " no longer exists.");
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
            ProveedorProducto proveedorProducto;
            try {
                proveedorProducto = em.getReference(ProveedorProducto.class, id);
                proveedorProducto.getProveedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedorProducto with id " + id + " no longer exists.", enfe);
            }
            em.remove(proveedorProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Proveedor id) throws NonexistentEntityException {
        destroy(id.getIdproveedor());
    }

    public List<ProveedorProducto> findProveedorProductoEntities() {
        return findProveedorProductoEntities(true, -1, -1);
    }

    public List<ProveedorProducto> findProveedorProductoEntities(int maxResults, int firstResult) {
        return findProveedorProductoEntities(false, maxResults, firstResult);
    }

    private List<ProveedorProducto> findProveedorProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProveedorProducto.class));
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

    public ProveedorProducto findProveedorProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProveedorProducto.class, id);
        } finally {
            em.close();
        }
    }

    public ProveedorProducto findProveedorProducto(Proveedor id) {
        return findProveedorProducto(id.getIdproveedor());
    }

    public int getProveedorProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProveedorProducto> rt = cq.from(ProveedorProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
