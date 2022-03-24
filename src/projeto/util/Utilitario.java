package projeto.util;

public class Utilitario {

	public static String getSqlSelect(String tabela, String[] colunas, String[] restricao) {
		//todo: demais clausulas
		//todo: joins
		String sql = null;
		if (tabela != null) {
			sql = "SELECT ";
			int tamCol = (colunas != null) ? colunas.length : 0;
			int tamRes = (restricao != null) ? restricao.length : 0;
			if (tamCol > 0) {
				int i = 1;
				for (String col : colunas) {
					sql += (i < tamCol)? col +", " : col +" ";
					i++;
				}
			} else {
				sql += "* ";
			}

			sql += "FROM " + tabela +" ";

			if (tamRes > 0) {
				int i = 1;
				for (String res : restricao) {
					sql += (i == 1)? "WHERE "+ res +"=? " : "AND "+ res +"=?";
					i++;
				}
			}
		}

		return sql;
	}
	
	public static void localSqlExceptionMetodo(String classe, String metodo) {
		System.out.println("-------------- EXCEPTION ------------------");
		System.err.println("Erro no pacote.classe.método: ["+ classe +"."+ metodo +"]");
		System.out.println("-------------------------------------------");
	}
}
