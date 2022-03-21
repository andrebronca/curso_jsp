<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<jsp:include page="head.jsp" />

<body>

	<jsp:include page="theme_loader.jsp" />

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar_header.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="pcoded_navbar.jsp" />

					<div class="pcoded-content">

						<jsp:include page="page_header.jsp" />

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<!-- jsp include page conteudo_body.jsp -->

										<div class="row">
											<div class="col-sm-12">
												<div class="card">
													<div class="card-block">
														<h5 class="sub-title">Cadastro de Usuário</h5>
													</div>
													<div class="card-block">
														<form id="formUser" class="form-material" action="<%= request.getContextPath() %>/ServletUsuarioController" method="post">
															<input type="hidden" id="acao" name="acao" value="" />
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id" 
																	class="form-control" readonly="readonly" value="${modelLogin.id}"><span
																	class="form-bar"></span><label class="float-label">ID</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	autocomplete="off" required="required" value="${modelLogin.nome}"><span
																	class="form-bar"></span> <label class="float-label">Nome</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="email" id="email"
																	class="form-control" required="required"
																	autocomplete="off" required="required" value="${modelLogin.email}"> <span
																	class="form-bar"></span> <label class="float-label">E-mail</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="login" id="login"
																	class="form-control" required="required"
																	autocomplete="off" required="required" value="${modelLogin.login}"> <span
																	class="form-bar"></span> <label class="float-label">Login</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	autocomplete="off" required="required" value="${modelLogin.senha}"> <span
																	class="form-bar"></span> <label class="float-label">Senha</label>
															</div>
															<button type="button" class="btn waves-effect waves-light hor-grd btn-grd-primary" onclick="limparForm();">Novo</button>
															<button class="btn waves-effect waves-light hor-grd btn-grd-success">Salvar</button>
															<button class="btn waves-effect waves-light hor-grd btn-grd-success" onclick="alert('falta implementar');">Atualizar</button>
															<button type="button" class="btn waves-effect waves-light hor-grd btn-grd-danger" onclick="criaDeleteComAjax();">Excluir</button>
															<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#modalUsuario" onclick="limparPesquisa();">Pesquisar</button>
														</form>
													</div>
												</div>
											</div>
										</div>

									</div>
														<h4 class="sub-title" id="msg">${msg}</h4>
									<!-- Page-body end -->
								</div>
								<!-- lista de usuários embaixo do formulário -->
								<div style="height: 200px; overflow: scroll; width: auto;">
									<table class="table" id="tabelaListaUsuarios">
									  <thead>
									    <tr>
									      <th scope="col">Id</th>
									      <th scope="col">Nome</th>
									      <th scope="col">Editar</th>
									    </tr>
									  </thead>
										<tbody>
											<c:forEach items="${usuariosLista}" var="ml">
												<tr>
													<td><c:out value="${ml.id}"></c:out></td>
													<td><c:out value="${ml.nome}"></c:out></td>
													<td><button type="button" onclick="verEditar(${ml.id});" class="btn btn-outline-primary btn-sm">Ver</button></td>
													<td><a class="btn btn-outline-success" href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
									<span id="totalListaUsuarios"></span>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<jsp:include page="javascriptfile.jsp" />

<!-- Modal tem que ficar aqui em baixo no body-->
<div class="modal fade" id="modalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de usuário</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="input-group mb-3">
				  <input id="nome_buscar" name="nome_buscar" type="text" class="form-control" placeholder="Nome" aria-label="nome" aria-describedby="basic-addon2">
				  <div class="input-group-append">
				    <button class="btn btn-outline-success" type="button" onclick="buscarUsuario();">Pesquisar</button>
				  </div>
				</div>
				<div style="height: 300px; overflow: scroll;">
					<table class="table" id="tabelaUsuarios">
					  <thead>
					    <tr>
					      <th scope="col">Id</th>
					      <th scope="col">Nome</th>
					      <th scope="col">Editar</th>
					    </tr>
					  </thead>
						<tbody></tbody>
					</table>
				</div>
					<span id="totalResultados"></span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
      </div>
    </div>
  </div>
</div>
	
<script type="text/javascript">
	function buscarUsuario(){
		let nome = document.getElementById('nome_buscar').value;
		if (nome != null && nome != '' && nome.trim() != ''){
			let urlAction = document.getElementById('formUser').action;
			$.ajax({
				method: 'get',
				url: urlAction,
				data: 'nome_buscar='+ nome +'&acao=buscarUserAjax',
				success: function(response){
					let json = JSON.parse(response);
					preencheTabelaListaUsuarios(json);
				}
			}).fail(function(xhr, status, errorThrown){
				alert('Erro ao buscar o usuário: '+ xhr.responseText);
			});
		}
	}
	
	/*modal de pesquisa de nomes*/
	function preencheTabelaListaUsuarios(j){
		$("#tabelaUsuarios > tbody > tr").remove();
		let btnValue = "Ver";
		for(let i=0; i < j.length; i++){
			$("#tabelaUsuarios > tbody").append("<tr><td>"+ j[i].id 
				+"</td><td>"+ j[i].nome 
				+"</td><td>"
				+'<button onclick="verEditar('+ j[i].id +');" type="button" '
					+'class="btn btn-outline-primary btn-sm btn-round">'
					+ btnValue 
					+'</button>'
				+"</td></tr>");
		}
		document.getElementById('totalResultados').textContent = 'Total de registros: '+ j.length;
	}
	
	function verEditar(id){
		//envia o id para a servlet, consulta o objeto, retorna json para preencher o formulário.
		let urlAction = document.getElementById('formUser').action;
		window.location.href = urlAction +'?acao=buscarEditar&id='+ id;
	}
	
	function limparPesquisa(){
		document.getElementById('nome_buscar').value = '';
	}
	
	function criaDeleteComAjax(){
		if(confirm('Deseja excluir o usuário via ajax?')){
			let urlAction = document.getElementById('formUser').action;
			let idUser = document.getElementById('id').value;
			$.ajax({
				method: 'get',
				url: urlAction,
				data: 'id='+ idUser +'&acao=deletarajax',
				success: function (response){	
					limparForm();
					document.getElementById('msg').textContent = response;
				}
			}).fail(function(xhr, status, errorThrown){
				alert('Erro ao excluir usuário via ajax: '+ xhr.responseText);
			});
		}
	}	
	
	function limparForm() {
		let elementos = document.getElementById('formUser').elements;
		
		for(i = 0; i < elementos.length; i++){
			elementos[i].value = '';
		}
	}
	
	function criarDelete(){
		if (confirm('Deseja excluir o usuário?')){
			document.getElementById('formUser').method = 'get'; 
			document.getElementById('acao').value = 'deletar';
			document.getElementById('formUser').submit();
		}
	}
</script>

</body>

</html>
