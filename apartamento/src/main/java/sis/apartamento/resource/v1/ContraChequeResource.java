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
import sis.apartamento.model.ContraCheque;
import sis.apartamento.repository.ContraChequeRepository;
import sis.apartamento.repository.filter.ContraChequeFilter;
import sis.apartamento.resource.v1.dto.request.ContraChequeRequestPostDTO;
import sis.apartamento.resource.v1.dto.request.ContraChequeRequestPutDTO;
import sis.apartamento.resource.v1.dto.response.ContraChequeResponseDTO;
import sis.apartamento.service.ContraChequeService;

@RestController  /* o @RestController faz automaticamente tudo o que o @Controller faz é retornar tudo em JSON  */
@RequestMapping("/v1/contraCheques") /*  é a anotação utilizada tradicionalmente para implementar URL handler, ela suporta os métodos Post, Get, Put, Delete e Pacth. */
public class ContraChequeResource {

    @Autowired /* f. ornece controle sobre onde e como a ligação entre os beans deve ser realizada*/
    private ContraChequeService contraChequeService;    /* @Autowired é usado para indicar Injeção de dependencia */

    @Autowired
    private ContraChequeRepository contraChequeRepository;

    @GetMapping     /*Essa anotação é usada para mapear solicitações HTTP GET em métodos manipuladores específicos */
    public Page<ContraCheque> pesquisar(ContraChequeFilter contraChequeFilter, Pageable pageable) {
        return contraChequeRepository.filtrar(contraChequeFilter, pageable);
    }

    @GetMapping(value = "/{id}")
    public ContraChequeResponseDTO buscarPorId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        var contraCheque = contraChequeService.buscarPorId(id);
        return modelMapper.map(contraCheque, ContraChequeResponseDTO.class);
    }

    @PostMapping
    public ContraChequeResponseDTO inserir(@RequestBody ContraChequeRequestPostDTO contraChequePostDTO) {
        ModelMapper modelMapper = new ModelMapper();
        try {
            var contraCheque = contraChequeService.inserir(modelMapper.map(contraChequePostDTO, ContraCheque.class));
            return modelMapper.map(contraCheque, ContraChequeResponseDTO.class);
        } catch (EntidadeRestricaoDeDadosException | EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ContraChequeResponseDTO atualizar(@PathVariable("id") Long id, @RequestBody ContraChequeRequestPutDTO contraChequePutDTO) {
        ModelMapper modelMapper = new ModelMapper();
        ContraCheque contraCheque = contraChequeService.editar(modelMapper.map(contraChequePutDTO, ContraCheque.class), id);
        return modelMapper.map(contraCheque, ContraChequeResponseDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id) {
        contraChequeService.deletar(id);
    }
}