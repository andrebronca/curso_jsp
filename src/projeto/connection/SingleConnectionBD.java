package projeto.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBD {
	private static String url = "jdbc:postgresql://localhost:5434/cursojsp?autoReconnect=true";
	private static String user = "postgres";
	private static String password = "postgres13";
	private static Connection connection = null;
	private static final String CLASS_FOR_NAME = "org.postgresql.Driver";
	private static final String MSG_CONECTADO = "Conectado com sucesso!";

	static {
		conectar(); // de qualquer lugar invocado chama o conectar
	}

	public SingleConnectionBD() {
		conectar();
	}

	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName(CLASS_FOR_NAME);
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false); // para decidir quando fazer o commit na aplicação
				System.out.println(MSG_CONECTADO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
