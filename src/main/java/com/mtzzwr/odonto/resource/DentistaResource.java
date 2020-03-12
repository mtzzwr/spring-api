package com.mtzzwr.odonto.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mtzzwr.odonto.dto.DentistaDTO;
import com.mtzzwr.odonto.model.Dentista;
import com.mtzzwr.odonto.repository.DentistaRepository;

@RestController
@RequestMapping("/odonto")
@CrossOrigin
public class DentistaResource {
	
	@Autowired
	private DentistaRepository dentistaRepository;
	
	// todos os dentistas
	@GetMapping("/dentistas")
	public List<Dentista> getDentistas(){
		return dentistaRepository.findAll();
	}
	
	// um único dentista
	@GetMapping("/dentistas/{codigo}")
	public ResponseEntity getDentista(@PathVariable Long codigo){
		Optional dentista = dentistaRepository.findById(codigo);
		return dentista.isPresent() ? ResponseEntity.ok(dentista.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/dentistas/cro/{cro}")
	public List<Dentista> getDentistaCro(@PathVariable String cro){
		return dentistaRepository.findByCro(cro);
	}
	
	@GetMapping("/dentistas/nome/{nome}")
	public List<Dentista> getDentistaNome(@PathVariable String nome){
		return dentistaRepository.findByLikeNome(nome);
	}
	
	// criar um novo dentista
	@PostMapping("/dentistas")
	@ResponseStatus(HttpStatus.CREATED)
	public Dentista gravar(@Valid @RequestBody Dentista dentista) {
		Dentista novoDentista = dentistaRepository.save(dentista);
		return novoDentista;
	}
	
	// editar um dentista
	@PutMapping("/dentistas/{codigo}")
	public ResponseEntity<Dentista> editar(@PathVariable Long codigo, @Valid @RequestBody Dentista dentista) {
		Dentista dentistaAtual = dentistaRepository.findById(codigo).get();

		BeanUtils.copyProperties(dentista, dentistaAtual, "codigo");
		
		dentistaRepository.save(dentistaAtual);
		
		return ResponseEntity.ok(dentistaAtual);

	}
	
	// deletar um dentista
	@DeleteMapping("/dentistas/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		dentistaRepository.deleteById(codigo);
	}
	
}
