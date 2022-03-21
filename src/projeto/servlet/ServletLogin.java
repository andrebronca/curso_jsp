package projeto.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import projeto.dao.DaoLoginRepository;
import projeto.model.ModelLogin;

@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" })
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoLoginRepository daoLogin;

	public ServletLogin() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate();
			redirecionar(request, response, null, null, "/index.jsp");
		} else {
			doPost(request, response);
		}
		
		
		//redirecionar(request, response, "msg", "Acesso não autorizado", "/index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		daoLogin = new DaoLoginRepository();

		try {
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				ModelLogin ml = new ModelLogin();
				ml.setLogin(login);
				ml.setSenha(senha);

				if (daoLogin.validarAutenticacao(ml)) {

					request.getSession().setAttribute("usuario", ml.getLogin());

					if (url == null || url.equalsIgnoreCase("null")
							|| url.equalsIgnoreCase("/principal/ServletLogin")) {
						url = "/principal/principal.jsp";
					}
					redirecionar(request, response, "msg", "Seja bem-vindo " + ml.getLogin(), url);
				} else {
//					request.getSession().invalidate();
					redirecionar(request, response, "msg", "Acesso não autorizado", "/index.jsp");
				}
			} else {
//				request.getSession().invalidate();
				redirecionar(request, response, "msg", "Login ou senha incorretos", "/index.jsp");
			}
		} catch (ServletException e) {
			e.printStackTrace();
			redirecionar(request, response, "msg_erro", e.getMessage(), "erro.jsp");
		} catch (IOException e) {
			e.printStackTrace();
			redirecionar(request, response, "msg_erro", e.getMessage(), "erro.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			redirecionar(request, response, "msg_erro", e.getMessage(), "erro.jsp");
		}

	}

	private void redirecionar(HttpServletRequest request, HttpServletResponse response, String variavel, String msg,
			String pagina) throws ServletException, IOException {
		if (variavel != null && !variavel.isEmpty() 
				&& msg != null && !msg.isEmpty()) {
			request.setAttribute(variavel, msg);
		}
		request.getRequestDispatcher(pagina).forward(request, response);
	}

}
