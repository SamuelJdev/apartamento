package sis.apartamento.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sis.apartamento.exception.NegocioException;
import sis.apartamento.infra.EnderecoDTO;
import sis.apartamento.infra.ViaCepClient;

@Service
public class EnderecoService {
	
	@Autowired
	public ViaCepClient viaCepClient;
	
	public EnderecoDTO buscarCep(String cep) {
		String json = viaCepClient.getCeps(cep);
		ObjectMapper  mapper =  new ObjectMapper();
		EnderecoDTO enderecoDTO = null;
		try {
			enderecoDTO = mapper.readValue(json, EnderecoDTO.class);
		} catch (JsonProcessingException e) {
			throw new NegocioException(e.getMessage());
		}

		return enderecoDTO;
	}
}
