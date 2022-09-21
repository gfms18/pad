package br.com.via1.pad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.via1.pad.dao.ArquivoDAO;
import br.com.via1.pad.dao.DocumentacaoDAO;
import br.com.via1.pad.models.Documentacao;

@Controller
public class InternoController {

	@Autowired
	private DocumentacaoDAO documentacaoDAO;
	
	@Autowired
	private ArquivoDAO arquivoDAO;
	
	@GetMapping("/interno")
	public String internoHome(Model model) {
		
		List<Documentacao> listaDocumentos = this.documentacaoDAO.findAll();
		
		model.addAttribute("listaDocumentos", listaDocumentos );
		
		return "homeInterno";
	}
	
	@GetMapping("/interno/situacaoDocumento")
	public String situacaoDocumento(Integer id, Model model) {
		Documentacao documentacao = this.documentacaoDAO.findDocumentacaoById(id);
		model.addAttribute("documentacao", documentacao);
		return "situacaoInterno";
	}
	
	@PostMapping("/salvarSituacao")
	public String salvarSituação(Documentacao documentacao) {
		
		this.documentacaoDAO.save(documentacao);
		
		return "redirect:/interno";
	}
	
}
