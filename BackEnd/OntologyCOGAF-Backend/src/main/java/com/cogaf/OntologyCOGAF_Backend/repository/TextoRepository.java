package com.cogaf.OntologyCOGAF_Backend.repository;

import com.cogaf.OntologyCOGAF_Backend.entity.TextoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextoRepository extends CrudRepository<TextoEntity, String> {

    TextoEntity findByFuncionCognitivaAndCapacidad(String funcionCognitiva, String capacidad);

}
