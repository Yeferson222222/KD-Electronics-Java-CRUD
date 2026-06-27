package Principal;

import Dao.CategoriaDAO;
import Dao.ProductoDAO;
import Excepciones.CategoriaException;
import Excepciones.ConexionException;
import Excepciones.ProductoException;
import Excepciones.ValidacionException;
import Modelos.Categoria;
import Modelos.Producto;
import Utilidades.Entrada;
import java.util.ArrayList;
import servicios.ProductoServicio;

public class Main {

    private static final ProductoDAO productoDAO = new ProductoDAO();
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private static final ProductoServicio productoServicio = new ProductoServicio(productoDAO, categoriaDAO);

    public static void main(String[] args) {

        int opcion = 0;

        do {

            try {

                mostrarMenu();

                opcion = Entrada.leerEntero("Seleccione una opcion: ");

                switch (opcion) {

                    case 1:
                        listarProductos();
                        break;

                    case 2:
                        insertarProducto();
                        break;

                    case 3:
                        actualizarProductos();
                        break;

                    case 4:
                        eliminarProductos();
                        break;

                    case 5:
                        buscarProductos();
                        break;

                    case 6:
                        mostrarCategorias();
                        break;

                    case 7:
                        System.out.println("Programa finalizado.");
                        break;

                    default:
                        System.out.println("Opcion incorrecta.");
                        break;
                }

            } catch (ValidacionException e) {

                System.out.println("Error de validacion: " + e.getMessage());

            } catch (ProductoException e) {

                System.out.println("Error de producto: " + e.getMessage());

            } catch (CategoriaException e) {

                System.out.println("Error de categoria: " + e.getMessage());

            } catch (ConexionException e) {

                System.out.println("Error de conexion: " + e.getMessage());

            }

        } while (opcion != 7);

    }

    private static void mostrarMenu() {

        System.out.println("\n========== CRUD PRODUCTOS ==========");
        System.out.println("1. Listar Productos");
        System.out.println("2. Insertar Producto");
        System.out.println("3. Actualizar Producto");
        System.out.println("4. Eliminar Producto");
        System.out.println("5. Buscar Producto");
        System.out.println("6. Ver Categorias");
        System.out.println("7. Salir");
    }

    private static void listarProductos() throws ConexionException, ProductoException {

        ArrayList<Producto> productos = productoServicio.listarProductos();

        System.out.println("\nPRODUCTOS REGISTRADOS");

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        for (Producto producto : productos) {
            imprimirProducto(producto);
        }

    }

    private static void imprimirProducto(Producto producto) {

        System.out.println("----------------------------------------");
        System.out.println("Codigo: " + producto.getCodigoProducto());
        System.out.println("Producto: " + producto.getNombreProducto());
        System.out.println("Descripcion: " + producto.getDescripcionProducto());
        System.out.println("Precio Base: " + producto.getPrecioBase());
        System.out.println("Precio Venta: " + producto.getPrecioVenta());
        System.out.println("Cantidad: " + producto.getCantidadDisponible());
        System.out.println("Id Categoria: " + producto.getIdCategoria());
        System.out.println("Categoria: " + producto.getNombreCategoria());

    }

    private static void insertarProducto()
            throws ValidacionException, ConexionException,
            ProductoException, CategoriaException {

        System.out.println("\nINSERTAR PRODUCTO");

        mostrarCategorias();

        Producto producto = leerDatosProducto(true);

        productoServicio.registrarProducto(producto);

        System.out.println("Producto registrado correctamente.");

    }

    public static void mostrarCategorias()
            throws ConexionException, CategoriaException {

        ArrayList<Categoria> categorias = productoServicio.listarCategorias();

        System.out.println("\nCATEGORIAS");

        if (categorias.isEmpty()) {

            System.out.println("No hay categorias registradas.");
            return;

        }

        for (Categoria categoria : categorias) {

            System.out.println(categoria.getIdCategoria()
                    + " - "
                    + categoria.getNombreCategoria());

        }

    }

    public static Producto leerDatosProducto(boolean pedirCodigo)
            throws ValidacionException {

        String codigoProducto = "";

        if (pedirCodigo) {

            codigoProducto = Entrada.leerTexto("COdigo del producto:");

        }

        String nombreProducto = Entrada.leerTexto("Nombre:");
        String descripcionProducto = Entrada.leerTexto("Descripción:");
        double precioBase = Entrada.leerDouble("Precio Base:");
        double precioVenta = Entrada.leerDouble("Precio Venta:");
        String idCategoria = Entrada.leerTexto("Id de la categoría:");
        int cantidadDisponible = Entrada.leerEntero("Cantidad disponible:");

        return new Producto(
                codigoProducto,
                nombreProducto,
                descripcionProducto,
                precioBase,
                precioVenta,
                idCategoria,
                cantidadDisponible);

    }

    private static void actualizarProductos()
            throws ValidacionException,
            ConexionException,
            ProductoException,
            CategoriaException {

        System.out.println("\nACTUALIZAR PRODUCTO");

        String codigoProducto = Entrada.leerTexto("Código del producto:");

        Producto productoEncontrado = productoServicio.buscarProducto(codigoProducto);

        System.out.println("\nPRODUCTO ENCONTRADO");

        imprimirProducto(productoEncontrado);

        mostrarCategorias();

        Producto productoActualizado = leerDatosProducto(false);

        productoActualizado.setCodigoProducto(codigoProducto);

        productoServicio.actualizarProducto(productoActualizado);

        System.out.println("Producto actualizado correctamente.");

    }

    private static void eliminarProductos()
            throws ValidacionException,
            ConexionException,
            ProductoException {

        System.out.println("\nELIMINAR PRODUCTO");

        String codigoProducto = Entrada.leerTexto("Código del producto:");

        Producto productoEncontrado = productoServicio.buscarProducto(codigoProducto);

        System.out.println("\nPRODUCTO ENCONTRADO");

        imprimirProducto(productoEncontrado);

        String confirmacion = Entrada.leerTexto("Escriba SI para confirmar:");

        if (confirmacion.equalsIgnoreCase("SI")) {

            productoServicio.eliminarProducto(codigoProducto);

            System.out.println("Producto eliminado correctamente.");

        } else {

            System.out.println("Eliminación cancelada.");

        }

    }

    private static void buscarProductos()
            throws ValidacionException,
            ConexionException,
            ProductoException {

        System.out.println("\nBUSCAR PRODUCTO");

        String codigoProducto = Entrada.leerTexto("Codigo del producto:");

        Producto productoEncontrado = productoServicio.buscarProducto(codigoProducto);

        imprimirProducto(productoEncontrado);

    }

}