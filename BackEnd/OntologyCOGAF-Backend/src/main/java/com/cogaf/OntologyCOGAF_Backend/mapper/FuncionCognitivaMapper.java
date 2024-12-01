package com.cogaf.OntologyCOGAF_Backend.mapper;

import com.cogaf.OntologyCOGAF_Backend.entity.TextoEntity;
import com.cogaf.OntologyCOGAF_Backend.response.FuncionCognitivaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface FuncionCognitivaMapper {

    @Mappings({
            @Mapping(source = "tipo", target = "tipo"),
            @Mapping(source = "nivel", target = "nivel"),
            @Mapping(source = "actividadComplementaria", target = "actividadComplementaria"),
            @Mapping(source = "mecanica", target = "mecanica"),
            @Mapping(source = "pruebaCognitiva", target = "pruebaCognitiva"),
            @Mapping(source = "tipoPrueba", target = "tipoPrueba"),
            @Mapping(source = "aplicacion", target = "aplicacion"),
            @Mapping(source = "tareaPsicologica", target = "tareaPsicologica"),

    })
    FuncionCognitivaResponse toFuncionCognitivaResponse(TextoEntity textoEntity);
}
