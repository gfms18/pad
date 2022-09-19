package br.com.via1.pad.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.via1.pad.models.Empresa;

public interface EmpresaDAO extends JpaRepository<Empresa, Integer> {

}
