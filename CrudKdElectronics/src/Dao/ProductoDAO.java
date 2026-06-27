package Dao;

import Conexion.ConexionBD;
import Excepciones.ConexionException;
import Excepciones.ProductoException;
import Modelos.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDAO {

    public ArrayList<Producto> listarProductos() throws ConexionException, ProductoException {

        ArrayList<Producto> productos = new ArrayList<>();

        String sql = "SELECT p.codigoproducto, "
                + "p.nombreproducto, "
                + "p.descripcionproducto, "
                + "p.preciobase, "
                + "p.precioventa, "
                + "p.idcategoria, "
                + "p.cantidaddisponible, "
                + "c.nombrecategoria "
                + "FROM producto p "
                + "INNER JOIN categoria c "
                + "ON p.idcategoria = c.idcategoria "
                + "WHERE p.activo = TRUE "
                + "ORDER BY p.codigoproducto";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                productos.add(mapearProducto(resultado));
            }

        } catch (SQLException e) {
            throw new ProductoException("Error al listar los productos: " + e.getMessage());
        }

        return productos;
    }

    private Producto mapearProducto(ResultSet resultado) throws SQLException {

        Producto producto = new Producto();

        producto.setCodigoProducto(resultado.getString("codigoproducto"));
        producto.setNombreProducto(resultado.getString("nombreproducto"));
        producto.setDescripcionProducto(resultado.getString("descripcionproducto"));
        producto.setPrecioBase(resultado.getDouble("preciobase"));
        producto.setPrecioVenta(resultado.getDouble("precioventa"));
        producto.setIdCategoria(resultado.getString("idcategoria"));
        producto.setCantidadDisponible(resultado.getInt("cantidaddisponible"));
        producto.setNombreCategoria(resultado.getString("nombrecategoria"));

        return producto;
    }

    public boolean existeProducto(String codigoProducto) throws ConexionException, ProductoException {

        String sql = "SELECT codigoproducto "
                + "FROM producto "
                + "WHERE codigoproducto = ? "
                + "AND activo = TRUE";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, codigoProducto);

            try (ResultSet resultado = consulta.executeQuery()) {
                return resultado.next();
            }

        } catch (SQLException e) {
            throw new ProductoException("Error al verificar el producto: " + e.getMessage());
        }
    }

    public void insertarProducto(Producto producto) throws ConexionException, ProductoException {

        String sql = "INSERT INTO producto "
                + "(codigoproducto, nombreproducto, descripcionproducto, "
                + "preciobase, precioventa, idcategoria, cantidaddisponible, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, TRUE)";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, producto.getCodigoProducto());
            consulta.setString(2, producto.getNombreProducto());
            consulta.setString(3, producto.getDescripcionProducto());
            consulta.setDouble(4, producto.getPrecioBase());
            consulta.setDouble(5, producto.getPrecioVenta());
            consulta.setString(6, producto.getIdCategoria());
            consulta.setInt(7, producto.getCantidadDisponible());

            consulta.executeUpdate();

        } catch (SQLException e) {
            throw new ProductoException("Error al insertar el producto: " + e.getMessage());
        }
    }

    public void actualizarProducto(Producto producto) throws ConexionException, ProductoException {

        String sql = "UPDATE producto SET "
                + "nombreproducto = ?, "
                + "descripcionproducto = ?, "
                + "preciobase = ?, "
                + "precioventa = ?, "
                + "idcategoria = ?, "
                + "cantidaddisponible = ? "
                + "WHERE codigoproducto = ? "
                + "AND activo = TRUE";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, producto.getNombreProducto());
            consulta.setString(2, producto.getDescripcionProducto());
            consulta.setDouble(3, producto.getPrecioBase());
            consulta.setDouble(4, producto.getPrecioVenta());
            consulta.setString(5, producto.getIdCategoria());
            consulta.setInt(6, producto.getCantidadDisponible());
            consulta.setString(7, producto.getCodigoProducto());

            consulta.executeUpdate();

        } catch (SQLException e) {
            throw new ProductoException("Error al actualizar el producto: " + e.getMessage());
        }
    }

    public void eliminarProducto(String codigoProducto) throws ConexionException, ProductoException {

        String sql = "UPDATE producto "
                + "SET activo = FALSE "
                + "WHERE codigoproducto = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, codigoProducto);

            consulta.executeUpdate();

        } catch (SQLException e) {
            throw new ProductoException("Error al eliminar el producto: " + e.getMessage());
        }
    }

}