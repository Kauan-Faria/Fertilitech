package com.godigital.backend.controller;

import com.godigital.backend.models.Medico;
import com.godigital.backend.repository.MedicoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  private final MedicoRepository repo;
  public MedicoController(MedicoRepository repo) { this.repo = repo; }

  @GetMapping
  public ResponseEntity<List<Medico>> listar() { return ResponseEntity.ok(repo.findAll()); }

  @GetMapping("/{id}")
  public ResponseEntity<Medico> porId(@PathVariable Long id) {
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Medico> criar(@RequestBody Medico m) {
    Medico salvo = repo.save(m);
    return ResponseEntity.created(URI.create("/api/v1/medicos/" + salvo.getId())).body(salvo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Medico> atualizar(@PathVariable Long id, @RequestBody Medico body) {
    return repo.findById(id).map(m -> {
      m.setNome(body.getNome());
      m.setEspecialidade(body.getEspecialidade());
      return ResponseEntity.ok(repo.save(m));
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
