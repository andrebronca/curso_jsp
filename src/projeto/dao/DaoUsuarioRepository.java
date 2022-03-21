package projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projeto.connection.SingleConnectionBD;
import projeto.model.ModelLogin;

public class DaoUsuarioRepository {
	private Connection connection;
	private static final String TABELA = "model_login";

	public DaoUsuarioRepository() {
		connection = SingleConnectionBD.getConnection();
	}

	public boolean gravarUsuario(ModelLogin ml) throws Exception {
		boolean gravado = false;
		String sql = "insert into " + TABELA + " (nome, email, login, senha) values (?, ?, ?, ?)";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, ml.getNome());
			ps.setString(2, ml.getEmail());
			ps.setString(3, ml.getLogin());
			ps.setString(4, ml.getSenha());
			ps.execute();
			connection.commit();
			gravado = true;
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} finally {
			if (ps != null)
				ps.close();
		}

		return gravado;
	}

	public ModelLogin obterUsuario(String login) throws Exception {
		ModelLogin ml = null;
		String sql = "select * from " + TABELA + " where login = ?"; // login é único
		PreparedStatement ps = null;
		ResultSet rs = null;
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
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}
		return ml;
	}
	
	public ModelLogin obterUsuario(Long id) throws Exception {
		ModelLogin ml = null;
		String sql = "select * from " + TABELA + " where id = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				ml = new ModelLogin();
				ml.setId(rs.getLong("id"));
				ml.setNome(rs.getString("nome"));
				ml.setEmail(rs.getString("email"));
				ml.setLogin(rs.getString("login"));
				//ml.setSenha(rs.getString("senha"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}
		return ml;
	}	
	
	public List<ModelLogin> consultaUsuarioList(String nome) throws Exception{
		List<ModelLogin> loginLista = new ArrayList<ModelLogin>();
		String sql = "select id, nome, email, login, senha from "+ TABELA +" where lower(nome) like concat('%',lower(?),'%') order by nome";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, nome);
			//ps.setString(1, "%"+ nome +"%"); //não usa o concat
			rs = ps.executeQuery();
			while (rs.next()) {
				ModelLogin ml = new ModelLogin();
				ml.setId(rs.getLong("id"));
				ml.setNome(rs.getString("nome"));
				ml.setEmail(rs.getString("email"));
				ml.setLogin(rs.getString("login"));
				//ml.setSenha(rs.getString("senha"));
				loginLista.add(ml);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
		}
		
		return loginLista;
	}
	
	public List<ModelLogin> getTodosUsuarios() throws Exception{
		List<ModelLogin> loginLista = new ArrayList<ModelLogin>();
		String sql = "select id, nome, email, login, senha from "+ TABELA +" order by id";
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = connection.createStatement();
			//ps.setString(1, "%"+ nome +"%"); //não usa o concat
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				ModelLogin ml = new ModelLogin();
				ml.setId(rs.getLong("id"));
				ml.setNome(rs.getString("nome"));
				ml.setEmail(rs.getString("email"));
				ml.setLogin(rs.getString("login"));
				//ml.setSenha(rs.getString("senha"));
				loginLista.add(ml);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) rs.close();
			if (stm != null) stm.close();
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
				e.printStackTrace();
			} finally {
				if (ps != null)
					ps.close();
			}
		}
		return atualizado;
	}
	
	

	public boolean deletarUser(String id) throws Exception {
		boolean excluiu = false;
		String sql = "delete from " + TABELA + " where id = ?";
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
