package floristeria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.StatementEventListener;

public class Floristeria {

	static Connection conexion;

	public static void main(String[] args) {
		try {

			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/floristeria", "root", "nina1971");
			menuPrincipal();
			conexion.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
/**
 * 
 * Método que abre menú principal.
 * 
 * @throws SQLException
 */
	public static void menuPrincipal() throws SQLException {
		byte opcion;
		boolean exit = false;
		do {
			Console.writeln("*****Menu principal*****");
			Console.writeln("0.salir de la aplicacion.");
			Console.writeln("1.Añadir productos al stock.");
			Console.writeln("2.Añadir nueva flor.");
			Console.writeln("3.Retirar para venta productos.");
			Console.writeln("4.Retirar producto de la tienda.");
			Console.writeln("5.Stock de productos.");
			Console.writeln("6.Modificar precios.");
			Console.writeln("7.Mostrar ventas.");
			opcion = Console.readInt("Introduce una opcion: ");
			switch (opcion) {
			case 0:
				Console.writeln("Has salido del menú añadir.");
				exit = true;
				break;
			case 1:
				añadirProducto();
				break;
			case 2:
				añadirNuevaFlor();
				break;
			case 3:
				retirarProducto();
				break;
			case 4:
				retirarProductoTienda();
				break;
			case 5:
				mostrarStock();
				break;
			case 6:
				modificarPrecios();
				break;
			case 7:
				mostrarVentas();
				break;
			default:
				Console.writeError("Debes introducir una opción entre 0 y 5.");
				break;
			}
		} while (!exit);

	}
/**
 * 
 * Método que añade producto a base de datos Floristeria en la tabla stock
 * 
 * @throws SQLException
 */
	public static void añadirProducto() throws SQLException {
		boolean exit = false;
		int nuevaCantidad = 0;
		int idProducto;
		String sql = "UPDATE Stock SET cantidad=cantidad+? WHERE idProducto=?";

		do {
			Console.writeln("0.Salir del menú añadir");
			idProducto = Console.readInt("Introduce el id del producto a añadir");
			if (idProducto == 0) {
				exit = true;

			} else {
				nuevaCantidad = Console.readInt("Introduce la cantidad a añadir");
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, nuevaCantidad);
				sentencia.setInt(2, idProducto);
				sentencia.executeUpdate();
			}
		} while (!exit);
	}
	
	/**
	 * 
	 * Método que añade una nueva flor a la base de datos.
	 */

	public static void añadirNuevaFlor() {
		try {
			String sql1 = null;
			String sql2 = null;
			String sql3 = null;
			String descripcion = null;
			String color = null;
			int cantidad = 0;
			int id = 0;
			double precio;
			sql1 = "INSERT INTO Flores (color) VALUES (?)";
			sql2 = "SELECT idFlores FROM Flores WHERE color=?";
			sql3 = "INSERT INTO Stock (cantidad,idFlores,descripcion,precio) VALUES (?,?,?,?)";
			color = Console.readString("Introduce color: ");
			descripcion = "Flor " + color;
			precio = Console.readDouble("Introduce precio del producto: ");
			cantidad = Console.readInt("Introduce una cantidad de producto a añadir: ");
			PreparedStatement sentencia1 = conexion.prepareStatement(sql1);
			sentencia1.setString(1, color);
			sentencia1.execute();
			PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
			sentencia2.setString(1, color);
			ResultSet rs = sentencia2.executeQuery();
			rs.next();
			id = rs.getInt(1);
			PreparedStatement sentencia3 = conexion.prepareStatement(sql3);
			sentencia3.setInt(1, cantidad);
			sentencia3.setInt(2, id);
			sentencia3.setString(3, descripcion);
			sentencia3.setDouble(4, precio);
			sentencia3.execute();
		} catch (SQLException e) {
			Console.writeError(
					"Probablemente has introducido un registro del mismo tipo.No se admiten tipos repetidos");
		}
	}
/**
 * 
 * Método que retira un producto de la tabla Stock y lo añade a la clase ticket
 * 
 * @throws SQLException
 */
	public static void retirarProducto() throws SQLException {

		boolean exit = false;
		double importe = 0;
		double importeParcial=0;
		double precio;
		int cantidadStock = 0;
		int cantidad = 0;
		int opcion;
		int idProducto;
		int idTicket = 0;
		
		String sql = "INSERT INTO DetalleTicket(idtickets,idProducto,cantidad,importeparcial,precio) VALUES(?,?,?,?,?)";
		String sql1 = "INSERT INTO tickets (importe,fecha) VALUES (?,?)";
		String sql11 = "SELECT idtickets FROM tickets WHERE importe=0";
		String sql2 = "SELECT idProducto,descripcion,cantidad FROM Stock WHERE idProducto=?";
		String sql3 = "UPDATE Stock SET cantidad=cantidad-? WHERE idProducto=?";
		String sql4 = "SELECT precio FROM Stock WHERE idProducto=?";
		String sql5 = "UPDATE tickets SET importe=? WHERE idtickets=?";
		
		
		opcion = Console.readInt("Crear ticket de venta?:(0 para salir,1 para crear ticket)");
		if (opcion == 0) {
			Console.writeln("Has salido sin crear venta");
		} else {
			PreparedStatement sentencia1 = conexion.prepareStatement(sql1);
			sentencia1.setDouble(1, importe);
			sentencia1.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
			sentencia1.execute();
			PreparedStatement sentencia11 = conexion.prepareStatement(sql11);
			ResultSet rs11 = sentencia11.executeQuery();
			rs11.next();
			idTicket = rs11.getInt(1);
			do {
				mostrarStock();
				Console.writeln("0.Salir del menú retirar");
				idProducto = Console.readInt("Introduce el id del producto a retirar: ");
				if (idProducto == 0) {
					exit = true;
				} else {
					PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
					sentencia2.setInt(1, idProducto);
					ResultSet rs = sentencia2.executeQuery();
					rs.next();
					cantidadStock = rs.getInt(3);
					cantidad = Console.readInt("Cantidad de producto a retirar: ");
					if (cantidad > cantidadStock) {
						Console.writeError("Has introducido una cantidad del producto superior a su stock");
					} else {
						PreparedStatement sentencia3 = conexion.prepareStatement(sql3);
						sentencia3.setInt(1, cantidad);
						sentencia3.setInt(2, idProducto);
						sentencia3.executeUpdate();
						PreparedStatement sentencia4 = conexion.prepareStatement(sql4);
						sentencia4.setInt(1, idProducto);
						ResultSet rs2 = sentencia4.executeQuery();
						rs2.next();
						precio=rs2.getDouble(1);
						importe += rs2.getDouble(1) * cantidad;
						importeParcial=rs2.getDouble(1)*cantidad;
						Console.writeln("el importe es: " + importe);
						PreparedStatement sentencia5 = conexion.prepareStatement(sql5);
						sentencia5.setDouble(1, importe);
						sentencia5.setInt(2, idTicket);
						sentencia5.executeUpdate();
						PreparedStatement sentencia = conexion.prepareStatement(sql);					
						sentencia.setInt(1, idTicket);
						sentencia.setInt(2, idProducto);
						sentencia.setInt(3, cantidad);
						sentencia.setDouble(4,importeParcial);
						sentencia.setDouble(5,precio);
						sentencia.executeUpdate();
					}
				}
			} while (!exit);

		}
	}
/**
 * Método que retira un producto de la floristeria.
 * 
 * @throws SQLException
 */
	public static void retirarProductoTienda() throws SQLException {

		int idProducto;		
		String sql="UPDATE Stock SET fueraCatalogo=1,cantidad=0 WHERE idProducto=?";		
		idProducto = Console.readInt("Introduce el id del producto a eliminar: ");
		PreparedStatement sentencia = conexion.prepareStatement(sql);
		sentencia.setInt(1, idProducto);
		sentencia.execute();
		

	}
/**
 * Método que muestra stock total de los productos detallado,con el valor total del stock.
 * 
 * 
 * @throws SQLException
 */
	public static void mostrarStock() throws SQLException {
		String sql = "SELECT idProducto,cantidad,precio,cantidad*precio,descripcion FROM Stock  WHERE fueraCatalogo=0 ORDER BY Descripcion desc";
		Console.writeln("           Stock de productos");
		Console.writeln("----------------------------------------------------------");
		Console.writeln("idProducto Cantidad  Precio    Valor    Descripcion ");
		Console.writeln("----------------------------------------------------------");
		Statement sentencia = conexion.createStatement();
		ResultSet rs = sentencia.executeQuery(sql);
		while (rs.next()) {
			System.out.printf("%5s", rs.getInt(1));
			System.out.printf("%10s", rs.getInt(2));
			System.out.printf("%10s", rs.getDouble(3));
			System.out.printf("%11s", rs.getDouble(4));
			System.out.printf("     %10s", rs.getString(5));
			System.out.println();
		}
		Console.writeln("------------------------------------------------------------");
		Console.writeln("VALOR TOTAL DEL STOCK: " + valorStock());
		Console.writeln("------------------------------------------------------------");
	}

	/**
	 * 
	 * Muestra que cálcula el valor total del Stock en la floristeria
	 * 
	 * @return Retorna el valor total del Stock.
	 * @throws SQLException
	 */
	public static double valorStock() throws SQLException {
		String sql = "SELECT cantidad,precio FROM Stock";
		double valorStock = 0;
		Statement sentencia = conexion.createStatement();
		ResultSet rs = sentencia.executeQuery(sql);
		while (rs.next()) {
			valorStock += rs.getInt(1) * rs.getDouble(2);
		}
		return valorStock;
	}

	/**
	 * 
	 * Método que cálcula el valor total de las ventas realizadas hasta el momento
	 * 
	 * @return valor total de las ventas.
	 * @throws SQLException
	 */
	public static double totalVentas() throws SQLException {
		String sql = "SELECT importe FROM tickets";
		double totalVentas = 0;
		Statement sentencia = conexion.createStatement();
		ResultSet rs = sentencia.executeQuery(sql);
		while (rs.next()) {
			totalVentas += rs.getDouble(1);
		}
		return totalVentas;

	}
	
	/**
	 * 
	 * Método que muestra las ventas realizadas totales	 * 
	 * 
	 * @throws SQLException
	 */

	public static void mostrarVentas() throws SQLException {

		String sql1 = "SELECT * FROM tickets";
		boolean exit = false;
		int idticket;
		int opcion = 0;
		double total = 0;
		double totalVentas = totalVentas();
		Statement sentencia = conexion.createStatement();
		ResultSet rs1 = sentencia.executeQuery(sql1);
		Console.writeln("           Historial de ventas ");
		Console.writeln("-----------------------------------------------");
		Console.writeln(" idticket    importe(euros)       fecha ");
		Console.writeln("-----------------------------------------------");
		while (rs1.next()) {
			System.out.printf("%5s", rs1.getInt(1));
			System.out.printf("%15s", rs1.getDouble(2));
			System.out.print("             " + rs1.getDate(3));
			System.out.println();
			opcion = rs1.getInt(1);
		}
		Console.writeln("-------------------------------------------------");
		Console.writeln("VENTAS TOTALES:" + totalVentas);
		Console.writeln("-------------------------------------------------");
		do {
			idticket = Console.readInt("Introduce el id del ticket a generar(0 para salir):");
			if (idticket == 0) {
				Console.writeln("Has salido mostrar ventas");
				exit = true;
			} else if (idticket < 1 || idticket > opcion) {
				Console.writeError("valor fuera de rango");
			} else {
				String sql2 = "SELECT importe FROM tickets WHERE idtickets=?";
				PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
				sentencia2.setInt(1, idticket);
				ResultSet rs2 = sentencia2.executeQuery();
				rs2.next();
				total = rs2.getDouble(1);
				String sql3 = "SELECT dt.cantidad,s.descripcion,dt.precio,dt.importeparcial FROM tickets t JOIN DetalleTicket dt ON t.idtickets=dt.idtickets JOIN Stock s ON s.idProducto=dt.idProducto WHERE t.idtickets=?";
				PreparedStatement sentencia3 = conexion.prepareStatement(sql3);
				sentencia3.setInt(1, idticket);				
				ResultSet rs3 = sentencia3.executeQuery();
				Console.writeln("               ticket de venta");
				Console.writeln("-------------------------------------------");
				Console.writeln("Cantidad   Precio   Importe     Concepto");
				Console.writeln("-------------------------------------------");				
				while (rs3.next()) {
					
					System.out.printf("%2s", rs3.getInt(1));					
					System.out.printf("%13s", rs3.getDouble(3));
					System.out.printf("%10s", rs3.getDouble(4));
					System.out.print("    " + rs3.getString(2));
					System.out.println();
				}
				Console.writeln("------------------------------------------");
				Console.writeln("TOTAL..................................." + total + "€");

			}
		} while (!exit);
	}

	/**
	 * Método para modificar precios de los productos.
	 * 
	 * @throws SQLException
	 */
	public static void modificarPrecios() throws SQLException {
		boolean exit = false;
		int nuevoPrecio = 0;
		int idProducto;
		String sql = "UPDATE Stock SET precio=? WHERE idProducto=?";
		
		do {
			Console.writeln("0.Salir del menú añadir.");
			idProducto = Console.readInt("Introduce el id del producto a modificar precio: ");
			if (idProducto == 0) {
				exit = true;

			} else {
				nuevoPrecio = Console.readInt("Introduce el nuevo precio: ");
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, nuevoPrecio);
				sentencia.setInt(2, idProducto);
				sentencia.executeUpdate();
			}
		} while (!exit);

	}
}
