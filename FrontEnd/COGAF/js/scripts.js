function addClass() {
  document.body.classList.add("sent");
}

sendLetter.addEventListener("click", addClass);

function actualizarCapacidades() {
  const opcionesPorFuncionCognitiva = {
    ATENCION: ["Comunicacion Asertiva", "Concentracion"],
    MEMORIA_OPERATIVA: ["Supervision", "Planificacion", "Decision"],
    PERCEPCION: ["Mantener Equilibrio", "Inspeccion"],
    FLEXIBILIDAD_COGNITIVA: ["Comprension", "Evaluacion"],
    CONTROL_INHIBITORIO: ["Autocuidado", "Control"],
  };
  const capacidad = document.getElementById("funcionCognitiva").value;
  const selectOpcion = document.getElementById("capacidad");

  selectOpcion.innerHTML =
    '<option value="" disabled selected>Capacidad</option>';

  if (capacidad in opcionesPorFuncionCognitiva) {
    opcionesPorFuncionCognitiva[capacidad].forEach((opcion) => {
      const nuevaOpcion = document.createElement("option");
      nuevaOpcion.value = opcion;
      nuevaOpcion.textContent = opcion;
      selectOpcion.appendChild(nuevaOpcion);
    });
  }
}

async function procesarFormulario(event) {
  event.preventDefault();

  const nombre = document.getElementById("nombre").value;
  const funcionCognitiva = document.getElementById("funcionCognitiva").value;
  const capacidad = document.getElementById("capacidad").value;

  const funcionCognitivaRequest = {
    funcionCognitiva: funcionCognitiva,
    capacidad: capacidad,
  };

  const funcionCognitivaRequestJSON = JSON.stringify(funcionCognitivaRequest);
  try {
    const response = await fetch(
      "http://localhost:8080/cogaf/ontology/recomendacion",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: funcionCognitivaRequestJSON,
      }
    );

    if (!response.ok) {
      throw new Error(
        `Error en la respuesta: ${response.status} ${response.statusText}`
      );
    }

    const data = await response.json();

    sessionStorage.setItem(
      "recomendacion",
      JSON.stringify({
        nombre,
        funcionCognitiva: funcionCognitiva.replace("_", " "),
        capacidad: capacidad,
        tipo: data.tipo,
        nivel: data.nivel,
        actividadComplementaria: data.actividadComplementaria,
        mecanica: data.mecanica,
        pruebaCognitiva: data.pruebaCognitiva,
        tipoPrueba: data.tipoPrueba,
        aplicacion: data.aplicacion,
        tareaPsicologica: data.tareaPsicologica,
      })
    );

    console.log("Datos procesados y guardados exitosamente:", data);
  } catch (error) {
    console.error("Error al consultar la API:", error);
  }

  window.location.href = "/COGAF/paginas/resultado.html";
}

function cargarInformacion() {
  const resultado = document.getElementById("resultado");

  const datosGuardados = sessionStorage.getItem("recomendacion");

  if (datosGuardados) {
    const datos = JSON.parse(datosGuardados);

    const mensaje = `
      <div class="card">
        <h2 class="card-header">${datos.nombre}</h2>
        <div class="card-body">
          <h5>Funcion Cognitiva: ${datos.funcionCognitiva}</h5> 
          <h5>Capacidad: ${datos.capacidad}</h5> 
          <p>Hemos preparado esta recomendación personalizada para ti:</p>
          <table class="table">
            <tr>
              <th>Tipo</th>
              <td>${datos.tipo}</td>
            </tr>
            <tr>
              <th>Nivel</th>
              <td>${datos.nivel}</td>
            </tr>
            <tr>
              <th>Categoria de juego</th>
              <td>${datos.actividadComplementaria}</td>
            </tr>
            <tr>
              <th>Mecánica</th>
              <td>${datos.mecanica}</td>
            </tr>
            <tr>
              <th>Prueba Cognitiva</th>
              <td>${datos.pruebaCognitiva}</td>
            </tr>
            <tr>
              <th>Tipo de Prueba</th>
              <td>${datos.tipoPrueba}</td>
            </tr>
            <tr>
              <th>Aplicación</th>
              <td>${datos.aplicacion}</td>
            </tr>
            <tr>
              <th>Tarea Psicológica</th>
              <td>${datos.tareaPsicologica}</td>
            </tr>
          </table>
        </div>
      </div>
    `;

    resultado.innerHTML = mensaje;
  } else {
    resultado.innerHTML = `
      <div class="error">
        <h2>⚠️ Error</h2>
        <p>No se encontraron datos para mostrar. Por favor, regresa al formulario y completa el proceso nuevamente.</p>
      </div>
    `;
  }
}

function volverAInicio() {
  localStorage.clear();
  sessionStorage.clear();  
  window.location.href = "../index.html";
}
