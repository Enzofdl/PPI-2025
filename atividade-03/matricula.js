document.addEventListener("DOMContentLoaded", function() {

    const matricula = document.getElementById("matricula");
    const botaoBuscar = document.getElementById("botaoBuscar");
    const foto = document.getElementById("foto");
    const mensagem = document.getElementById("busca");

    const fotosSequenciais = [
        "img/1.jpeg", // 12211bcc034
        "img/2.jpeg", // 12121bcc028
        "img/3.jpeg", // 12211bcc054
        "img/4.jpeg", // 12211bcc020
        "img/5.jpeg", // 12221bcc010
        "img/6.jpeg"   // 12011bcc049
    ];

    let indiceFotoAtual = -1; // -1 indica que nenhuma foto está em exibição

    botaoBuscar.addEventListener("click", function (){
        const matriculaValor = matricula.value.trim().toLowerCase();

        foto.style.display = "none";
        foto.src = "";
        mensagem.textContent = "";
        indiceFotoAtual = -1;

        switch (matriculaValor){
            case "12211bcc034":
                foto.src = fotosSequenciais[0];
                indiceFotoAtual = 0;
                break;

            case "12121bcc028":
                foto.src = fotosSequenciais[1];
                indiceFotoAtual = 1;
                break;

            case "12211bcc054":
                foto.src = fotosSequenciais[2];
                indiceFotoAtual = 2;
                break;

            case "12211bcc020":
                foto.src = fotosSequenciais[3];
                indiceFotoAtual = 3;
                break;

            case "12221bcc010":
                foto.src = fotosSequenciais[4];
                indiceFotoAtual = 4;
                break;

            case "12011bcc049":
                foto.src = fotosSequenciais[5];
                indiceFotoAtual = 5;
                break;

            default:
                mensagem.textContent = "Matrícula não encontrada.";
                return;
        }

        foto.style.display = "block";
        foto.style.cursor = "pointer";
    });

    //Evento "Clickável"
    foto.addEventListener("click", function() {
        if (indiceFotoAtual >= 0) {
            indiceFotoAtual = (indiceFotoAtual + 1) % fotosSequenciais.length;

            foto.src = fotosSequenciais[indiceFotoAtual];
        }
    });

});