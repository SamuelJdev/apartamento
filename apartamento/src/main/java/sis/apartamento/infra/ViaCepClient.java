package sis.apartamento.infra;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "viacep", url = "viacep.com.br/ws")
public interface ViaCepClient {
	
	@RequestMapping(value = "{cep}/json")
	String getCeps(@PathVariable("cep") String cep);

}