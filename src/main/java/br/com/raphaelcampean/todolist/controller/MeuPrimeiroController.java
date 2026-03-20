package br.com.raphaelcampean.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota")
public class MeuPrimeiroController {
    
    @GetMapping
    public String minhaPrimeiraRota() {
        return "Olá, esta é a minha primeira rota!";
    }
}
