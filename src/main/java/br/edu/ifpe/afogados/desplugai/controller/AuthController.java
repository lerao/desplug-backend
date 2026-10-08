package br.edu.ifpe.afogados.desplugai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping
    public ResponseEntity<Object> login() {

        return ResponseEntity.ok().build();
    }
}
