package sis.apartamento.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sis.apartamento.model.Inquilino;
import sis.apartamento.resource.dto.InquilinoRequestPostDTO;
import sis.apartamento.resource.dto.InquilinoRequestPutDTO;
import sis.apartamento.resource.dto.InquilinoResponseDTO;
import sis.apartamento.service.InquilinoService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inquilinos")
public class InquilinoResource {

    @Autowired
    private InquilinoService inquilinoService;

    @GetMapping
    public @ResponseBody List<Inquilino> listar (){
        return inquilinoService.listarTodos();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InquilinoResponseDTO> buscarPorId(@PathVariable("id") Long id){
        ModelMapper modelMapper = new ModelMapper();
        var inquilino = inquilinoService.buscarPorId(id);
        return ResponseEntity.ok(modelMapper.map(inquilino,InquilinoResponseDTO.class));
    }

    @PostMapping
    public ResponseEntity<InquilinoResponseDTO> inserir (@RequestBody InquilinoRequestPostDTO inquilinoRequestPostDTO){
        ModelMapper modelMapper = new ModelMapper();
        var inquilino = modelMapper.map(inquilinoRequestPostDTO, Inquilino.class);
        try {
            inquilino = inquilinoService.inserir(inquilino);
        }catch (DataIntegrityViolationException e){
            ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(modelMapper.map(inquilino, InquilinoResponseDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<InquilinoResponseDTO> atualizar (@PathVariable("id") Long id, @RequestBody InquilinoRequestPutDTO inquilinoRequestPutDTO){
        ModelMapper modelMapper = new ModelMapper();

        var inquilino = inquilinoService.editar(modelMapper.map(inquilinoRequestPutDTO, Inquilino.class), id);

        return ResponseEntity.ok(modelMapper.map(inquilino, InquilinoResponseDTO.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id ){
        inquilinoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
