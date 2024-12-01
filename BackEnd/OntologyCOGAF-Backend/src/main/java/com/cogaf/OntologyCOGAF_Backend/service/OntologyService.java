package com.cogaf.OntologyCOGAF_Backend.service;

import com.cogaf.OntologyCOGAF_Backend.request.FuncionCognitivaRequest;
import com.cogaf.OntologyCOGAF_Backend.response.FuncionCognitivaResponse;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Service;

@Service
public class OntologyService {

    private Model model;

    public FuncionCognitivaResponse getRecomendacion(FuncionCognitivaRequest funcionCognitivaRequest) {
        String capacidad = funcionCognitivaRequest.getCapacidad().replace(" ", "");
        this.model = FileManager.get().loadModel("src/main/resources/static/COGAF.owl");

        String consulta = "PREFIX ex: <Fhttp://www.semanticweb.org/casad/ontologies/2024/10/COGAF#>\n" +
                "SELECT ?tipo ?nivel ?tareaDescripcion ?prueba ?tipoPrueba ?aplicacion ?mecanica ?actividadComplementaria\n" +
                "WHERE {\n" +
                "  ex:" + capacidad + " ex:tipo ?tipo .\n" +
                "  ex:" + capacidad + " ex:nivel ?nivel .\n" +
                "  ex:" + capacidad + " ex:tarea ?tarea .\n" +
                "  ?tarea ex:descripcion ?tareaDescripcion .\n" +
                "  ex:" + capacidad + " ex:prueba ?prueba .\n" +
                "  ex:" + capacidad + " ex:tipoPrueba ?tipoPrueba .\n" +
                "  ex:" + capacidad + " ex:aplicacion ?aplicacion .\n" +
                "  ex:" + capacidad + " ex:mecanica ?mecanica .\n" +
                "  ex:" + capacidad + " ex:actividad ?actividadComplementaria .\n" +
                "}";

        Query query = QueryFactory.create(consulta);
        FuncionCognitivaResponse response = new FuncionCognitivaResponse();

        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qe.execSelect();
            if (results.hasNext()) {
                QuerySolution sol = results.next();

                response.setTipo(getLocalName(sol, "tipo"));
                response.setNivel(getLocalName(sol, "nivel"));
                response.setTareaPsicologica(getLiteralValue(sol, "tareaDescripcion"));
                response.setPruebaCognitiva(getLocalName(sol, "prueba").replace("_", " "));
                response.setTipoPrueba(getLocalName(sol, "tipoPrueba"));
                response.setAplicacion(getLocalName(sol, "aplicacion"));
                response.setMecanica(getLocalNameFromURI(sol, "mecanica").replace("_", " "));
                response.setActividadComplementaria(getLocalName(sol, "actividadComplementaria"));
            }
        }

