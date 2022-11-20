package sis.apartamento.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.model.Locacao;
import sis.apartamento.service.LocacaoService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/locacoes")
public class LocacaoResource {

    @Autowired
    private LocacaoService locacaoService;

    @GetMapping
    public @ResponseBody List<Locacao> listar (){
        return locacaoService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Locacao> buscarPorId(@PathVariable("id") Long id){
        Locacao locacao = locacaoService.buscarPorId(id);
        return ResponseEntity.ok(locacao);
    }

    @PostMapping
    public ResponseEntity<Void> inserir (@RequestBody Locacao locacao){
        try {
            locacao = locacaoService.inserir(locacao);
        }catch (DataIntegrityViolationException e){
            ResponseEntity.badRequest().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(locacao.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizar (@PathVariable("id") Long id, @RequestBody Locacao locacao){
        locacao.setId(id);
        locacaoService.editar(locacao);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id ){
        locacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
