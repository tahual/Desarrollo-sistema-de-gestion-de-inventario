/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumgedu.gt.db;

import miumg.edu.gt.gestor_de_inventarios.entity.Usuario;
import miumg.edu.gt.gestor_de_inventarios.entity.Reporte;
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
public class ReporteJpaController implements Serializable {

    public ReporteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reporte reporte) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idusuario = reporte.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdusuario());
                reporte.setIdusuario(idusuario);
            }
            em.persist(reporte);
            if (idusuario != null) {
                idusuario.getReporteList().add(reporte);
                idusuario = em.merge(idusuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reporte reporte) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reporte persistentReporte = em.find(Reporte.class, reporte.getIdreporte());
            Usuario idusuarioOld = persistentReporte.getIdusuario();
            Usuario idusuarioNew = reporte.getIdusuario();
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
                reporte.setIdusuario(idusuarioNew);
            }
            reporte = em.merge(reporte);
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getReporteList().remove(reporte);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getReporteList().add(reporte);
                idusuarioNew = em.merge(idusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reporte.getIdreporte();
                if (findReporte(id) == null) {
                    throw new NonexistentEntityException("The reporte with id " + id + " no longer exists.");
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
            Reporte reporte;
            try {
                reporte = em.getReference(Reporte.class, id);
                reporte.getIdreporte();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reporte with id " + id + " no longer exists.", enfe);
            }
            Usuario idusuario = reporte.getIdusuario();
            if (idusuario != null) {
                idusuario.getReporteList().remove(reporte);
                idusuario = em.merge(idusuario);
            }
            em.remove(reporte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reporte> findReporteEntities() {
        return findReporteEntities(true, -1, -1);
    }

    public List<Reporte> findReporteEntities(int maxResults, int firstResult) {
        return findReporteEntities(false, maxResults, firstResult);
    }

    private List<Reporte> findReporteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reporte.class));
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

    public Reporte findReporte(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reporte.class, id);
        } finally {
            em.close();
        }
    }

    public int getReporteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reporte> rt = cq.from(Reporte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
