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
import sis.apartamento.model.Apto;
import sis.apartamento.repository.AptoRepository;
import sis.apartamento.repository.filter.AptosFilter;
import sis.apartamento.resource.v1.dto.request.AptoRequestPostDTO;
import sis.apartamento.resource.v1.dto.request.AptoRequestPutDTO;
import sis.apartamento.resource.v1.dto.response.AptoResponseDTO;
import sis.apartamento.service.AptoService;

@RestController
@RequestMapping("/v1/aptos")
public class AptoResource {

    @Autowired
    private AptoService aptoService;
    @Autowired
    private AptoRepository aptoRepository;

    @GetMapping
    public Page<Apto> pesquisar(AptosFilter aptosFilter, Pageable pageable) {
        return aptoRepository.filtrar(aptosFilter, pageable);
    }

    @GetMapping(value = "/{id}")
    public AptoResponseDTO buscarPorId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Apto apto = aptoService.buscarPorId(id);
        return modelMapper.map(apto, AptoResponseDTO.class);
    }

    @PostMapping
    public AptoResponseDTO inserir(@RequestBody AptoRequestPostDTO aptoRequestPostDTO) {
        ModelMapper modelMapper = new ModelMapper();
        try {
            var apto = aptoService.inserir(modelMapper.map(aptoRequestPostDTO, Apto.class));
            return modelMapper.map(apto, AptoResponseDTO.class);

        } catch (EntidadeRestricaoDeDadosException e) {
            throw new NegocioException(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public AptoResponseDTO atualizar(@PathVariable("id") Long id, @RequestBody AptoRequestPutDTO aptoRequestPutDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Apto apto = aptoService.editar(modelMapper.map(aptoRequestPutDTO, Apto.class), id);
        return modelMapper.map(apto, AptoResponseDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id) {
        aptoService.deletar(id);
    }
}