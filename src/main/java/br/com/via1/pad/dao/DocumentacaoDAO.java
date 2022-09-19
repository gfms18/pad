package br.com.via1.pad.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.via1.pad.models.Documentacao;

public interface DocumentacaoDAO extends JpaRepository<Documentacao, Integer> {

	Documentacao findDocumentacaoById(Integer id);
}
