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
import sis.apartamento.model.Inquilino;
import sis.apartamento.repository.InquilinoRepository;
import sis.apartamento.repository.filter.InquilinoFilter;
import sis.apartamento.resource.v1.dto.request.InquilinoRequestPostDTO;
import sis.apartamento.resource.v1.dto.request.InquilinoRequestPutDTO;
import sis.apartamento.resource.v1.dto.response.InquilinoResponseDTO;
import sis.apartamento.service.InquilinoService;

@RestController
@RequestMapping("/v1/inquilinos")
public class InquilinoResource {

    @Autowired
    private InquilinoService inquilinoService;

    @Autowired
    private InquilinoRepository inquilinoRepository;

    @GetMapping
    public Page<Inquilino> pesquisar(InquilinoFilter inquilinoFilter, Pageable pageable) {
        return inquilinoRepository.filtrar(inquilinoFilter, pageable);
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
    public InquilinoResponseDTO atualizar(@PathVariable("id") Long id, @RequestBody InquilinoRequestPutDTO inquilinoRequestPutDTO) {
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
