package com.projeto.shopee.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class PublicController {

    @RequestMapping
    public String getOlaPublic(){
        return "Ola, visualização publica";
    }
}
