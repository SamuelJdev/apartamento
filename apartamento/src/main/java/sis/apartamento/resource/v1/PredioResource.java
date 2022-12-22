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
import sis.apartamento.model.Predio;
import sis.apartamento.repository.PredioRepository;
import sis.apartamento.repository.filter.PredioFilter;
import sis.apartamento.resource.v1.dto.request.PredioRequestPostDTO;
import sis.apartamento.resource.v1.dto.request.PredioRequestPutDTO;
import sis.apartamento.resource.v1.dto.response.PredioResponseDTO;
import sis.apartamento.service.PredioService;

@RestController
@RequestMapping("/v1/predios")
public class PredioResource {
    @Autowired
    private PredioService predioService;

    @Autowired
    private PredioRepository predioRepository;

    @GetMapping
    public Page<Predio> pesquisar(PredioFilter predioFilter, Pageable pageable) {
        return predioRepository.filtrar(predioFilter, pageable);
    }

    @GetMapping(value = "/{id}")
    public PredioResponseDTO buscarPorId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Predio predio = predioService.buscarPorId(id);
        return modelMapper.map(predio, PredioResponseDTO.class);
    }

    @PostMapping
    public PredioResponseDTO inserir(@RequestBody PredioRequestPostDTO predioRequestPostDTO) {
        ModelMapper modelMapper = new ModelMapper();
        try {
            var predio = predioService.inserir(modelMapper.map(predioRequestPostDTO, Predio.class));
            return modelMapper.map(predio, PredioResponseDTO.class);
        } catch (EntidadeRestricaoDeDadosException | EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public PredioResponseDTO atualizar(@PathVariable("id") Long id, @RequestBody PredioRequestPutDTO predioRequestPut) {
        ModelMapper modelMapper = new ModelMapper();
        Predio predio = predioService.editar(modelMapper.map(predioRequestPut, Predio.class), id);
        return modelMapper.map(predio, PredioResponseDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id) {
        predioService.deletar(id);
    }
}