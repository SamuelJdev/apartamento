package sis.apartamento.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.model.Predio;
import sis.apartamento.service.PredioService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/predios")
public class PredioResource {
    @Autowired
    private PredioService predioService;

    @GetMapping
    public @ResponseBody List<Predio> listar (){
        return predioService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Predio> buscarPorId(@PathVariable("id") Long id){
        Predio predio = predioService.buscarPorId(id);
        return ResponseEntity.ok(predio);
    }

    @PostMapping
    public ResponseEntity<Void> inserir (@RequestBody Predio predio){
        try {
            predio = predioService.inserir(predio);
        }catch (DataIntegrityViolationException e){
            ResponseEntity.badRequest().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(predio.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizar (@PathVariable("id") Long id, @RequestBody Predio predio){
        predio.setId(id);
        predioService.editar(predio);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id ){
        predioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}