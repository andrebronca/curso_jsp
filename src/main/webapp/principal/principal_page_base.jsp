<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
                                        <div class="row">
                                            <!-- jsp include page conteudo_body.jsp -->
                                            <h2>Conteúdo da página base</h2>
                                        </div>
                                    </div>
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
<jsp:include page="javascriptfile.jsp" />
</body>

</html>
 