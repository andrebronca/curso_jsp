<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<title>Curso JSP</title>
<style type="text/css">
.posicao {
	position: absolute;
	left: 33%;
	right: 33%;
}
.top_40 {
	top: 40%;
}
.top_30 {
	top: 30%;
}
.top_20 { top: 20% }

</style>
</head>
<body>
	<div class="container">
		<h3 class="text-center posicao top_30">Bem-vindo ao curso de JSP</h3>
		
		<p style="color: #B22222" 
			class="top_20 posicao">${msg}</p>
		
		<form action="<%= request.getContextPath() %>/ServletLogin" method="post" 
			class="row g-3 needs-validation posicao top_40">
			<input type="hidden" value="<%=request.getParameter("url")%>"	name="url" id="url" />
			
			<div class="mb-3">
				<label for="login" class="form-label">Login</label> <input
					type="text" class="form-control" id="login" name="login" required/>
					<div class="invalid-feedback">Por favor informe o login</div>
					<div class="valid-feedback">Ok</div>
			</div>
			
			<div class="mb-3">
				<label for="senha" class="form-label">Senha</label> <input
					type="password" class="form-control" id="senha" name="senha" required/>
					<div class="invalid-feedback">Por favor informe a senha</div>
			</div>

				<button type="submit" class="btn btn-primary" name="btn_enviar">Acessar</button>

		</form>
	</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	<script type="text/javascript">
	// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
  'use strict'
  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()
	</script>
</body>
</html>