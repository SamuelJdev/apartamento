package sis.apartamento.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.model.Apto;
import sis.apartamento.service.AptoService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/aptos")
public class AptoResource {

    @Autowired
    private AptoService aptoService;

    @GetMapping
    public @ResponseBody List<Apto> listar (){
        return aptoService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Apto> buscarPorId(@PathVariable("id") Long id){
        Apto apto = aptoService.buscarPorId(id);
        return ResponseEntity.ok(apto);
    }

    @PostMapping
    public ResponseEntity<Void> inserir (@RequestBody Apto apto){
        try {
            apto = aptoService.inserir(apto);
        }catch (DataIntegrityViolationException e){
            ResponseEntity.badRequest().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(apto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizar (@PathVariable("id") Long id, @RequestBody Apto apto){
        apto.setId(id);
        aptoService.editar(apto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id ){
        aptoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}