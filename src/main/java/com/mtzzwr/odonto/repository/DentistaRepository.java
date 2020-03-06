package com.mtzzwr.odonto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mtzzwr.odonto.model.Dentista;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
	List<Dentista> findByCro(String cro);
	
	@Query(value = "SELECT d FROM Dentista d WHERE d.nome LIKE %:nome%")
	List<Dentista> findByLikeNome(@Param(value = "nome") String nome);
}
