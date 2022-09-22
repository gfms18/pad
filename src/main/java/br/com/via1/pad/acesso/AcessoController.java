package br.com.via1.pad.acesso;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.via1.pad.dao.DocumentacaoDAO;
import br.com.via1.pad.dao.UsuarioDAO;
import br.com.via1.pad.models.Documentacao;
import br.com.via1.pad.models.Tipo;
import br.com.via1.pad.models.Usuario;

@Controller
public class AcessoController {

	@Autowired
	private UsuarioDAO usuarioDAO; 
	
	@Autowired
	private DocumentacaoDAO documentacaoDAO;
	
	@GetMapping("/")
	public String retornarLogin() { 
		return "login";
	}
	
	@PostMapping("/login")  
	public String login(String login, String senha, RedirectAttributes ra, HttpSession session) {
		Usuario usuario = this.usuarioDAO.findByLoginAndSenha(login, senha);
		
		if(usuario != null && usuario.getTipo().equals(Tipo.INTERNO)) {  
			session.setAttribute("usuarioLogado", usuario);
			return "redirect:/interno";
		}
		
		if(usuario != null && usuario.getTipo().equals(Tipo.EXTERNO)) {
			session.setAttribute("usuarioLogado", usuario);
			return "redirect:/externo";
		} else {
			ra.addFlashAttribute("mensagem", "Login ou senha invalidos.");
			return "redirect:/";
		}
		
		
		
	} 
	
	@GetMapping("/acessoNegado")
	public String acessoNegado() {
		return "acesso_negado";
	}
	
	@GetMapping("/sair")
	public String sair(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
