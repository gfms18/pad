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
	
	@GetMapping("/externo")
	public String externoHome(Model model) {
		
		List<Documentacao> listaDocumentos = this.documentacaoDAO.findAll();

		model.addAttribute("listaDocumentos", listaDocumentos );
		
		return "homeExterno";
	}
	
	
	
	@PostMapping("/login")
	public String login(Usuario usuario, RedirectAttributes ra, HttpSession session) {
		usuario = this.usuarioDAO.findByLoginAndSenha(usuario.getLogin(), usuario.getSenha());
		
		if(usuario != null && usuario.getTipo().equals(Tipo.INTERNO)) {
			session.setAttribute("usuariologado", usuario);
			return "redirect:/interno";
		} else if(usuario != null && usuario.getTipo().equals(Tipo.EXTERNO)) {
			session.setAttribute("usuarioLogado", usuario);
			return "redirect:/externo";
		} else {
			ra.addFlashAttribute("mensagem", "Login ou senha invalidos.");
			return "redirect:/";
		}
		
		
		
	}
}
