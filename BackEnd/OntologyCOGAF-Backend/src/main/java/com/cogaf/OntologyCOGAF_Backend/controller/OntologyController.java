package com.cogaf.OntologyCOGAF_Backend.controller;

import com.cogaf.OntologyCOGAF_Backend.request.FuncionCognitivaRequest;
import com.cogaf.OntologyCOGAF_Backend.response.FuncionCognitivaResponse;
import com.cogaf.OntologyCOGAF_Backend.service.OntologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ontology")
public class OntologyController {

    @Autowired
    OntologyService ontologyService;

    @PostMapping("/recomendacion")
    public ResponseEntity<FuncionCognitivaResponse> getRecomendacion(@RequestBody FuncionCognitivaRequest funcionCognitivaRequest) {
        return ResponseEntity.ok(ontologyService.getRecomendacion(funcionCognitivaRequest));
    }
}
