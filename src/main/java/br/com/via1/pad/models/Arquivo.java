package br.com.via1.pad.models;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Arquivo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idArquivo;
	
	@Lob
	private byte[] arquivo;
	
	@Enumerated
	private NomeArquivo nomeArquivo;
	
	private String nomeOriginalArquivo;
	
	@ManyToOne
	private Documentacao documentacao;
	
	
	public String getNomeOriginalArquivo() {
		return nomeOriginalArquivo;
	}

	public void setNomeOriginalArquivo(String nomeOriginalArquivo) {
		this.nomeOriginalArquivo = nomeOriginalArquivo;
	}

	


	public Integer getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(Integer idArquivo) {
		this.idArquivo = idArquivo;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public NomeArquivo getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(NomeArquivo nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Documentacao getDocumentacao() {
		return documentacao;
	}

	public void setDocumentacao(Documentacao documentacao) {
		this.documentacao = documentacao;
	}
	
	
	
	
}
