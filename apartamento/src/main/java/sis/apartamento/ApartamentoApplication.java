package sis.apartamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApartamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApartamentoApplication.class, args);
	}
}