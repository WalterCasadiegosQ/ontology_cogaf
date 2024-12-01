package com.cogaf.OntologyCOGAF_Backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FuncionCognitivaResponse {

    private String tipo;
    private String nivel;
    private String actividadComplementaria;
    private String mecanica;
    private String pruebaCognitiva;
    private String tipoPrueba;
    private String aplicacion;
    private String tareaPsicologica;
}
