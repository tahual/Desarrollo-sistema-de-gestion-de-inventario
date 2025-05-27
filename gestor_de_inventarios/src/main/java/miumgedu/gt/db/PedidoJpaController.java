/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumgedu.gt.db;

import miumg.edu.gt.gestor_de_inventarios.entity.Usuario;
import miumg.edu.gt.gestor_de_inventarios.entity.Producto;
import miumg.edu.gt.gestor_de_inventarios.entity.Pedido;
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
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idproducto = pedido.getIdproducto();
            if (idproducto != null) {
                idproducto = em.getReference(idproducto.getClass(), idproducto.getIdproducto());
                pedido.setIdproducto(idproducto);
            }
            Usuario idusuario = pedido.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdusuario());
                pedido.setIdusuario(idusuario);
            }
            em.persist(pedido);
            if (idproducto != null) {
                idproducto.getPedidoList().add(pedido);
                idproducto = em.merge(idproducto);
            }
            if (idusuario != null) {
                idusuario.getPedidoList().add(pedido);
                idusuario = em.merge(idusuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getIdpedido());
            Producto idproductoOld = persistentPedido.getIdproducto();
            Producto idproductoNew = pedido.getIdproducto();
            Usuario idusuarioOld = persistentPedido.getIdusuario();
            Usuario idusuarioNew = pedido.getIdusuario();
            if (idproductoNew != null) {
                idproductoNew = em.getReference(idproductoNew.getClass(), idproductoNew.getIdproducto());
                pedido.setIdproducto(idproductoNew);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
                pedido.setIdusuario(idusuarioNew);
            }
            pedido = em.merge(pedido);
            if (idproductoOld != null && !idproductoOld.equals(idproductoNew)) {
                idproductoOld.getPedidoList().remove(pedido);
                idproductoOld = em.merge(idproductoOld);
            }
            if (idproductoNew != null && !idproductoNew.equals(idproductoOld)) {
                idproductoNew.getPedidoList().add(pedido);
                idproductoNew = em.merge(idproductoNew);
            }
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getPedidoList().remove(pedido);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getPedidoList().add(pedido);
                idusuarioNew = em.merge(idusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedido.getIdpedido();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getIdpedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            Producto idproducto = pedido.getIdproducto();
            if (idproducto != null) {
                idproducto.getPedidoList().remove(pedido);
                idproducto = em.merge(idproducto);
            }
            Usuario idusuario = pedido.getIdusuario();
            if (idusuario != null) {
                idusuario.getPedidoList().remove(pedido);
                idusuario = em.merge(idusuario);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
