/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumgedu.gt.db;

import miumg.edu.gt.gestor_de_inventarios.entity.Usuario;
import miumg.edu.gt.gestor_de_inventarios.entity.Proveedor;
import miumg.edu.gt.gestor_de_inventarios.entity.Producto;
import miumg.edu.gt.gestor_de_inventarios.entity.Pedido;
import miumg.edu.gt.gestor_de_inventarios.entity.Ordencompra;
import miumg.edu.gt.gestor_de_inventarios.entity.Factura;
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
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getProveedorList() == null) {
            producto.setProveedorList(new ArrayList<Proveedor>());
        }
        if (producto.getFacturaList() == null) {
            producto.setFacturaList(new ArrayList<Factura>());
        }
        if (producto.getOrdencompraList() == null) {
            producto.setOrdencompraList(new ArrayList<Ordencompra>());
        }
        if (producto.getPedidoList() == null) {
            producto.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idusuario = producto.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdusuario());
                producto.setIdusuario(idusuario);
            }
            List<Proveedor> attachedProveedorList = new ArrayList<Proveedor>();
            for (Proveedor proveedorListProveedorToAttach : producto.getProveedorList()) {
                proveedorListProveedorToAttach = em.getReference(proveedorListProveedorToAttach.getClass(), proveedorListProveedorToAttach.getIdproveedor());
                attachedProveedorList.add(proveedorListProveedorToAttach);
            }
            producto.setProveedorList(attachedProveedorList);
            List<Factura> attachedFacturaList = new ArrayList<Factura>();
            for (Factura facturaListFacturaToAttach : producto.getFacturaList()) {
                facturaListFacturaToAttach = em.getReference(facturaListFacturaToAttach.getClass(), facturaListFacturaToAttach.getIdfactura());
                attachedFacturaList.add(facturaListFacturaToAttach);
            }
            producto.setFacturaList(attachedFacturaList);
            List<Ordencompra> attachedOrdencompraList = new ArrayList<Ordencompra>();
            for (Ordencompra ordencompraListOrdencompraToAttach : producto.getOrdencompraList()) {
                ordencompraListOrdencompraToAttach = em.getReference(ordencompraListOrdencompraToAttach.getClass(), ordencompraListOrdencompraToAttach.getIdordencompra());
                attachedOrdencompraList.add(ordencompraListOrdencompraToAttach);
            }
            producto.setOrdencompraList(attachedOrdencompraList);
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : producto.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getIdpedido());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            producto.setPedidoList(attachedPedidoList);
            em.persist(producto);
            if (idusuario != null) {
                idusuario.getProductoList().add(producto);
                idusuario = em.merge(idusuario);
            }
            for (Proveedor proveedorListProveedor : producto.getProveedorList()) {
                proveedorListProveedor.getProductoList().add(producto);
                proveedorListProveedor = em.merge(proveedorListProveedor);
            }
            for (Factura facturaListFactura : producto.getFacturaList()) {
                Producto oldIdproductoOfFacturaListFactura = facturaListFactura.getIdproducto();
                facturaListFactura.setIdproducto(producto);
                facturaListFactura = em.merge(facturaListFactura);
                if (oldIdproductoOfFacturaListFactura != null) {
                    oldIdproductoOfFacturaListFactura.getFacturaList().remove(facturaListFactura);
                    oldIdproductoOfFacturaListFactura = em.merge(oldIdproductoOfFacturaListFactura);
                }
            }
            for (Ordencompra ordencompraListOrdencompra : producto.getOrdencompraList()) {
                Producto oldIdproductoOfOrdencompraListOrdencompra = ordencompraListOrdencompra.getIdproducto();
                ordencompraListOrdencompra.setIdproducto(producto);
                ordencompraListOrdencompra = em.merge(ordencompraListOrdencompra);
                if (oldIdproductoOfOrdencompraListOrdencompra != null) {
                    oldIdproductoOfOrdencompraListOrdencompra.getOrdencompraList().remove(ordencompraListOrdencompra);
                    oldIdproductoOfOrdencompraListOrdencompra = em.merge(oldIdproductoOfOrdencompraListOrdencompra);
                }
            }
            for (Pedido pedidoListPedido : producto.getPedidoList()) {
                Producto oldIdproductoOfPedidoListPedido = pedidoListPedido.getIdproducto();
                pedidoListPedido.setIdproducto(producto);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldIdproductoOfPedidoListPedido != null) {
                    oldIdproductoOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldIdproductoOfPedidoListPedido = em.merge(oldIdproductoOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdproducto());
            Usuario idusuarioOld = persistentProducto.getIdusuario();
            Usuario idusuarioNew = producto.getIdusuario();
            List<Proveedor> proveedorListOld = persistentProducto.getProveedorList();
            List<Proveedor> proveedorListNew = producto.getProveedorList();
            List<Factura> facturaListOld = persistentProducto.getFacturaList();
            List<Factura> facturaListNew = producto.getFacturaList();
            List<Ordencompra> ordencompraListOld = persistentProducto.getOrdencompraList();
            List<Ordencompra> ordencompraListNew = producto.getOrdencompraList();
            List<Pedido> pedidoListOld = persistentProducto.getPedidoList();
            List<Pedido> pedidoListNew = producto.getPedidoList();
            List<String> illegalOrphanMessages = null;
            for (Factura facturaListOldFactura : facturaListOld) {
                if (!facturaListNew.contains(facturaListOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaListOldFactura + " since its idproducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
                producto.setIdusuario(idusuarioNew);
            }
            List<Proveedor> attachedProveedorListNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorListNewProveedorToAttach : proveedorListNew) {
                proveedorListNewProveedorToAttach = em.getReference(proveedorListNewProveedorToAttach.getClass(), proveedorListNewProveedorToAttach.getIdproveedor());
                attachedProveedorListNew.add(proveedorListNewProveedorToAttach);
            }
            proveedorListNew = attachedProveedorListNew;
            producto.setProveedorList(proveedorListNew);
            List<Factura> attachedFacturaListNew = new ArrayList<Factura>();
            for (Factura facturaListNewFacturaToAttach : facturaListNew) {
                facturaListNewFacturaToAttach = em.getReference(facturaListNewFacturaToAttach.getClass(), facturaListNewFacturaToAttach.getIdfactura());
                attachedFacturaListNew.add(facturaListNewFacturaToAttach);
            }
            facturaListNew = attachedFacturaListNew;
            producto.setFacturaList(facturaListNew);
            List<Ordencompra> attachedOrdencompraListNew = new ArrayList<Ordencompra>();
            for (Ordencompra ordencompraListNewOrdencompraToAttach : ordencompraListNew) {
                ordencompraListNewOrdencompraToAttach = em.getReference(ordencompraListNewOrdencompraToAttach.getClass(), ordencompraListNewOrdencompraToAttach.getIdordencompra());
                attachedOrdencompraListNew.add(ordencompraListNewOrdencompraToAttach);
            }
            ordencompraListNew = attachedOrdencompraListNew;
            producto.setOrdencompraList(ordencompraListNew);
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getIdpedido());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            producto.setPedidoList(pedidoListNew);
            producto = em.merge(producto);
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getProductoList().remove(producto);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getProductoList().add(producto);
                idusuarioNew = em.merge(idusuarioNew);
            }
            for (Proveedor proveedorListOldProveedor : proveedorListOld) {
                if (!proveedorListNew.contains(proveedorListOldProveedor)) {
                    proveedorListOldProveedor.getProductoList().remove(producto);
                    proveedorListOldProveedor = em.merge(proveedorListOldProveedor);
                }
            }
            for (Proveedor proveedorListNewProveedor : proveedorListNew) {
                if (!proveedorListOld.contains(proveedorListNewProveedor)) {
                    proveedorListNewProveedor.getProductoList().add(producto);
                    proveedorListNewProveedor = em.merge(proveedorListNewProveedor);
                }
            }
            for (Factura facturaListNewFactura : facturaListNew) {
                if (!facturaListOld.contains(facturaListNewFactura)) {
                    Producto oldIdproductoOfFacturaListNewFactura = facturaListNewFactura.getIdproducto();
                    facturaListNewFactura.setIdproducto(producto);
                    facturaListNewFactura = em.merge(facturaListNewFactura);
                    if (oldIdproductoOfFacturaListNewFactura != null && !oldIdproductoOfFacturaListNewFactura.equals(producto)) {
                        oldIdproductoOfFacturaListNewFactura.getFacturaList().remove(facturaListNewFactura);
                        oldIdproductoOfFacturaListNewFactura = em.merge(oldIdproductoOfFacturaListNewFactura);
                    }
                }
            }
            for (Ordencompra ordencompraListOldOrdencompra : ordencompraListOld) {
                if (!ordencompraListNew.contains(ordencompraListOldOrdencompra)) {
                    ordencompraListOldOrdencompra.setIdproducto(null);
                    ordencompraListOldOrdencompra = em.merge(ordencompraListOldOrdencompra);
                }
            }
            for (Ordencompra ordencompraListNewOrdencompra : ordencompraListNew) {
                if (!ordencompraListOld.contains(ordencompraListNewOrdencompra)) {
                    Producto oldIdproductoOfOrdencompraListNewOrdencompra = ordencompraListNewOrdencompra.getIdproducto();
                    ordencompraListNewOrdencompra.setIdproducto(producto);
                    ordencompraListNewOrdencompra = em.merge(ordencompraListNewOrdencompra);
                    if (oldIdproductoOfOrdencompraListNewOrdencompra != null && !oldIdproductoOfOrdencompraListNewOrdencompra.equals(producto)) {
                        oldIdproductoOfOrdencompraListNewOrdencompra.getOrdencompraList().remove(ordencompraListNewOrdencompra);
                        oldIdproductoOfOrdencompraListNewOrdencompra = em.merge(oldIdproductoOfOrdencompraListNewOrdencompra);
                    }
                }
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    pedidoListOldPedido.setIdproducto(null);
                    pedidoListOldPedido = em.merge(pedidoListOldPedido);
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Producto oldIdproductoOfPedidoListNewPedido = pedidoListNewPedido.getIdproducto();
                    pedidoListNewPedido.setIdproducto(producto);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldIdproductoOfPedidoListNewPedido != null && !oldIdproductoOfPedidoListNewPedido.equals(producto)) {
                        oldIdproductoOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldIdproductoOfPedidoListNewPedido = em.merge(oldIdproductoOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdproducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdproducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Factura> facturaListOrphanCheck = producto.getFacturaList();
            for (Factura facturaListOrphanCheckFactura : facturaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Factura " + facturaListOrphanCheckFactura + " in its facturaList field has a non-nullable idproducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario idusuario = producto.getIdusuario();
            if (idusuario != null) {
                idusuario.getProductoList().remove(producto);
                idusuario = em.merge(idusuario);
            }
            List<Proveedor> proveedorList = producto.getProveedorList();
            for (Proveedor proveedorListProveedor : proveedorList) {
                proveedorListProveedor.getProductoList().remove(producto);
                proveedorListProveedor = em.merge(proveedorListProveedor);
            }
            List<Ordencompra> ordencompraList = producto.getOrdencompraList();
            for (Ordencompra ordencompraListOrdencompra : ordencompraList) {
                ordencompraListOrdencompra.setIdproducto(null);
                ordencompraListOrdencompra = em.merge(ordencompraListOrdencompra);
            }
            List<Pedido> pedidoList = producto.getPedidoList();
            for (Pedido pedidoListPedido : pedidoList) {
                pedidoListPedido.setIdproducto(null);
                pedidoListPedido = em.merge(pedidoListPedido);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
