package com.cogaf.OntologyCOGAF_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "texto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TextoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_texto")
    private String idTexto;
    @Column(name = "funcion_cognitiva")
    private String funcionCognitiva;
    @Column(name = "tipo_funcion_cognitiva")
    private String tipo;
    @Column(name = "capacidad")
    private String capacidad;
    @Column(name = "nivel")
    private String nivel;
    @Column(name = "actividad_complementaria")
    private String actividadComplementaria;
    @Column(name = "mecanica")
    private String mecanica;
    @Column(name = "prueba_cognitiva")
    private String pruebaCognitiva;
    @Column(name = "tipo_prueba")
    private String tipoPrueba;
    @Column(name = "prueba_aplicacion")
    private String aplicacion;
    @Column(name = "tarea_psicologica")
    private String tareaPsicologica;
    @Column(name = "evento")
    private String evento;
    @Column(name = "caracteristica")
    private String caracteristica;
    @Column(name = "parametro")
    private String parametro;
    @Column(name = "metodo_reconocimiento")
    private String metodoReconocimiento;

}
