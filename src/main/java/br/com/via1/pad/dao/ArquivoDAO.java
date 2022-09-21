package br.com.via1.pad.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.via1.pad.models.Arquivo;
import br.com.via1.pad.models.Documentacao;

public interface ArquivoDAO extends JpaRepository<Arquivo, Integer> {


	List<Arquivo> findAllByDocumentacao(Sort by, Documentacao documentacao);
	
	List<Arquivo> findAllByDocumentacao(Documentacao documentacao);
}
