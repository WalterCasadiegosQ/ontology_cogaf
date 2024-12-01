package com.cogaf.OntologyCOGAF_Backend.service;

import com.cogaf.OntologyCOGAF_Backend.entity.TextoEntity;
import com.cogaf.OntologyCOGAF_Backend.mapper.FuncionCognitivaMapper;
import com.cogaf.OntologyCOGAF_Backend.repository.TextoRepository;
import com.cogaf.OntologyCOGAF_Backend.request.FuncionCognitivaRequest;
import com.cogaf.OntologyCOGAF_Backend.response.FuncionCognitivaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextoService {

    @Autowired
    private TextoRepository textoRepository;

    @Autowired
    private FuncionCognitivaMapper funcionCognitivaMapper;

    public FuncionCognitivaResponse getRecomendacion(FuncionCognitivaRequest funcionCognitivaRequest) {
        String funcionCognitiva = funcionCognitivaRequest.getFuncionCognitiva().replace("_", " ");
        TextoEntity textoEntity = textoRepository.findByFuncionCognitivaAndCapacidad(funcionCognitiva, funcionCognitivaRequest.getCapacidad());
        FuncionCognitivaResponse funcionCognitivaResponse = funcionCognitivaMapper.toFuncionCognitivaResponse(textoEntity);

        return funcionCognitivaResponse;
    }
}
