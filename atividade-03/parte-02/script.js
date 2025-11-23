// Ao carregar a página, dá foco no primeiro campo
window.onload = function () {
  document.getElementById("titulo").focus();
};

function gerarRelatorio() {
  const titulo = document.getElementById("titulo").value.trim();
  const descricao = document.getElementById("descricao").value.trim();
  const inicio = document.getElementById("inicio").value;
  const empresa = document.getElementById("empresa").value.trim();
  const categoria = document.getElementById("categoria").value;

  // Validação com HTML5 (required)
  const form = document.getElementById("formEvento");
  if (!form.checkValidity()) {
      form.reportValidity();
      return;
  }

  // Monta o relatório
  const texto = `
      <strong>Título:</strong> ${titulo}<br>
      <strong>Descrição:</strong> ${descricao}<br>
      <strong>Data/Hora de Início:</strong> ${inicio}<br>
      <strong>Empresa:</strong> ${empresa}<br>
      <strong>Categoria:</strong> ${categoria}
  `;

  document.getElementById("textoRelatorio").innerHTML = texto;

  // Define imagem baseada no select
  const imagens = {
      corporativo: "../img/parte-02/corporativo.jpg",
      show: "../img/parte-02/show.jpg",
      workshop: "../img/parte-02/workshop.jpg",
      esportivo: "../img/parte-02/esportivo.jpg"
  };

  document.getElementById("imgCategoria").src = imagens[categoria];

  // Exibe relatório
  document.getElementById("relatorio").style.display = "block";
}