        return response;
    }

    private String getLocalName(QuerySolution sol, String varName) {
        if (sol.contains(varName) && sol.get(varName).isResource()) {
            Resource resource = sol.getResource(varName);
            return resource.getLocalName();
        }
        return "";
    }

    private String getLiteralValue(QuerySolution sol, String varName) {
        if (sol.contains(varName) && sol.get(varName).isLiteral()) {
            return sol.getLiteral(varName).getString();
        }
        return "";
    }

    private String getLocalNameFromURI(QuerySolution sol, String varName) {
        if (sol.contains(varName) && sol.get(varName).isResource()) {
            Resource resource = sol.getResource(varName);
            String uri = resource.getURI();
            if (uri.contains("#")) {
                return uri.substring(uri.lastIndexOf("#") + 1);
            }
        }
        return "";
    }

    public FuncionCognitivaResponse getRecomendacion1(FuncionCognitivaRequest funcionCognitivaRequest) {
        String capacidad = funcionCognitivaRequest.getCapacidad().replace(" ", "");
        this.model = FileManager.get().loadModel("src/main/resources/static/COGAF.owl");
        FuncionCognitivaResponse funcionCognitivaResponse = new FuncionCognitivaResponse();
        funcionCognitivaResponse.setTipo(getTipo(capacidad));
        funcionCognitivaResponse.setNivel(getNivel(capacidad));
        funcionCognitivaResponse.setTareaPsicologica(getTareaPsicologica(capacidad));
        funcionCognitivaResponse.setAplicacion(getAplicacion(capacidad));
        funcionCognitivaResponse.setTipoPrueba(getTipoPrueba(capacidad));
        funcionCognitivaResponse.setPruebaCognitiva(getPruebaCognitiva(capacidad));
        funcionCognitivaResponse.setMecanica(getMecanica(capacidad));
        funcionCognitivaResponse.setActividadComplementaria(getActividadComplementaria(capacidad));

        return funcionCognitivaResponse;
    }

    public String getTipo(String capacidad) {
        String consulta = "PREFIX ex: <Fhttp://www.semanticweb.org/casad/ontologies/2024/10/COGAF#>\n" +
                "SELECT ?tipo\n" +
                "WHERE {\n" +
                "  ex:"+ capacidad +" ex:tipo ?tipo .\n" +
                "}";
        Query query = QueryFactory.create(consulta);
        String tipo = "";
        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution sol = results.next();
                Resource pruebaResource = sol.getResource("tipo");
                if (pruebaResource != null) {
                    tipo = pruebaResource.getLocalName();
                    System.out.printf("Nombre de la instancia: %s%n", tipo);
                }
            }
        }
        return tipo;
    }

    public String getNivel(String capacidad) {
        String consulta = "PREFIX ex: <Fhttp://www.semanticweb.org/casad/ontologies/2024/10/COGAF#>\n" +
                "SELECT ?nivel\n" +
                "WHERE {\n" +
                "  ex:"+ capacidad +" ex:nivel ?nivel .\n" +
                "}";
        Query query = QueryFactory.create(consulta);
        String nivel = "";
        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution sol = results.next();
                Resource pruebaResource = sol.getResource("nivel");
                if (pruebaResource != null) {
                    nivel = pruebaResource.getLocalName();
                    System.out.printf("Nombre de la instancia: %s%n", nivel);
                }
            }
        }
        return nivel;
    }

    public String getTareaPsicologica(String capacidad) {
        String consulta = "PREFIX ex: <Fhttp://www.semanticweb.org/casad/ontologies/2024/10/COGAF#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "SELECT (STR(?descripcion) AS ?descripcionTexto)\n" +
                "WHERE {\n" +
                "  ex:"+ capacidad +" ex:tarea ?tareaPsicologica . \n" +
                "  ?tareaPsicologica ex:descripcion ?descripcion . \n" +
                "}";
        Query query = QueryFactory.create(consulta);
        String tarea = "";
        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution sol = results.next();
                tarea = sol.getLiteral("descripcionTexto").toString();
                System.out.printf("Tarea psicologica: %s%n", tarea);
            }
        }
        return tarea;
    }

    public String getPruebaCognitiva(String capacidad) {
        String consulta = "PREFIX ex: <Fhttp://www.semanticweb.org/casad/ontologies/2024/10/COGAF#>\n" +
                "SELECT ?prueba\n" +
                "WHERE {\n" +
                "  ex:"+ capacidad +" ex:prueba ?prueba .\n" +
                "}";
        Query query = QueryFactory.create(consulta);
        String prueba = "";
        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution sol = results.next();
                Resource pruebaResource = sol.getResource("prueba");
                if (pruebaResource != null) {
                    prueba = pruebaResource.getLocalName();
                    System.out.printf("Nombre de la instancia: %s%n", prueba);
                }
            }
        }
        prueba = prueba.replace("_", " ");
        return prueba;
    }

    public String getTipoPrueba(String capacidad) {
        String consulta = "PREFIX ex: <Fhttp://www.semanticweb.org/casad/ontologies/2024/10/COGAF#>\n" +
                "SELECT ?tipoPrueba\n" +
                "WHERE {\n" +
                "  ex:"+ capacidad +" ex:tipoPrueba ?tipoPrueba .\n" +
                "}";
        Query query = QueryFactory.create(consulta);
        String tipoPrueba = "";
        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution sol = results.next();
                Resource pruebaResource = sol.getResource("tipoPrueba");
                if (pruebaResource != null) {
                    tipoPrueba = pruebaResource.getLocalName();
                    System.out.printf("Nombre de la instancia: %s%n", tipoPrueba);
                }
            }
        }
        return tipoPrueba;
    }

    public String getAplicacion(String capacidad) {
        String consulta = "PREFIX ex: <Fhttp://www.semanticweb.org/casad/ontologies/2024/10/COGAF#>\n" +
                "SELECT ?aplicacion\n" +
                "WHERE {\n" +
                "  ex:"+ capacidad +" ex:aplicacion ?aplicacion .\n" +
                "}";
        Query query = QueryFactory.create(consulta);
        String aplicacion = "";
        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution sol = results.next();
                Resource pruebaResource = sol.getResource("aplicacion");
                if (pruebaResource != null) {
                    aplicacion = pruebaResource.getLocalName();
                    System.out.printf("Nombre de la instancia: %s%n", aplicacion);
                }
            }
        }
        return aplicacion;
    }

    public String getMecanica(String capacidad) {
        String consulta = "PREFIX ex: <Fhttp://www.semanticweb.org/casad/ontologies/2024/10/COGAF#>\n" +
                "SELECT ?mecanica\n" +
                "WHERE {\n" +
                "  ex:"+ capacidad +" ex:mecanica ?mecanica .\n" +
                "}";
        Query query = QueryFactory.create(consulta);
        String mecanica = "";
        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution sol = results.next();
                Resource pruebaResource = sol.getResource("mecanica");
                if (pruebaResource != null) {
                    String uri = pruebaResource.getURI();
                    mecanica = uri.substring(uri.lastIndexOf("#") + 1);
                    System.out.printf("Nombre de la instancia: %s%n", mecanica);
                }
            }
        }
        mecanica = mecanica.replace("_", " ");
        return mecanica;
    }

    public String getActividadComplementaria(String capacidad) {
        String consulta = "PREFIX ex: <Fhttp://www.semanticweb.org/casad/ontologies/2024/10/COGAF#>\n" +
                "SELECT ?actividadComplementaria\n" +
                "WHERE {\n" +
                "  ex:"+ capacidad +" ex:actividad ?actividadComplementaria .\n" +
                "}";
        Query query = QueryFactory.create(consulta);
        String actividadComplementaria = "";
        try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution sol = results.next();
                Resource pruebaResource = sol.getResource("actividadComplementaria");
                if (pruebaResource != null) {
                    actividadComplementaria = pruebaResource.getLocalName();
                    System.out.printf("Nombre de la instancia: %s%n", actividadComplementaria);
                }
            }
        }
        return actividadComplementaria;
    }



}

