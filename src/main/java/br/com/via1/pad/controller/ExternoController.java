package br.com.via1.pad.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.via1.pad.dao.ArquivoDAO;
import br.com.via1.pad.dao.DocumentacaoDAO;
import br.com.via1.pad.dao.EmpresaDAO;
import br.com.via1.pad.models.Arquivo;
import br.com.via1.pad.models.Documentacao;
import br.com.via1.pad.models.Empresa;
import br.com.via1.pad.models.Usuario;

@Controller

public class ExternoController {
	
	@Autowired
	private DocumentacaoDAO documentacaoDAO;
	
	@Autowired
	private ArquivoDAO arquivoDAO;
	
	
	//pagina
	@GetMapping("/criarDocumentacao")
	public String criarDocumentacao() {
		return "criar_documentacao";
	}
	
	@GetMapping("/externo")
	public String externoHome(Model model, HttpServletRequest request ) { 
		
		List<Documentacao> listaDocumentos = this.documentacaoDAO.findAll();
		
		model.addAttribute("listaDocumentos", listaDocumentos );
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
		model.addAttribute("nome", usuario);
		
		return "homeExterno";
	}

	//pagina
	@GetMapping("/externo/adicionarArquivo")
	public String adicionarArquivo(Integer id, Model model) {
		Documentacao documentacao = this.documentacaoDAO.findDocumentacaoById(id);
		model.addAttribute("documentacao", documentacao);
		model.addAttribute("listaArquivos", arquivoDAO.findAllByDocumentacao(Sort.by("nomeArquivo"),documentacao));
		return "adicionar_arquivo";
	}
	
	//metodo
	@PostMapping("/criarDocumentacao")
	public String criarDocumentacao(Documentacao documentacao){		
		this.documentacaoDAO.save(documentacao);		
		return "redirect:/externo";
	}
	
	@GetMapping("/exibirArquivo/{idArquivo}")
	@ResponseBody
	public byte[] exibirArquivo(@PathVariable("idArquivo") Integer idArquivo){
		Arquivo arquivo = this.arquivoDAO.getOne(idArquivo);
		return arquivo.getArquivo();
	}
	
	//metodo
	@GetMapping("/apagarDocumentacao/{id}")
	public String apagarDocumentacao(Documentacao id) {
		
		for(Arquivo arq : this.arquivoDAO.findAllByDocumentacao(this.documentacaoDAO.getById(id.getId()))) {
			this.arquivoDAO.delete(arq);
		}
		
		this.documentacaoDAO.delete(id);
		return "redirect:/externo";
	}
	
	@GetMapping("/externo/editarDocumento")
	public String editarDocumento(Integer id, Model model) {
		Documentacao documentacao = this.documentacaoDAO.findDocumentacaoById(id);
		model.addAttribute("documentacao", documentacao);
		model.addAttribute("listaArquivos", arquivoDAO.findAllByDocumentacao(Sort.by("nomeArquivo"), documentacao));
		return "editar_documento";
	}
	
	@GetMapping("/externo/situacaoDocumento")
	public String situacaoDocumento(Integer id, Model model) {
		Documentacao documentacao = this.documentacaoDAO.findDocumentacaoById(id);
		model.addAttribute("documentacao", documentacao);
		return "situacao";
	}
	
	//metodo
	@PostMapping("/adicionarArquivo")
	public String adicionarArquivo(Arquivo arquivo, @RequestParam("fileCurriculo") MultipartFile file, Integer id) throws IOException {
		
		System.out.println(id);
		arquivo.setArquivo(file.getBytes());
		arquivo.setNomeOriginalArquivo(file.getOriginalFilename());
		Documentacao documentacao  = this.documentacaoDAO.findDocumentacaoById(id);
		System.out.println(documentacao.getId());
		arquivo.setDocumentacao(documentacao);
		this.arquivoDAO.save(arquivo);
		
		
		return "redirect:/externo/adicionarArquivo/?id=" + id;
	}
	
	@GetMapping("/apagarArquivo")///{idDocument}, String idDocumento /idDocumento=*{id}
	public String apagarArquivo(Arquivo idArquivo, Documentacao id) {
		this.arquivoDAO.delete(idArquivo);
		return "redirect:/externo/editarDocumento/?id=" + id.getId();
	}
	
}
