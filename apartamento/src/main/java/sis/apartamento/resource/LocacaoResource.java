package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sis.apartamento.exception.EntidadeNaoEncontradaException;
import sis.apartamento.exception.EntidadeRestricaoDeDadosException;
import sis.apartamento.exception.NegocioException;
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
    public List<Locacao> listar() {
        return locacaoService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public LocacaoResponseDTO buscarPorId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Locacao locacao = locacaoService.buscarPorId(id);
        return modelMapper.map(locacao, LocacaoResponseDTO.class);
    }

    @PostMapping
    public LocacaoResponseDTO inserir(@RequestBody LocacaoRequestPostDTO locacaoDTO) {
        ModelMapper modelMapper = new ModelMapper();
        try {
            var locacao = locacaoService.inserir(modelMapper.map(locacaoDTO, Locacao.class));
            return modelMapper.map(locacao, LocacaoResponseDTO.class);
        } catch (EntidadeRestricaoDeDadosException e) {
            throw new NegocioException(e.getMessage());
        }
        catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public LocacaoResponseDTO atualizar(@PathVariable("id") Long id, @RequestBody LocacaoResponsePutDTO locacaoResponsePutDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Locacao locacao =  locacaoService.editar(modelMapper.map(locacaoResponsePutDTO, Locacao.class), id);
        return modelMapper.map(locacao, LocacaoResponseDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id) {
        locacaoService.deletar(id);
    }
}
