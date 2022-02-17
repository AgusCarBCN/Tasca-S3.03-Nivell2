package floristeria;

import java.util.Scanner;

/**
 * La clase Console contiene métodos que permiten introducir datos desde el
 * teclado y verificar que cumplen con un formato determinado.
 * 
 * @author Agustin Carnerero Peña
 *
 */
public class Console {
	private static Scanner enter = new Scanner(System.in);
	private static boolean verify = false;
	private static String intro = "";
	private static byte entero8Bits = 0;
	private static double numeroReal;

	private Console() {
	};

	/**
	 * Imprime un objeto y salta una linea.
	 * 
	 * @param object Objeto que se le pasa como parametro.
	 */
	public static void writeln(Object object) {
		System.out.println(object);
	}

	/**
	 * Imprime un objeto y no salta de línea.
	 * 
	 * @param object Objeto que se le pasa como parametro.
	 */
	public static void write(Object object) {
		System.out.print(object);
	}

	/**
	 * Imprime un mensaje de error.
	 * 
	 * @param message Retorna un mensaje de error
	 */
	public static void writeError(String message) {
		System.err.println(message);
		System.out.println("");
	}

	/**
	 * Método que permite leer un string desde el teclado.No hay restricciones de
	 * carácteres.
	 * 
	 * @param message Mensaje que aparecerá antes de introducir la cadena.
	 * 
	 * @return Retorna un cadena de carácteres.
	 */
	public static String read(String message) {
		write(message);
		intro = enter.nextLine();
		return intro;
	}

	/**
	 * Método que pide un número de tipo byte que representa una opción de un
	 * menú.Valida que sea un byte positivo.
	 * 
	 * @return Retorna un número byte positivo si la validación es correcta.
	 */
	public static byte readInt(String message) {
		verify = false;

		do {
			write(message);
			if (enter.hasNextByte()) {
				entero8Bits = enter.nextByte();
				enter.nextLine();
				verify = true;
			} else {
				enter.nextLine();
				writeError("Debes introducir un número.");
			}
		} while (!verify);
		return entero8Bits;
	}

	public static String readString(String message) {

		do {
			write(message);
			intro = enter.nextLine();
			verify = intro.matches("[A-Z,a-z,ñ,\s]{1,20}");
			if (!verify) {
				writeError("Error al introducir la descripción.");
				writeError("La descripción debe contener letras");

			}
		} while (!verify);
		return intro;
	}
	public static double readDouble(String message) {
		verify = false;

		do {
			write(message);
			if (enter.hasNextDouble()) {
				numeroReal = enter.nextDouble();
				enter.nextLine();
				verify = true;
				if (numeroReal <= 0) {
					writeError("Debes introducir un número positivo mayor que 0.");
				}
			} else {
				enter.nextLine();
				writeError("Debes introducir un número.");
			}
		} while (!verify || numeroReal <= 0);
		return numeroReal;
	}
}
