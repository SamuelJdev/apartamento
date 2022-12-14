package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sis.apartamento.exception.EntidadeNaoEncontradaException;
import sis.apartamento.exception.EntidadeRestricaoDeDadosException;
import sis.apartamento.exception.NegocioException;
import sis.apartamento.model.Apto;
import sis.apartamento.resource.dto.*;
import sis.apartamento.service.AptoService;

import java.util.List;

@RestController
@RequestMapping("/aptos")
public class AptoResource {

    @Autowired
    private AptoService aptoService;

    @GetMapping
    public List<Apto> listar() {
        return aptoService.listarTodos();
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
    public AptoResponseDTO atualizar(@PathVariable("id") Long id, @RequestBody AptoResponsePutDTO aptoRequestPutDTO) {
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