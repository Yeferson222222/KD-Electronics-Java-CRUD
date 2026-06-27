package servicios;

import Dao.CategoriaDAO;
import Dao.ProductoDAO;
import Excepciones.CategoriaException;
import Excepciones.ConexionException;
import Excepciones.ProductoException;
import Excepciones.ValidacionException;
import Modelos.Categoria;
import Modelos.Producto;
import Utilidades.Validaciones;
import java.util.ArrayList;

public class ProductoServicio {

    private final ProductoDAO productoDAO;
    private final CategoriaDAO categoriaDAO;

    public ProductoServicio(ProductoDAO productoDAO, CategoriaDAO categoriaDAO) {
        this.productoDAO = productoDAO;
        this.categoriaDAO = categoriaDAO;
    }

    public ArrayList<Producto> listarProductos()
            throws ConexionException, ProductoException {
        return productoDAO.listarProductos();
    }

    public ArrayList<Categoria> listarCategorias()
            throws ConexionException, CategoriaException {
        return categoriaDAO.listarCategorias();
    }

    public Producto buscarProducto(String codigoProducto)
            throws ConexionException, ProductoException, ValidacionException {

        // 🔴 VALIDACIÓN OBLIGATORIA
        Validaciones.validarCodigoProducto(codigoProducto);

        return productoDAO.listarProductos()
                .stream()
                .filter(p -> p.getCodigoProducto().equalsIgnoreCase(codigoProducto))
                .findFirst()
                .orElseThrow(() -> new ProductoException("No existe un producto con ese código."));
    }

    public void registrarProducto(Producto producto)
            throws ConexionException, ProductoException,
            CategoriaException, ValidacionException {

        validarProducto(producto); // 🔴 SIEMPRE PRIMERO

        if (productoDAO.existeProducto(producto.getCodigoProducto())) {
            throw new ProductoException("Ya existe un producto con ese código.");
        }

        if (!categoriaDAO.existeCategoria(producto.getIdCategoria())) {
            throw new CategoriaException("La categoría no existe.");
        }

        productoDAO.insertarProducto(producto);
    }

    public void actualizarProducto(Producto producto)
            throws ConexionException, ProductoException,
            CategoriaException, ValidacionException {

        validarProducto(producto);

        if (!productoDAO.existeProducto(producto.getCodigoProducto())) {
            throw new ProductoException("No se puede actualizar porque el producto no existe.");
        }

        if (!categoriaDAO.existeCategoria(producto.getIdCategoria())) {
            throw new CategoriaException("La categoría no existe.");
        }

        productoDAO.actualizarProducto(producto);
    }

    public void eliminarProducto(String codigoProducto)
            throws ConexionException, ProductoException, ValidacionException {

        Validaciones.validarCodigoProducto(codigoProducto);

        if (!productoDAO.existeProducto(codigoProducto)) {
            throw new ProductoException("No se puede eliminar porque el producto no existe.");
        }

        productoDAO.eliminarProducto(codigoProducto);
    }

  
    private void validarProducto(Producto producto)
            throws ValidacionException {

        if (producto == null) {
            throw new ValidacionException("El producto no puede ser nulo.");
        }

        
        Validaciones.validarCodigoProducto(producto.getCodigoProducto());
        Validaciones.validarNombreProducto(producto.getNombreProducto());
        Validaciones.validarDescripcion(producto.getDescripcionProducto());

        
        Validaciones.validarTexto(
                producto.getIdCategoria(),
                "La categoría del producto es obligatoria."
        );

        Validaciones.validarPrecioBase(producto.getPrecioBase());

        Validaciones.validarPrecioVenta(
                producto.getPrecioBase(),
                producto.getPrecioVenta()
        );

        Validaciones.validarCantidad(producto.getCantidadDisponible());
    }
}