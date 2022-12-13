package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.exception.EntidadeNaoEncontradaException;
import sis.apartamento.exception.NegocioException;
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
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public UsuarioResponseDTO buscarPorId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = usuarioService.buscarPorId(id);
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    @PostMapping
    public UsuarioResponseDTO inserir(@RequestBody UsuarioRequestPostDTO usuarioRequestPostDTO) {
        ModelMapper modelMapper = new ModelMapper();
        try {
            var usuario = usuarioService.inserir(modelMapper.map(usuarioRequestPostDTO, Usuario.class));
            return modelMapper.map(usuario, UsuarioResponseDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new NegocioException(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public UsuarioResponseDTO atualizar(@PathVariable("id") Long id, @RequestBody UsuarioRequestPutDTO usuarioRequestPutDTO) {
        ModelMapper modelMapper = new ModelMapper();

        Usuario usuario = usuarioService.editar(modelMapper.map(usuarioRequestPutDTO, Usuario.class), id);
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id) {
        usuarioService.deletar(id);
    }
}
