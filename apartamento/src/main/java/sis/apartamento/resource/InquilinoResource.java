package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sis.apartamento.exception.EntidadeNaoEncontradaException;
import sis.apartamento.exception.EntidadeRestricaoDeDadosException;
import sis.apartamento.exception.NegocioException;
import sis.apartamento.model.Inquilino;
import sis.apartamento.resource.dto.InquilinoRequestPostDTO;
import sis.apartamento.resource.dto.InquilinoResponsePutDTO;
import sis.apartamento.resource.dto.InquilinoResponseDTO;
import sis.apartamento.service.InquilinoService;

import java.util.List;

@RestController
@RequestMapping("/inquilinos")
public class InquilinoResource {

    @Autowired
    private InquilinoService inquilinoService;

    @GetMapping
    public List<Inquilino> listar() {
        return inquilinoService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public InquilinoResponseDTO buscarPorId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        var inquilino = inquilinoService.buscarPorId(id);
        return modelMapper.map(inquilino, InquilinoResponseDTO.class);
    }

    @PostMapping
    public InquilinoResponseDTO inserir(@RequestBody InquilinoRequestPostDTO inquilinoRequestPostDTO) {
        ModelMapper modelMapper = new ModelMapper();
        try {
            var inquilino = inquilinoService.inserir(modelMapper.map(inquilinoRequestPostDTO, Inquilino.class));
            return modelMapper.map(inquilino, InquilinoResponseDTO.class);
        } catch (EntidadeRestricaoDeDadosException e) {
            throw new NegocioException(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public InquilinoResponseDTO atualizar(@PathVariable("id") Long id, @RequestBody InquilinoResponsePutDTO inquilinoRequestPutDTO) {
        ModelMapper modelMapper = new ModelMapper();
        var inquilino = inquilinoService.editar(modelMapper.map(inquilinoRequestPutDTO, Inquilino.class), id);
        return modelMapper.map(inquilino, InquilinoResponseDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id) {
        inquilinoService.deletar(id);
    }
}
