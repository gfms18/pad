package br.com.via1.pad.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.via1.pad.dao.ArquivoDAO;
import br.com.via1.pad.dao.DocumentacaoDAO;
import br.com.via1.pad.dao.UsuarioDAO;
import br.com.via1.pad.models.Arquivo;
import br.com.via1.pad.models.Documentacao;
import br.com.via1.pad.models.Tipo;
import br.com.via1.pad.models.Usuario;

@Controller
public class InternoController {

	@Autowired
	private DocumentacaoDAO documentacaoDAO;
	
	@Autowired
	private ArquivoDAO arquivoDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@GetMapping("/interno")
	public String internoHome(Model model, HttpServletRequest request) {
		
		List<Usuario> usuarios = this.usuarioDAO.findAll();
		List<Usuario> empresas = new ArrayList<>();
		
		for(Usuario user : usuarios) {
			if(user.getTipo().equals(Tipo.EXTERNO)) {
				empresas.add(user);
			}
		}
		
		model.addAttribute("listaEmpresas", empresas );
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
		model.addAttribute("nome", usuario);
		
		return "homeInterno";
	}
	
	@GetMapping("/interno/acessarDocumentacao/{id}")
	public String acessarDocumentacao(@PathVariable("id") Integer id ,Model model) {
		Usuario usuario = this.usuarioDAO.getOne(id);
		List<Documentacao> listaDocumentacao = this.documentacaoDAO.findAllByUsuario(Sort.by("ano"),usuario);
		model.addAttribute("listaDocumentacao", listaDocumentacao);
		return "documentacao_interno";
	}
	
	@GetMapping("/interno/situacaoDocumento/{id}")
	public String situacaoDocumento(@PathVariable("id") Integer id, Model model) {
		Documentacao documentacao = this.documentacaoDAO.findDocumentacaoById(id);
		model.addAttribute("documentacao", documentacao);
		model.addAttribute("listaArquivos", arquivoDAO.findAllByDocumentacao(Sort.by("nomeArquivo"), documentacao));
		return "situacaoInterno";
	}
	
	@GetMapping("/interno/visualizarpdf/{idArquivo}")
	public String visualizarPdf(@PathVariable("idArquivo") Integer idArquivo, Model model) {
		
		Arquivo arquivo = this.arquivoDAO.getOne(idArquivo);
		model.addAttribute("arquivo", arquivo);
		return "visualizarpdf";
				
	}
	
	@GetMapping("/interno/arquivo/{idArquivo}.pdf")
	@ResponseBody
	public byte[] exibirArquivo(@PathVariable("idArquivo") Integer idArquivo) {
		Arquivo arquivo = this.arquivoDAO.getOne(idArquivo);
		return arquivo.getArquivo();
	}
	
	@PostMapping("/salvarSituacao")
	public String salvarSituação(Documentacao documentacao) {		
		Documentacao documentacao1 = documentacaoDAO.getOne(documentacao.getId());
		documentacao1.setDescricao(documentacao.getDescricao());
		documentacao1.setStatus(documentacao.getStatus());
		this.documentacaoDAO.save(documentacao1);
		return "redirect:/interno/acessarDocumentacao/" + documentacao1.getUsuario().getId();
	}
	
	@GetMapping("/interno/adicionarEmpresa")
	public String paginaAddUsuario() {
		return "adicionar_empresa";
	}
	
	@PostMapping("/addEmpresa")
	public String addEmpresa(Usuario usuario) {
		usuario.setTipo(Tipo.EXTERNO);
		this.usuarioDAO.save(usuario);
		return "redirect:/interno";
	}
}
