package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.model.Inquilino;
import sis.apartamento.model.Usuario;
import sis.apartamento.resource.dto.InquilinoResponseDTO;
import sis.apartamento.resource.dto.UsuarioRequestPostDTO;
import sis.apartamento.resource.dto.UsuarioRequestPutDTO;
import sis.apartamento.resource.dto.UsuarioResponseDTO;
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
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable("id") Long id){
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(modelMapper.map(usuario, UsuarioResponseDTO.class));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> inserir (@RequestBody UsuarioRequestPostDTO usuarioRequestPostDTO){
        ModelMapper modelMapper = new ModelMapper();
        var usuario = modelMapper.map(usuarioRequestPostDTO, Usuario.class);
        try {
            usuario = usuarioService.inserir(usuario);
        }catch (DataIntegrityViolationException e){
            ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(modelMapper.map(usuario, UsuarioResponseDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar (@PathVariable("id") Long id, @RequestBody UsuarioRequestPutDTO usuarioRequestPutDTO){
        ModelMapper modelMapper = new ModelMapper();

        Usuario usuario  = usuarioService.editar(modelMapper.map(usuarioRequestPutDTO, Usuario.class), id);
        return ResponseEntity.ok(modelMapper.map(usuario, UsuarioResponseDTO.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id ){
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
