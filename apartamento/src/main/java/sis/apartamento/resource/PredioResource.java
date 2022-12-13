package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sis.apartamento.model.Predio;
import sis.apartamento.resource.dto.PredioRequestPostDTO;
import sis.apartamento.resource.dto.PredioRequestPutDTO;
import sis.apartamento.resource.dto.PredioResponseDTO;
import sis.apartamento.service.PredioService;

import java.util.List;

@RestController
@RequestMapping("/predios")
public class PredioResource {
    @Autowired
    private PredioService predioService;

    @GetMapping
    public @ResponseBody List<Predio> listar (){
        return predioService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PredioResponseDTO> buscarPorId(@PathVariable("id") Long id){
        ModelMapper modelMapper = new ModelMapper();
        Predio predio = predioService.buscarPorId(id);
        return ResponseEntity.ok(modelMapper.map(predio, PredioResponseDTO.class));
    }

    @PostMapping
    public ResponseEntity<PredioResponseDTO> inserir (@RequestBody PredioRequestPostDTO predioRequestPostDTO){
        ModelMapper modelMapper = new ModelMapper();
        var predio = modelMapper.map(predioRequestPostDTO, Predio.class);
        try {
            predio = predioService.inserir(predio);
        }catch (DataIntegrityViolationException e){
            ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(modelMapper.map(predio, PredioResponseDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PredioResponseDTO> atualizar (@PathVariable("id") Long id, @RequestBody PredioRequestPutDTO predioRequestPut){
        ModelMapper modelMapper = new ModelMapper();

        Predio predio = predioService.editar(modelMapper.map(predioRequestPut, Predio.class), id);
        return ResponseEntity.ok(modelMapper.map(predio, PredioResponseDTO.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id ){
        predioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}