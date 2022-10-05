package br.com.via1.pad.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.via1.pad.models.Documentacao;
import br.com.via1.pad.models.Usuario;
import org.springframework.data.domain.Sort;

public interface DocumentacaoDAO extends JpaRepository<Documentacao, Integer> {

	Documentacao findDocumentacaoById(Integer id);

	List<Documentacao> findAllByUsuario(Sort by,Usuario usuario);
	
	List<Documentacao> findAllByUsuario(Usuario usuario);
	
	List<Documentacao> findAllByUsuario(Integer id);
}
