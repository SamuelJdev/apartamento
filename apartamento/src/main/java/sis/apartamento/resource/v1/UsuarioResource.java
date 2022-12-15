package sis.apartamento.resource.v1;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sis.apartamento.exception.EntidadeNaoEncontradaException;
import sis.apartamento.exception.EntidadeRestricaoDeDadosException;
import sis.apartamento.exception.NegocioException;
import sis.apartamento.model.Usuario;
import sis.apartamento.repository.UsuarioRepository;
import sis.apartamento.repository.filter.UsuarioFilter;
import sis.apartamento.resource.v1.dto.request.UsuarioRequestPostDTO;
import sis.apartamento.resource.v1.dto.request.UsuarioRequestPutDTO;
import sis.apartamento.resource.v1.dto.response.UsuarioResponseDTO;
import sis.apartamento.service.UsuarioService;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioResource {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public Page<Usuario> pesquisar(UsuarioFilter usuarioFilter, Pageable pageable) {
        return usuarioRepository.filtrar(usuarioFilter, pageable);
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
        } catch (EntidadeRestricaoDeDadosException e) {
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
