package softapi.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class BancoDados {
	
	public static Connection conecta(String conexao) throws NamingException, SQLException {
		
		InitialContext ctx = new InitialContext();
		BasicDataSource ds = (BasicDataSource) ctx.lookup("java:comp/env/jdbc/" + conexao);
		return ds.getConnection();
		
	}
	
	public static void teste(String conexao) throws NamingException, SQLException {
		
		Connection bd = conecta(conexao);
		bd.close();
		
	}

}
