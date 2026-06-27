package Utilidades;

import Excepciones.ValidacionException;

public class Validaciones {

    // ==========================================
    // EXPRESIONES REGULARES
    // ==========================================

    // Código del producto: P001, P002, P999
    private static final String REGEX_CODIGO = "^P\\d{3}$";

    // Nombre del producto
    private static final String REGEX_NOMBRE =
            "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 .()\\-]{3,50}$";

    // Descripción
    private static final String REGEX_DESCRIPCION =
            "^.{5,200}$";

    // Código de categoría: CAT01, CAT02...
    private static final String REGEX_CATEGORIA =
            "^CAT\\d{2}$";

    // ==========================================
    // VALIDAR CÓDIGO DEL PRODUCTO
    // ==========================================

    public static void validarCodigoProducto(String codigo)
            throws ValidacionException {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new ValidacionException("El código del producto es obligatorio.");
        }

        codigo = codigo.trim().toUpperCase();

        if (!codigo.matches(REGEX_CODIGO)) {
            throw new ValidacionException(
                    "Código inválido. Debe tener el formato P001.");
        }
    }

    // ==========================================
    // VALIDAR NOMBRE DEL PRODUCTO
    // ==========================================

    public static void validarNombreProducto(String nombre)
            throws ValidacionException {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del producto es obligatorio.");
        }

        nombre = nombre.trim();

        if (!nombre.matches(REGEX_NOMBRE)) {
            throw new ValidacionException(
                    "El nombre debe contener entre 3 y 50 caracteres y solo puede incluir letras, números, espacios, puntos, guiones y paréntesis.");
        }
    }

    // ==========================================
    // VALIDAR DESCRIPCIÓN
    // ==========================================

    public static void validarDescripcion(String descripcion)
            throws ValidacionException {

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new ValidacionException("La descripción es obligatoria.");
        }

        descripcion = descripcion.trim();

        if (!descripcion.matches(REGEX_DESCRIPCION)) {
            throw new ValidacionException(
                    "La descripción debe contener entre 5 y 200 caracteres.");
        }
    }

    // ==========================================
    // VALIDAR CATEGORÍA
    // ==========================================

    public static void validarTexto(String categoria, String mensaje)
            throws ValidacionException {

        if (categoria == null || categoria.trim().isEmpty()) {
            throw new ValidacionException(mensaje);
        }

        categoria = categoria.trim().toUpperCase();

        if (!categoria.matches(REGEX_CATEGORIA)) {
            throw new ValidacionException(
                    "La categoría debe tener el formato CAT01.");
        }
    }

    // ==========================================
    // VALIDAR PRECIO BASE
    // ==========================================

    public static void validarPrecioBase(double precio)
            throws ValidacionException {

        if (precio <= 0) {
            throw new ValidacionException(
                    "El precio base debe ser mayor que cero.");
        }
    }

    // ==========================================
    // VALIDAR PRECIO DE VENTA
    // ==========================================

    public static void validarPrecioVenta(double precioBase,
                                          double precioVenta)
            throws ValidacionException {

        if (precioVenta <= 0) {
            throw new ValidacionException(
                    "El precio de venta debe ser mayor que cero.");
        }

        if (precioVenta < precioBase) {
            throw new ValidacionException(
                    "El precio de venta no puede ser menor que el precio base.");
        }
    }

    // ==========================================
    // VALIDAR CANTIDAD
    // ==========================================

    public static void validarCantidad(int cantidad)
            throws ValidacionException {

        if (cantidad < 0) {
            throw new ValidacionException(
                    "La cantidad disponible no puede ser negativa.");
        }
    }

}