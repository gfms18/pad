package br.com.via1.pad.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	// pagina
	@GetMapping("/externo/criarDocumentacao")
	public String criarDocumentacao() {
		return "criar_documentacao";
	}

	@GetMapping("/externo")
	public String externoHome(Model model, HttpServletRequest request) {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
		if(usuario.isPrimeiroAcesso()) {
			return "redirect:externo/alterarSenha";
		}
		List<Documentacao> listaDocumentos = this.documentacaoDAO.findAllByUsuario(Sort.by("ano").descending(),
				usuario);

		model.addAttribute("listaDocumentos", listaDocumentos);

		model.addAttribute("nome", usuario);

		return "homeExterno";
	}

	// pagina
	@GetMapping("/externo/adicionarArquivo")
	public String adicionarArquivo(Integer id, Model model) {
		Documentacao documentacao = this.documentacaoDAO.findDocumentacaoById(id);
		model.addAttribute("documentacao", documentacao);
		model.addAttribute("listaArquivos", arquivoDAO.findAllByDocumentacao(Sort.by("nomeArquivo"), documentacao));
		return "adicionar_arquivo";
	}

	// metodo
	@PostMapping("/externo/criarDocumentacao")
	public String criarDocumentacao(Documentacao documentacao, HttpServletRequest request) {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
		documentacao.setUsuario(usuario);
		
		documentacao.setUltimaAlteracao(LocalDateTime.now());
		
		this.documentacaoDAO.save(documentacao);
		return "redirect:/externo";
	}

	@GetMapping("/exibirArquivo/{idArquivo}")
	@ResponseBody
	public byte[] exibirArquivo(@PathVariable("idArquivo") Integer idArquivo) {
		Arquivo arquivo = this.arquivoDAO.getOne(idArquivo);
		return arquivo.getArquivo();
	}

	// metodo
	@GetMapping("/externo/apagarDocumentacao/{id}")
	public String apagarDocumentacao(Documentacao id) {

		for (Arquivo arq : this.arquivoDAO.findAllByDocumentacao(this.documentacaoDAO.getById(id.getId()))) {
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

	// metodo
	@PostMapping("/externo/adicionarArquivo")
	public String adicionarArquivo(Arquivo arquivo, @RequestParam("fileCurriculo") MultipartFile file, Integer id)
			throws IOException {
		arquivo.setArquivo(file.getBytes());
		arquivo.setNomeOriginalArquivo(file.getOriginalFilename());
		Documentacao documentacao = this.documentacaoDAO.findDocumentacaoById(id);
		arquivo.setDocumentacao(documentacao);
		
		documentacao.setUltimaAlteracao(LocalDateTime.now());
		this.arquivoDAO.save(arquivo);

		return "redirect:/externo/adicionarArquivo/?id=" + id;
	}

	@GetMapping("/externo/apagarArquivo") /// {idDocument}, String idDocumento /idDocumento=*{id}
	public String apagarArquivo(Arquivo idArquivo, Documentacao id) {
		Documentacao documentacao = this.documentacaoDAO.getOne(id.getId());
		
		documentacao.setUltimaAlteracao(LocalDateTime.now());
		this.arquivoDAO.delete(idArquivo);
		return "redirect:/externo/editarDocumento/?id=" + id.getId();
	}

}
