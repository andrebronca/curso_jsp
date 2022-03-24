package projeto.servlet;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import projeto.dao.DaoUsuarioRepository;

public class ServletGenericUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	DaoUsuarioRepository dao = new DaoUsuarioRepository();
	
	public Long getUserLogadoId(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Long id = null;
		String userLogado = (String) session.getAttribute("usuario");
		if (userLogado != null && !userLogado.isEmpty()) {
			id = dao.obterUsuario(userLogado).getId();
		}
		return id;
	}
}
