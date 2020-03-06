package com.mtzzwr.odonto.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtzzwr.odonto.model.Especialidade;
import com.mtzzwr.odonto.repository.EspecialidadeRepository;

@RestController
@RequestMapping("/odonto")
public class EspecialidadeResource {

	@Autowired
	private EspecialidadeRepository especialidadeRepository;
	
	@GetMapping("/especialidades")
	public List<Especialidade> getEspecialidades(){
		return especialidadeRepository.findAll();
	}
	
}
