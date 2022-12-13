package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.exception.EntidadeNaoEncontradaException;
import sis.apartamento.exception.EntidadeRestricaoDeDadosException;
import sis.apartamento.exception.NegocioException;
import sis.apartamento.model.Apto;
import sis.apartamento.model.Inquilino;
import sis.apartamento.model.Usuario;
import sis.apartamento.resource.dto.*;
import sis.apartamento.service.AptoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/aptos")
public class AptoResource {

    @Autowired
    private AptoService aptoService;

    @GetMapping
    public @ResponseBody List<Apto> listar() {
        return aptoService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AptoResponseDTO> buscarPorId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Apto apto = aptoService.buscarPorId(id);
        return ResponseEntity.ok(modelMapper.map(apto, AptoResponseDTO.class));
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
    public ResponseEntity<AptoResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody AptoRequestPutDTO aptoRequestPutDTO) {
        ModelMapper modelMapper = new ModelMapper();

        Apto apto = aptoService.editar(modelMapper.map(aptoRequestPutDTO, Apto.class), id);

        return ResponseEntity.ok(modelMapper.map(apto, AptoResponseDTO.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        aptoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}