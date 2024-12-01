package com.cogaf.OntologyCOGAF_Backend.controller;

import com.cogaf.OntologyCOGAF_Backend.request.FuncionCognitivaRequest;
import com.cogaf.OntologyCOGAF_Backend.response.FuncionCognitivaResponse;
import com.cogaf.OntologyCOGAF_Backend.service.TextoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/texto")
public class TextoController {

    @Autowired
    private TextoService textoService;

    @PostMapping("/recomendacion")
    public ResponseEntity<FuncionCognitivaResponse> getRecomendacion(@RequestBody FuncionCognitivaRequest funcionCognitivaRequest) {
        return ResponseEntity.ok(textoService.getRecomendacion(funcionCognitivaRequest));
    }
}
