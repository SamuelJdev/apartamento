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
import sis.apartamento.model.Locacao;
import sis.apartamento.repository.LocacaoRepository;
import sis.apartamento.repository.filter.LocacaoFilter;
import sis.apartamento.resource.v1.dto.request.LocacaoRequestPostDTO;
import sis.apartamento.resource.v1.dto.request.LocacaoRequestPutDTO;
import sis.apartamento.resource.v1.dto.response.LocacaoResponseDTO;
import sis.apartamento.service.interfaces.ILocacao;

@RestController
@RequestMapping("/v1/locacoes")
public class LocacaoResource {
    @Autowired
    private ILocacao locacaoService;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @GetMapping
    public Page<Locacao> pesquisar(LocacaoFilter locacaoFilter, Pageable pageable) {
        return locacaoRepository.filtrar(locacaoFilter, pageable);
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
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public LocacaoResponseDTO atualizar(@PathVariable("id") Long id, @RequestBody LocacaoRequestPutDTO locacaoResponsePutDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Locacao locacao = locacaoService.editar(modelMapper.map(locacaoResponsePutDTO, Locacao.class), id);
        return modelMapper.map(locacao, LocacaoResponseDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id) {
        locacaoService.deletar(id);
    }
}
