package projeto.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import projeto.dao.DaoUsuarioRepository;
import projeto.model.ModelLogin;

@WebServlet("/ServletUsuarioController")
public class ServletUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUsuarioRepository daoUsuario = new DaoUsuarioRepository();
	private ServletGenericUtil servletGenericUtil = new ServletGenericUtil();

	public ServletUsuarioController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String id = null;
		String nomeBusca = null;

		// exemplo vai ser pra ilustrar o uso de ajax e sem ajax
		if (acao.equalsIgnoreCase("deletar") && acao != null && !acao.isEmpty()) {
			id = request.getParameter("id");
			try {
				daoUsuario.deletarUser(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			redirecionar(request, response, "msg", "Excluído com sucesso!", "principal/usuario.jsp");
		} else if (acao.equalsIgnoreCase("deletarajax") && acao != null && !acao.isEmpty()) {
			id = request.getParameter("id");
			try {
				daoUsuario.deletarUser(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.getWriter().write("Excluído via ajax");
			
		} else if (acao.equalsIgnoreCase("buscarEditar") && acao != null && !acao.isEmpty()) {
			id = request.getParameter("id");
			if (id != null && !id.isEmpty()) {
				try {
					ModelLogin usuario = daoUsuario.obterUsuario(Long.parseLong(id), servletGenericUtil.getUserLogadoId(request));
					request.setAttribute("modelLogin", usuario);
					setListaTodosUsuarios(request);
					redirecionar(request, response, "msg", "Usuário em Edição", "principal/usuario.jsp");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (acao.equalsIgnoreCase("buscarUserAjax") && acao != null && !acao.isEmpty()) {
			nomeBusca = request.getParameter("nome_buscar");
			if (nomeBusca != null && !nomeBusca.isEmpty()) {
				try {
					List<ModelLogin> dadosJsonUser = daoUsuario.consultaUsuarioList(nomeBusca, servletGenericUtil.getUserLogadoId(request));
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(dadosJsonUser);
					response.getWriter().write(json);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (acao.equalsIgnoreCase("listarUsers") && acao != null && !acao.isEmpty()) {
			setListaTodosUsuarios(request);
			redirecionar(request, response, "msg", "Lista de Usuários", "/principal/usuario.jsp");
			
//			try {
//				List<ModelLogin> logins = daoUsuario.getTodosUsuarios();
//				request.setAttribute("usuariosLista", logins);
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String login = request.getParameter("login");
		String msg = "";

		ModelLogin cadUserLogin = new ModelLogin();

		try {
			if (id != null && !id.isEmpty()) {
				cadUserLogin.setId(Long.parseLong(id));
			}
			cadUserLogin.setNome(nome);
			cadUserLogin.setEmail(email);
			cadUserLogin.setSenha(senha);
			cadUserLogin.setLogin(login);

			if (!daoUsuario.validaLogin(cadUserLogin.getLogin())) {
				daoUsuario.gravarUsuario(cadUserLogin, servletGenericUtil.getUserLogadoId(request)); // grava
				cadUserLogin = daoUsuario.obterUsuario(cadUserLogin.getLogin());
				msg = "Usuário salvo com sucesso";
			} else {
				if (daoUsuario.atualizarLogin(cadUserLogin)) {
					msg = "Usuário atualizado com sucesso";
				}
			}

			request.setAttribute("modelLogin", cadUserLogin);
			setListaTodosUsuarios(request);
			redirecionar(request, response, "msg", msg, "principal/usuario.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			redirecionar(request, response, "msg_erro", e.getMessage(), "erro.jsp");
		}

	}
	
	private void setListaTodosUsuarios(HttpServletRequest request) {
		List<ModelLogin> logins;
		try {
			logins = daoUsuario.getTodosUsuarios(servletGenericUtil.getUserLogadoId(request));
			request.setAttribute("usuariosLista", logins);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void redirecionar(HttpServletRequest request, HttpServletResponse response, String variavel, String msg,
			String pagina) throws ServletException, IOException {
		if (variavel != null && !variavel.isEmpty() && msg != null && !msg.isEmpty()) {
			request.setAttribute(variavel, msg);
		}
		request.getRequestDispatcher(pagina).forward(request, response);
	}

}
