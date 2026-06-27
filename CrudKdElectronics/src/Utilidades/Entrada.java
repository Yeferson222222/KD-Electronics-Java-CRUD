package Utilidades;

import Excepciones.ValidacionException;
import java.util.Scanner;

public class Entrada {

    private static final Scanner entrada = new Scanner(System.in);

    // Expresiones regulares
    private static final String REGEX_DECIMAL = "^\\d+(\\.\\d{1,2})?$";
    private static final String REGEX_ENTERO = "^\\d+$";

    /**
     * Lee un texto.
     */
    public static String leerTexto(String mensaje) throws ValidacionException {

        System.out.print(mensaje + " ");

        String valor = entrada.nextLine().trim();

        if (valor.isEmpty()) {
            throw new ValidacionException("El campo no puede estar vacío.");
        }

        return valor;
    }

    /**
     * Lee un número decimal y valida el formato mediante Expresiones
     * Regulares antes de convertirlo.
     */
    public static double leerDouble(String mensaje)
            throws ValidacionException {

        System.out.print(mensaje + " ");

        String valor = entrada.nextLine().trim();

        if (valor.isEmpty()) {
            throw new ValidacionException("El campo no puede estar vacío.");
        }

        if (!valor.matches(REGEX_DECIMAL)) {
            throw new ValidacionException(
                    "Debe ingresar un número decimal válido (ejemplo: 1500 o 1500.50).");
        }

        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new ValidacionException("Número decimal inválido.");
        }
    }

    /**
     * Lee un número entero y valida el formato mediante Expresiones
     * Regulares antes de convertirlo.
     */
    public static int leerEntero(String mensaje)
            throws ValidacionException {

        System.out.print(mensaje + " ");

        String valor = entrada.nextLine().trim();

        if (valor.isEmpty()) {
            throw new ValidacionException("El campo no puede estar vacío.");
        }

        if (!valor.matches(REGEX_ENTERO)) {
            throw new ValidacionException(
                    "Debe ingresar únicamente números enteros positivos.");
        }

        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new ValidacionException("Número entero inválido.");
        }
    }

}