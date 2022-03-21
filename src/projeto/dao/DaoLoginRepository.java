package projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import projeto.connection.SingleConnectionBD;
import projeto.model.ModelLogin;
import projeto.util.Utilitario;

public class DaoLoginRepository {
	private Connection connection;

	public DaoLoginRepository() {
		connection = SingleConnectionBD.getConnection();
	}

	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		boolean valido = false;
		String sql = "select * from model_login where login=? and senha=?";
		System.out.println(sql);
		// testar o código abaixo
		String[] colunas = { "login", "senha" };
		String[] restricoes = { "login", "senha" };
		System.out.println(Utilitario.getSqlSelect("model_login", colunas, restricoes));

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, modelLogin.getLogin());
			ps.setString(2, modelLogin.getSenha());
			rs = ps.executeQuery();
			if (rs.next()) {
				if (!validarSqlInjection(modelLogin.getLogin())) {
					valido = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();
		}

		return valido;
	}

	

	private boolean validarSqlInjection(String arg) {
		boolean temSqlInjection = false;
		List<Character> caracteres = new ArrayList<>();
		char[] char_suspeitos = { '\'', '=', '\"', ';', 'D', 'R', 'O', 'P', 'o', 'r', };
		for (int i = 0; i < arg.length(); i++) {
			for (char c : char_suspeitos) {
				if (arg.charAt(i) == c) {
					caracteres.add(arg.charAt(i));
				}
			}
		}
		if (caracteres.size() > 2) {
			temSqlInjection = true;
		}

		return temSqlInjection;
	}
	
}
