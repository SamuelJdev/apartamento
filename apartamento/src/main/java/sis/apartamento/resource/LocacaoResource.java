package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sis.apartamento.model.Locacao;
import sis.apartamento.resource.dto.LocacaoRequestPostDTO;
import sis.apartamento.resource.dto.LocacaoResponseDTO;
import sis.apartamento.resource.dto.LocacaoResponsePutDTO;
import sis.apartamento.service.interfaces.ILocacao;

import java.util.List;

@RestController
@RequestMapping("/locacoes")
public class LocacaoResource {
    @Autowired
    private ILocacao locacaoService;

    @GetMapping
    public @ResponseBody List<Locacao> listar() {
        return locacaoService.listarTodos();
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<LocacaoResponseDTO> buscarPorId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Locacao locacao = locacaoService.buscarPorId(id);
        return ResponseEntity.ok(modelMapper.map(locacao, LocacaoResponseDTO.class));
    }

    @PostMapping
    public ResponseEntity<LocacaoResponseDTO> inserir(@RequestBody LocacaoRequestPostDTO locacaoDTO) {
        ModelMapper modelMapper = new ModelMapper();
        var locacao = modelMapper.map(locacaoDTO, Locacao.class);
        try {
            locacaoService.inserir(locacao);
        } catch (DataIntegrityViolationException e) {
            ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(modelMapper.map(locacao, LocacaoResponseDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<LocacaoResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody LocacaoResponsePutDTO locacaoResponsePutDTO) {
        ModelMapper modelMapper = new ModelMapper();

        Locacao locacao =  locacaoService.editar(modelMapper.map(locacaoResponsePutDTO, Locacao.class), id);

        return ResponseEntity.ok(modelMapper.map(locacao, LocacaoResponseDTO.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        locacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
