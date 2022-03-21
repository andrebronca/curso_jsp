package test;

import java.sql.SQLException;

import org.junit.Test;

import projeto.dao.DaoUsuarioRepository;
import projeto.model.ModelLogin;

public class TestBD {

	@Test
	public void intiValidaLogin() {
		ModelLogin ml = new ModelLogin();
		ml.setLogin("josi2");
		DaoUsuarioRepository dao = new DaoUsuarioRepository();
		try {
			ml = dao.obterUsuario(ml.getLogin());
			if (ml != null) {
				System.out.println("Login existe");
			} else {
				System.out.println("Login não existe");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void atualizarLogin() {
		ModelLogin ml = new ModelLogin();
		DaoUsuarioRepository dao = new DaoUsuarioRepository();
		try {
			ml = dao.obterUsuario("andre");
			ml.setSenha("admin");
			ml.setNome("Andre R. B.");
			if (dao.atualizarLogin(ml)) {
				System.out.println("Atualizado com sucesso");
			} else {
				System.out.println("Erro ao atualizar");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deletarUsuario() throws Exception {
		DaoUsuarioRepository dao = new DaoUsuarioRepository();
		String id = "19";
		try {
			boolean res = dao.deletarUser(id);
			if (res) {
				System.out.println("excluído com sucesso");
			} else {
				System.out.println("nao foi excluído");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
