package br.com.via1.pad.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.via1.pad.models.Documentacao;
import br.com.via1.pad.models.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer>{

	Usuario findByLoginAndSenha(String login, String senha);
	Usuario findUsuarioById(Integer id);
	
}
