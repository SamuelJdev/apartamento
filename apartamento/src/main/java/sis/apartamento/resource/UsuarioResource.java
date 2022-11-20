package sis.apartamento.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.model.Usuario;
import sis.apartamento.service.UsuarioService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public @ResponseBody List<Usuario> listar (){
        return usuarioService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable("id") Long id){
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Void> inserir (@RequestBody Usuario usuario){
        try {
            usuario = usuarioService.inserir(usuario);
        }catch (DataIntegrityViolationException e){
            ResponseEntity.badRequest().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizar (@PathVariable("id") Long id, @RequestBody Usuario usuario){
        usuario.setId(id);
        usuarioService.editar(usuario);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id ){
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
