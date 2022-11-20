package sis.apartamento.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.model.Inquilino;
import sis.apartamento.service.InquilinoService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inquilinos")
public class InquilinoResource {

    @Autowired
    private InquilinoService inquilinoService;

    @GetMapping
    public @ResponseBody List<Inquilino> listar (){
        return inquilinoService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Inquilino> buscarPorId(@PathVariable("id") Long id){
        Inquilino inquilino = inquilinoService.buscarPorId(id);
        return ResponseEntity.ok(inquilino);
    }

    @PostMapping
    public ResponseEntity<Void> inserir (@RequestBody Inquilino inquilino){
        try {
            inquilino = inquilinoService.inserir(inquilino);
        }catch (DataIntegrityViolationException e){
            ResponseEntity.badRequest().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(inquilino.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizar (@PathVariable("id") Long id, @RequestBody Inquilino inquilino){
        inquilino.setId(id);
        inquilinoService.editar(inquilino);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id ){
        inquilinoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
