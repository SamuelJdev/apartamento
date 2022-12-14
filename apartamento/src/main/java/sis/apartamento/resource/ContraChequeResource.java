package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sis.apartamento.exception.EntidadeNaoEncontradaException;
import sis.apartamento.exception.EntidadeRestricaoDeDadosException;
import sis.apartamento.exception.NegocioException;
import sis.apartamento.model.ContraCheque;
import sis.apartamento.resource.dto.ContraChequeRequestPostDTO;
import sis.apartamento.resource.dto.ContraChequeResponsePutDTO;
import sis.apartamento.resource.dto.ContraChequeResponseDTO;
import sis.apartamento.service.ContraChequeService;

import java.util.List;

@RestController  /* o @RestController faz automaticamente tudo o que o @Controller faz é retornar tudo em JSON  */
@RequestMapping("/contraCheques") /*  é a anotação utilizada tradicionalmente para implementar URL handler, ela suporta os métodos Post, Get, Put, Delete e Pacth. */
public class ContraChequeResource {

    @Autowired /* f. ornece controle sobre onde e como a ligação entre os beans deve ser realizada*/
    private ContraChequeService contraChequeService;    /* @Autowired é usado para indicar Injeção de dependencia */

    @GetMapping     /*Essa anotação é usada para mapear solicitações HTTP GET em métodos manipuladores específicos */
    public List<ContraCheque> listar() {
        return contraChequeService.listarTodos();
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
        } catch (EntidadeRestricaoDeDadosException e) {
            throw new NegocioException(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ContraChequeResponseDTO atualizar(@PathVariable("id") Long id, @RequestBody ContraChequeResponsePutDTO contraChequePutDTO) {
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