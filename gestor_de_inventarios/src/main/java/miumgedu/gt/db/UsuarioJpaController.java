/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumgedu.gt.db;

import miumg.edu.gt.gestor_de_inventarios.entity.Venta;
import miumg.edu.gt.gestor_de_inventarios.entity.Usuario;
import miumg.edu.gt.gestor_de_inventarios.entity.Reporte;
import miumg.edu.gt.gestor_de_inventarios.entity.Proveedor;
import miumg.edu.gt.gestor_de_inventarios.entity.Producto;
import miumg.edu.gt.gestor_de_inventarios.entity.Pedido;
import miumg.edu.gt.gestor_de_inventarios.entity.Ordencompra;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import miumgedu.gt.db.exceptions.NonexistentEntityException;

/**
 *
 * @author danyt
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getVentaList() == null) {
            usuario.setVentaList(new ArrayList<Venta>());
        }
        if (usuario.getOrdencompraList() == null) {
            usuario.setOrdencompraList(new ArrayList<Ordencompra>());
        }
        if (usuario.getPedidoList() == null) {
            usuario.setPedidoList(new ArrayList<Pedido>());
        }
        if (usuario.getProveedorList() == null) {
            usuario.setProveedorList(new ArrayList<Proveedor>());
        }
        if (usuario.getProductoList() == null) {
            usuario.setProductoList(new ArrayList<Producto>());
        }
        if (usuario.getReporteList() == null) {
            usuario.setReporteList(new ArrayList<Reporte>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : usuario.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getIdventa());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            usuario.setVentaList(attachedVentaList);
            List<Ordencompra> attachedOrdencompraList = new ArrayList<Ordencompra>();
            for (Ordencompra ordencompraListOrdencompraToAttach : usuario.getOrdencompraList()) {
                ordencompraListOrdencompraToAttach = em.getReference(ordencompraListOrdencompraToAttach.getClass(), ordencompraListOrdencompraToAttach.getIdordencompra());
                attachedOrdencompraList.add(ordencompraListOrdencompraToAttach);
            }
            usuario.setOrdencompraList(attachedOrdencompraList);
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : usuario.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getIdpedido());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            usuario.setPedidoList(attachedPedidoList);
            List<Proveedor> attachedProveedorList = new ArrayList<Proveedor>();
            for (Proveedor proveedorListProveedorToAttach : usuario.getProveedorList()) {
                proveedorListProveedorToAttach = em.getReference(proveedorListProveedorToAttach.getClass(), proveedorListProveedorToAttach.getIdproveedor());
                attachedProveedorList.add(proveedorListProveedorToAttach);
            }
            usuario.setProveedorList(attachedProveedorList);
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : usuario.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getIdproducto());
                attachedProductoList.add(productoListProductoToAttach);
            }
            usuario.setProductoList(attachedProductoList);
            List<Reporte> attachedReporteList = new ArrayList<Reporte>();
            for (Reporte reporteListReporteToAttach : usuario.getReporteList()) {
                reporteListReporteToAttach = em.getReference(reporteListReporteToAttach.getClass(), reporteListReporteToAttach.getIdreporte());
                attachedReporteList.add(reporteListReporteToAttach);
            }
            usuario.setReporteList(attachedReporteList);
            em.persist(usuario);
            for (Venta ventaListVenta : usuario.getVentaList()) {
                Usuario oldIdusuarioOfVentaListVenta = ventaListVenta.getIdusuario();
                ventaListVenta.setIdusuario(usuario);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldIdusuarioOfVentaListVenta != null) {
                    oldIdusuarioOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldIdusuarioOfVentaListVenta = em.merge(oldIdusuarioOfVentaListVenta);
                }
            }
            for (Ordencompra ordencompraListOrdencompra : usuario.getOrdencompraList()) {
                Usuario oldIdusuarioOfOrdencompraListOrdencompra = ordencompraListOrdencompra.getIdusuario();
                ordencompraListOrdencompra.setIdusuario(usuario);
                ordencompraListOrdencompra = em.merge(ordencompraListOrdencompra);
                if (oldIdusuarioOfOrdencompraListOrdencompra != null) {
                    oldIdusuarioOfOrdencompraListOrdencompra.getOrdencompraList().remove(ordencompraListOrdencompra);
                    oldIdusuarioOfOrdencompraListOrdencompra = em.merge(oldIdusuarioOfOrdencompraListOrdencompra);
                }
            }
            for (Pedido pedidoListPedido : usuario.getPedidoList()) {
                Usuario oldIdusuarioOfPedidoListPedido = pedidoListPedido.getIdusuario();
                pedidoListPedido.setIdusuario(usuario);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldIdusuarioOfPedidoListPedido != null) {
                    oldIdusuarioOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldIdusuarioOfPedidoListPedido = em.merge(oldIdusuarioOfPedidoListPedido);
                }
            }
            for (Proveedor proveedorListProveedor : usuario.getProveedorList()) {
                Usuario oldIdusuarioOfProveedorListProveedor = proveedorListProveedor.getIdusuario();
                proveedorListProveedor.setIdusuario(usuario);
                proveedorListProveedor = em.merge(proveedorListProveedor);
                if (oldIdusuarioOfProveedorListProveedor != null) {
                    oldIdusuarioOfProveedorListProveedor.getProveedorList().remove(proveedorListProveedor);
                    oldIdusuarioOfProveedorListProveedor = em.merge(oldIdusuarioOfProveedorListProveedor);
                }
            }
            for (Producto productoListProducto : usuario.getProductoList()) {
                Usuario oldIdusuarioOfProductoListProducto = productoListProducto.getIdusuario();
                productoListProducto.setIdusuario(usuario);
                productoListProducto = em.merge(productoListProducto);
                if (oldIdusuarioOfProductoListProducto != null) {
                    oldIdusuarioOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldIdusuarioOfProductoListProducto = em.merge(oldIdusuarioOfProductoListProducto);
                }
            }
            for (Reporte reporteListReporte : usuario.getReporteList()) {
                Usuario oldIdusuarioOfReporteListReporte = reporteListReporte.getIdusuario();
                reporteListReporte.setIdusuario(usuario);
                reporteListReporte = em.merge(reporteListReporte);
                if (oldIdusuarioOfReporteListReporte != null) {
                    oldIdusuarioOfReporteListReporte.getReporteList().remove(reporteListReporte);
                    oldIdusuarioOfReporteListReporte = em.merge(oldIdusuarioOfReporteListReporte);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdusuario());
            List<Venta> ventaListOld = persistentUsuario.getVentaList();
            List<Venta> ventaListNew = usuario.getVentaList();
            List<Ordencompra> ordencompraListOld = persistentUsuario.getOrdencompraList();
            List<Ordencompra> ordencompraListNew = usuario.getOrdencompraList();
            List<Pedido> pedidoListOld = persistentUsuario.getPedidoList();
            List<Pedido> pedidoListNew = usuario.getPedidoList();
            List<Proveedor> proveedorListOld = persistentUsuario.getProveedorList();
            List<Proveedor> proveedorListNew = usuario.getProveedorList();
            List<Producto> productoListOld = persistentUsuario.getProductoList();
            List<Producto> productoListNew = usuario.getProductoList();
            List<Reporte> reporteListOld = persistentUsuario.getReporteList();
            List<Reporte> reporteListNew = usuario.getReporteList();
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getIdventa());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            usuario.setVentaList(ventaListNew);
            List<Ordencompra> attachedOrdencompraListNew = new ArrayList<Ordencompra>();
            for (Ordencompra ordencompraListNewOrdencompraToAttach : ordencompraListNew) {
                ordencompraListNewOrdencompraToAttach = em.getReference(ordencompraListNewOrdencompraToAttach.getClass(), ordencompraListNewOrdencompraToAttach.getIdordencompra());
                attachedOrdencompraListNew.add(ordencompraListNewOrdencompraToAttach);
            }
            ordencompraListNew = attachedOrdencompraListNew;
            usuario.setOrdencompraList(ordencompraListNew);
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getIdpedido());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            usuario.setPedidoList(pedidoListNew);
            List<Proveedor> attachedProveedorListNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorListNewProveedorToAttach : proveedorListNew) {
                proveedorListNewProveedorToAttach = em.getReference(proveedorListNewProveedorToAttach.getClass(), proveedorListNewProveedorToAttach.getIdproveedor());
                attachedProveedorListNew.add(proveedorListNewProveedorToAttach);
            }
            proveedorListNew = attachedProveedorListNew;
            usuario.setProveedorList(proveedorListNew);
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getIdproducto());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            usuario.setProductoList(productoListNew);
            List<Reporte> attachedReporteListNew = new ArrayList<Reporte>();
            for (Reporte reporteListNewReporteToAttach : reporteListNew) {
                reporteListNewReporteToAttach = em.getReference(reporteListNewReporteToAttach.getClass(), reporteListNewReporteToAttach.getIdreporte());
                attachedReporteListNew.add(reporteListNewReporteToAttach);
            }
            reporteListNew = attachedReporteListNew;
            usuario.setReporteList(reporteListNew);
            usuario = em.merge(usuario);
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    ventaListOldVenta.setIdusuario(null);
                    ventaListOldVenta = em.merge(ventaListOldVenta);
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Usuario oldIdusuarioOfVentaListNewVenta = ventaListNewVenta.getIdusuario();
                    ventaListNewVenta.setIdusuario(usuario);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldIdusuarioOfVentaListNewVenta != null && !oldIdusuarioOfVentaListNewVenta.equals(usuario)) {
                        oldIdusuarioOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldIdusuarioOfVentaListNewVenta = em.merge(oldIdusuarioOfVentaListNewVenta);
                    }
                }
            }
            for (Ordencompra ordencompraListOldOrdencompra : ordencompraListOld) {
                if (!ordencompraListNew.contains(ordencompraListOldOrdencompra)) {
                    ordencompraListOldOrdencompra.setIdusuario(null);
                    ordencompraListOldOrdencompra = em.merge(ordencompraListOldOrdencompra);
                }
            }
            for (Ordencompra ordencompraListNewOrdencompra : ordencompraListNew) {
                if (!ordencompraListOld.contains(ordencompraListNewOrdencompra)) {
                    Usuario oldIdusuarioOfOrdencompraListNewOrdencompra = ordencompraListNewOrdencompra.getIdusuario();
                    ordencompraListNewOrdencompra.setIdusuario(usuario);
                    ordencompraListNewOrdencompra = em.merge(ordencompraListNewOrdencompra);
                    if (oldIdusuarioOfOrdencompraListNewOrdencompra != null && !oldIdusuarioOfOrdencompraListNewOrdencompra.equals(usuario)) {
                        oldIdusuarioOfOrdencompraListNewOrdencompra.getOrdencompraList().remove(ordencompraListNewOrdencompra);
                        oldIdusuarioOfOrdencompraListNewOrdencompra = em.merge(oldIdusuarioOfOrdencompraListNewOrdencompra);
                    }
                }
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    pedidoListOldPedido.setIdusuario(null);
                    pedidoListOldPedido = em.merge(pedidoListOldPedido);
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Usuario oldIdusuarioOfPedidoListNewPedido = pedidoListNewPedido.getIdusuario();
                    pedidoListNewPedido.setIdusuario(usuario);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldIdusuarioOfPedidoListNewPedido != null && !oldIdusuarioOfPedidoListNewPedido.equals(usuario)) {
                        oldIdusuarioOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldIdusuarioOfPedidoListNewPedido = em.merge(oldIdusuarioOfPedidoListNewPedido);
                    }
                }
            }
            for (Proveedor proveedorListOldProveedor : proveedorListOld) {
                if (!proveedorListNew.contains(proveedorListOldProveedor)) {
                    proveedorListOldProveedor.setIdusuario(null);
                    proveedorListOldProveedor = em.merge(proveedorListOldProveedor);
                }
            }
            for (Proveedor proveedorListNewProveedor : proveedorListNew) {
                if (!proveedorListOld.contains(proveedorListNewProveedor)) {
                    Usuario oldIdusuarioOfProveedorListNewProveedor = proveedorListNewProveedor.getIdusuario();
                    proveedorListNewProveedor.setIdusuario(usuario);
                    proveedorListNewProveedor = em.merge(proveedorListNewProveedor);
                    if (oldIdusuarioOfProveedorListNewProveedor != null && !oldIdusuarioOfProveedorListNewProveedor.equals(usuario)) {
                        oldIdusuarioOfProveedorListNewProveedor.getProveedorList().remove(proveedorListNewProveedor);
                        oldIdusuarioOfProveedorListNewProveedor = em.merge(oldIdusuarioOfProveedorListNewProveedor);
                    }
                }
            }
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setIdusuario(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Usuario oldIdusuarioOfProductoListNewProducto = productoListNewProducto.getIdusuario();
                    productoListNewProducto.setIdusuario(usuario);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldIdusuarioOfProductoListNewProducto != null && !oldIdusuarioOfProductoListNewProducto.equals(usuario)) {
                        oldIdusuarioOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldIdusuarioOfProductoListNewProducto = em.merge(oldIdusuarioOfProductoListNewProducto);
                    }
                }
            }
            for (Reporte reporteListOldReporte : reporteListOld) {
                if (!reporteListNew.contains(reporteListOldReporte)) {
                    reporteListOldReporte.setIdusuario(null);
                    reporteListOldReporte = em.merge(reporteListOldReporte);
                }
            }
            for (Reporte reporteListNewReporte : reporteListNew) {
                if (!reporteListOld.contains(reporteListNewReporte)) {
                    Usuario oldIdusuarioOfReporteListNewReporte = reporteListNewReporte.getIdusuario();
                    reporteListNewReporte.setIdusuario(usuario);
                    reporteListNewReporte = em.merge(reporteListNewReporte);
                    if (oldIdusuarioOfReporteListNewReporte != null && !oldIdusuarioOfReporteListNewReporte.equals(usuario)) {
                        oldIdusuarioOfReporteListNewReporte.getReporteList().remove(reporteListNewReporte);
                        oldIdusuarioOfReporteListNewReporte = em.merge(oldIdusuarioOfReporteListNewReporte);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdusuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdusuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<Venta> ventaList = usuario.getVentaList();
            for (Venta ventaListVenta : ventaList) {
                ventaListVenta.setIdusuario(null);
                ventaListVenta = em.merge(ventaListVenta);
            }
            List<Ordencompra> ordencompraList = usuario.getOrdencompraList();
            for (Ordencompra ordencompraListOrdencompra : ordencompraList) {
                ordencompraListOrdencompra.setIdusuario(null);
                ordencompraListOrdencompra = em.merge(ordencompraListOrdencompra);
            }
            List<Pedido> pedidoList = usuario.getPedidoList();
            for (Pedido pedidoListPedido : pedidoList) {
                pedidoListPedido.setIdusuario(null);
                pedidoListPedido = em.merge(pedidoListPedido);
            }
            List<Proveedor> proveedorList = usuario.getProveedorList();
            for (Proveedor proveedorListProveedor : proveedorList) {
                proveedorListProveedor.setIdusuario(null);
                proveedorListProveedor = em.merge(proveedorListProveedor);
            }
            List<Producto> productoList = usuario.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setIdusuario(null);
                productoListProducto = em.merge(productoListProducto);
            }
            List<Reporte> reporteList = usuario.getReporteList();
            for (Reporte reporteListReporte : reporteList) {
                reporteListReporte.setIdusuario(null);
                reporteListReporte = em.merge(reporteListReporte);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
