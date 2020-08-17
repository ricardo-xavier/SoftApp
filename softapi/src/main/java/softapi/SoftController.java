package softapi;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import softapi.dao.BancoDados;

@SpringBootApplication
@RestController
@ConfigurationProperties(prefix = "softapi")
public class SoftController extends SpringBootServletInitializer {

	private String conexao;

	public static void main(String[] args) {
		SpringApplication.run(SoftController.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SoftController.class);
	}

	@GetMapping("/")
	public String versao() {
		return "SoftApi 0.0.1-SNAPSHOT";
	}

	@GetMapping("/teste")
	public String teste() {
		try {
			BancoDados.teste(conexao);
			
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
		return "conectado a " + conexao;
	}

	public String getConexao() {
		return conexao;
	}

	public void setConexao(String conexao) {
		this.conexao = conexao;
	}

}
