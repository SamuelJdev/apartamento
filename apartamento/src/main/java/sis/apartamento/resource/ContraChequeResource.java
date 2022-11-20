package sis.apartamento.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.model.ContraCheque;
import sis.apartamento.service.ContraChequeService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contraCheques")
public class ContraChequeResource {

    @Autowired
    private ContraChequeService contraChequeService;

    @GetMapping
    public @ResponseBody List<ContraCheque> listar (){
        return contraChequeService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContraCheque> buscarPorId(@PathVariable("id") Long id){
        ContraCheque contraCheque = contraChequeService.buscarPorId(id);
        return ResponseEntity.ok(contraCheque);
    }

    @PostMapping
    public ResponseEntity<Void> inserir (@RequestBody ContraCheque contraCheque){
        try {
            contraCheque = contraChequeService.inserir(contraCheque);
        }catch (DataIntegrityViolationException e){
            ResponseEntity.badRequest().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(contraCheque.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizar (@PathVariable("id") Long id, @RequestBody ContraCheque contraCheque){
        contraCheque.setId(id);
        contraChequeService.editar(contraCheque);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id ){
        contraChequeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}