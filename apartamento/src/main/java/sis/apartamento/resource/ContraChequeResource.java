package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.model.ContraCheque;
import sis.apartamento.resource.dto.ContraChequePostDTO;
import sis.apartamento.resource.dto.ContraChequePutDTO;
import sis.apartamento.resource.dto.ContraChequeResponseDTO;
import sis.apartamento.service.ContraChequeService;
import java.net.URI;
import java.util.List;

@RestController  /* o @RestController faz automaticamente tudo o que o @Controller faz é retornar tudo em JSON  */
@RequestMapping("/contraCheques") /*  é a anotação utilizada tradicionalmente para implementar URL handler, ela suporta os métodos Post, Get, Put, Delete e Pacth. */
public class ContraChequeResource {

    @Autowired /* f. ornece controle sobre onde e como a ligação entre os beans deve ser realizada*/
    private ContraChequeService contraChequeService;    /* @Autowired é usado para indicar Injeção de dependencia */

    @GetMapping     /*Essa anotação é usada para mapear solicitações HTTP GET em métodos manipuladores específicos */
    public @ResponseBody List<ContraCheque> listar (){
        return contraChequeService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContraChequeResponseDTO> buscarPorId(@PathVariable("id") Long id){
        ModelMapper modelMapper = new ModelMapper();
        var contraCheque = contraChequeService.buscarPorId(id);
        return ResponseEntity.ok(modelMapper.map(contraCheque, ContraChequeResponseDTO.class));
    }

    @PostMapping
    public ResponseEntity<ContraChequeResponseDTO> inserir (@RequestBody ContraChequePostDTO contraChequePostDTO){
        ModelMapper modelMapper = new ModelMapper();
        ContraCheque contraCheque = modelMapper.map(contraChequePostDTO, ContraCheque.class);
        try {
            contraCheque = contraChequeService.inserir(contraCheque);
        }catch (DataIntegrityViolationException e){
            ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(modelMapper.map(contraCheque, ContraChequeResponseDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ContraChequeResponseDTO> atualizar (@PathVariable("id") Long id, @RequestBody ContraChequePutDTO contraChequePutDTO){
        ModelMapper modelMapper = new ModelMapper();

        ContraCheque contraCheque = contraChequeService.editar(modelMapper.map(contraChequePutDTO, ContraCheque.class), id);

        return ResponseEntity.ok(modelMapper.map(contraCheque, ContraChequeResponseDTO.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id ){
        contraChequeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}