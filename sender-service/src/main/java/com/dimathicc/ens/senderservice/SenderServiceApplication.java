package com.dimathicc.ens.senderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class SenderServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SenderServiceApplication.class, args);
	}
}
