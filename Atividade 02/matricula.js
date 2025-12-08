document.addEventListener("DOMContentLoaded", function() {

const matricula = document.getElementById("matricula");
const botaoBuscar = document.getElementById("botaoBuscar");
const foto = document.getElementById("foto");
const mensagem = document.getElementById("busca");

botaoBuscar.addEventListener("click", function (){
    const matriculaValor = matricula.value.trim().toLowerCase();

    //Limpa pesquisa anterior
    foto.style.display = "none";
    foto.src = "";
    mensagem.textContent = "";

    switch (matriculaValor){
        case "12211bcc034":
            foto.src = "img/1.jpg";
            foto.style.display = "block";
            break;

        case "12121bcc028":
            foto.src = "img/2.jpg";
            foto.style.display = "block";
            break;

        case "12211bcc054":
            foto.src = "img/3.jpg";
            foto.style.display = "block";
            break;

        case "12211bcc020":
            foto.src = "img/4.jpg";
            foto.style.display = "block";
            break;

        case "12221bcc010":
            foto.src = "img/5.jpg";
            foto.style.display = "block";
            break;

        case "12011bcc049":
            foto.src = "img/6.jpg";
            foto.style.display = "block";
            break;

        default:
            mensagem.textContent = "Matrícula não encontrada."
    }
});

});