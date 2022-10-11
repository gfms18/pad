package br.com.via1.pad.acesso;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
	public String login(String login, String senha, RedirectAttributes ra, HttpSession session, HttpServletRequest request) {
		Usuario usuario = this.usuarioDAO.findByLoginAndSenha(login, senha);
		
		if(usuario != null && usuario.getTipo().equals(Tipo.INTERNO)) {
			session.setAttribute("usuarioLogado", usuario);
			return "redirect:/interno";
		} else if(usuario != null && usuario.getTipo().equals(Tipo.EXTERNO)) {
			session.setAttribute("usuarioLogado", usuario);
			return "redirect:/externo";
		} else {
			ra.addFlashAttribute("mensagem", "Login ou senha invalidos.");
			return "redirect:/";
			//
		}
		
	} 
	
	
	@GetMapping("/externo/alterarSenha")
	public String alterarSenha() {
		return "alterar_Senha";
	}
	@PostMapping("/externo/salvarSenha")
	public String novaSenha(String senha, String senhaNova, String confsenha, RedirectAttributes ra,HttpServletRequest request) {
		
		Usuario usu = (Usuario) request.getSession().getAttribute("usuarioLogado");
		
		if (usu.getSenha().equals(senha)) {
			if(senhaNova.equals(confsenha)) {
				usu.setSenha(senhaNova);
				usu.setPrimeiroAcesso(false);
				this.usuarioDAO.save(usu);
				ra.addFlashAttribute("mensagem", "Senhas alteradas com sucesso.");
				System.out.println("foi");
				return "redirect:/externo";
			
			} else {
				ra.addFlashAttribute("mensagem", "Senhas diferentes");
				System.out.println("foi");			
				return "redirect:/externo/alterarSenha";
				
			}
			
		} 
		ra.addFlashAttribute("mensagem", "Senhas diferentes");
		System.out.println("foi");			
		return "redirect:/externo/alterarSenha";
			
		
	}
	
	@GetMapping("/interno/alterarSenha")
	public String alterarSenhaInterno() {
		return "alterar_senha_interno";
	}
	@PostMapping("/interno/salvarSenha")
	public String novaSenhaInterno(String senha, String senhaNova, String confsenha, RedirectAttributes ra,HttpServletRequest request) {
		
		Usuario usu = (Usuario) request.getSession().getAttribute("usuarioLogado");
		
		if (usu.getSenha().equals(senha)) {
			if(senhaNova.equals(confsenha)) {
				usu.setSenha(senhaNova);
				usu.setPrimeiroAcesso(false);
				this.usuarioDAO.save(usu);
				ra.addFlashAttribute("mensagem", "Senhas alteradas com sucesso.");
				System.out.println("foi");
				return "redirect:/interno";
			
			} else {
				ra.addFlashAttribute("mensagem", "Senhas diferentes");
				System.out.println("foi");			
				return "redirect:/interno/alterarSenha";
				
			}
			
		} 
		ra.addFlashAttribute("mensagem", "Senhas diferentes");
		System.out.println("foi");			
		return "redirect:/interno/alterarSenha";
			
		
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
