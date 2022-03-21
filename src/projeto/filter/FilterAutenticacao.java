package projeto.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import projeto.connection.SingleConnectionBD;

@WebFilter(urlPatterns = { "/principal/*" })
public class FilterAutenticacao implements Filter {
	private static Connection connection;

	public FilterAutenticacao() {
	}

	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession sess = req.getSession();
			String userLogado = (String) sess.getAttribute("usuario");
			String urlAutenticar = req.getServletPath();

			if (userLogado == null && !urlAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {
				redirecionar("/index.jsp?url=" + urlAutenticar, request, response, "msg", "Favor realizar o login");
				// ?url será setada no index em um input hidden no form
				return; // parar a execução e redirecionar para o login
			} else {
				chain.doFilter(request, response);
			}
			
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				redirecionar("erro.jsp", request, response, "msg_erro", e.getMessage());
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBD.getConnection();
	}

	private void redirecionar(String pagina, ServletRequest req, ServletResponse resp, String var, String texto)
			throws IOException, ServletException {
		RequestDispatcher red = req.getRequestDispatcher(pagina);
		req.setAttribute(var, texto);
		red.forward(req, resp);
	}

}
