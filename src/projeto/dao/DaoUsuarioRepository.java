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

public class DaoUsuarioRepository {
	private Connection connection;
	private static final String TABELA = "model_login";

	public DaoUsuarioRepository() {
		connection = SingleConnectionBD.getConnection();
	}

	public boolean gravarUsuario(ModelLogin ml, Long userIdLogado) throws Exception {
		boolean gravado = false;
		String sql = "insert into " + TABELA + " (nome, email, login, senha, usuario_id) values (?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		if (ml != null && userIdLogado != null) {
			try {
				ps = connection.prepareStatement(sql);
				ps.setString(1, ml.getNome());
				ps.setString(2, ml.getEmail());
				ps.setString(3, ml.getLogin());
				ps.setString(4, ml.getSenha());
				ps.setLong(5, userIdLogado);
				ps.execute();
				connection.commit();
				gravado = true;
			} catch (SQLException e) {
				Utilitario.localSqlExceptionMetodo(new Object(){}.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
				e.printStackTrace();
				connection.rollback();
			} finally {
				if (ps != null)
					ps.close();
			}
		}

		return gravado;
	}

	public ModelLogin obterUsuario(String login, Long idUserLogado) throws Exception {
		ModelLogin ml = null;
		String sql = "select * from " + TABELA + " where login = ? and usuario_id = ? and useradmin is false"; // login
																												// é
																												// único
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (login != null && !login.isEmpty() && idUserLogado != null) {
			try {
				ps = connection.prepareStatement(sql);
				ps.setString(1, login);
				ps.setLong(2, idUserLogado);
				rs = ps.executeQuery();
				while (rs.next()) {
					ml = new ModelLogin();
					ml.setId(rs.getLong("id"));
					ml.setNome(rs.getString("nome"));
					ml.setEmail(rs.getString("email"));
					ml.setLogin(rs.getString("login"));
					ml.setSenha(rs.getString("senha"));
				}
			} catch (SQLException e) {
				Utilitario.localSqlExceptionMetodo(new Object(){}.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
				e.printStackTrace();
			} finally {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			}
		}
		return ml;
	}
	
	public ModelLogin obterUsuario(String login) throws Exception {
		ModelLogin ml = null;
		String sql = "select * from " + TABELA + " where login = ?"; // login
																												// é
																												// único
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (login != null && !login.isEmpty()) {
			try {
				ps = connection.prepareStatement(sql);
				ps.setString(1, login);
				rs = ps.executeQuery();
				while (rs.next()) {
					ml = new ModelLogin();
					ml.setId(rs.getLong("id"));
					ml.setNome(rs.getString("nome"));
					ml.setEmail(rs.getString("email"));
					ml.setLogin(rs.getString("login"));
					ml.setSenha(rs.getString("senha"));
				}
			} catch (SQLException e) {
				Utilitario.localSqlExceptionMetodo(new Object(){}.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
				e.printStackTrace();
			} finally {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			}
		}
		return ml;
	}

	public ModelLogin obterUsuario(Long id, Long idUserLogado) throws Exception {
		ModelLogin ml = null;
		String sql = "select * from " + TABELA + " where id = ? and useradmin is false and usuario_id = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (id != null && idUserLogado != null) {
			try {
				ps = connection.prepareStatement(sql);
				ps.setLong(1, id);
				ps.setLong(2, idUserLogado);
				rs = ps.executeQuery();
				while (rs.next()) {
					ml = new ModelLogin();
					ml.setId(rs.getLong("id"));
					ml.setNome(rs.getString("nome"));
					ml.setEmail(rs.getString("email"));
					ml.setLogin(rs.getString("login"));
					// ml.setSenha(rs.getString("senha"));
				}
			} catch (SQLException e) {
				Utilitario.localSqlExceptionMetodo(new Object(){}.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
				e.printStackTrace();
			} finally {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			}
		}
		return ml;
	}

	public List<ModelLogin> consultaUsuarioList(String nome, Long idUserLogado) throws Exception {
		List<ModelLogin> loginLista = new ArrayList<ModelLogin>();
		String sql = "select id, nome, email, login, senha from " + TABELA
				+ " where lower(nome) like concat('%',lower(?),'%') and usuario_id = ? and not useradmin order by nome";
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (nome != null && !nome.isEmpty() && idUserLogado != null) {
			try {
				ps = connection.prepareStatement(sql);
				ps.setString(1, nome);
				ps.setLong(2, idUserLogado);
				// ps.setString(1, "%"+ nome +"%"); //não usar o concat
				rs = ps.executeQuery();
				while (rs.next()) {
					ModelLogin ml = new ModelLogin();
					ml.setId(rs.getLong("id"));
					ml.setNome(rs.getString("nome"));
					ml.setEmail(rs.getString("email"));
					ml.setLogin(rs.getString("login"));
					// ml.setSenha(rs.getString("senha"));
					loginLista.add(ml);
				}
			} catch (SQLException e) {
				Utilitario.localSqlExceptionMetodo(new Object(){}.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
				e.printStackTrace();
			} finally {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			}
		}

		return loginLista;
	}

	public List<ModelLogin> getTodosUsuarios(Long idUserLogado) throws Exception {
		List<ModelLogin> loginLista = new ArrayList<ModelLogin>();
		String sql = "select id, nome, email, login, senha from " + TABELA + " where usuario_id = ? and useradmin is false order by id";
		PreparedStatement ps = null; // Era statment
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(sql);
			ps.setLong(1, idUserLogado);
			rs = ps.executeQuery();
			while (rs.next()) {
				ModelLogin ml = new ModelLogin();
				ml.setId(rs.getLong("id"));
				ml.setNome(rs.getString("nome"));
				ml.setEmail(rs.getString("email"));
				ml.setLogin(rs.getString("login"));
				// ml.setSenha(rs.getString("senha"));
				loginLista.add(ml);
			}
		} catch (SQLException e) {
			Utilitario.localSqlExceptionMetodo(new Object(){}.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}

		return loginLista;
	}

	public boolean atualizarLogin(ModelLogin model) throws Exception {
		boolean atualizado = false;
		String sql = "update " + TABELA + " set nome=?, email=?, login=?, senha=? where id=?";
		PreparedStatement ps = null;

		if (model != null) {
			try {
				ps = connection.prepareStatement(sql);
				ps.setString(1, model.getNome());
				ps.setString(2, model.getEmail());
				ps.setString(3, model.getLogin());
				ps.setString(4, model.getSenha());
				ps.setLong(5, model.getId());
				ps.execute();
				connection.commit();
				atualizado = true;
			} catch (SQLException e) {
				Utilitario.localSqlExceptionMetodo(new Object(){}.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
				e.printStackTrace();
				connection.rollback();
			} finally {
				if (ps != null)
					ps.close();
			}
		}
		return atualizado;
	}

	public boolean deletarUser(String id) throws Exception {
		boolean excluiu = false;
		String sql = "delete from " + TABELA + " where id = ? and useradmin is false"; // não permite deletar user tipo
																						// admin
		PreparedStatement ps = null;
		Long idUser;
		if (id != null && !id.isEmpty()) {
			idUser = Long.parseLong(id);

			try {
				ps = connection.prepareStatement(sql);
				ps.setLong(1, idUser);
				ps.execute();
				connection.commit();
				excluiu = true;
			} catch (SQLException e) {
				Utilitario.localSqlExceptionMetodo(new Object(){}.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
				e.printStackTrace();
				connection.rollback();
			} finally {
				if (ps != null)
					ps.close();
			}
		}
		return excluiu;
	}

	public boolean validaLogin(String login) throws Exception {
		boolean existe = false;
		String sql = "select * from " + TABELA + " where login = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, login);
			rs = ps.executeQuery();
			while (rs.next()) {
				existe = true;
			}
		} catch (SQLException e) {
			Utilitario.localSqlExceptionMetodo(new Object(){}.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}

		return existe;
	}
	

}
