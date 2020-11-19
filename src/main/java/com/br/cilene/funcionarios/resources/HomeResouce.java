package com.br.cilene.funcionarios.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/")
public class HomeResouce {
	
	@GetMapping
	public String getHello() {
		return "oi!";
	}
}
