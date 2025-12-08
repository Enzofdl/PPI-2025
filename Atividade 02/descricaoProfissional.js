const paragInicial = document.getElementById("paragrafo-inicial").innerHTML;
const alunos = [
    { matricula: "12221bcc010", descricaoId: "descricao-12221bcc010" }, // Enzo
    { matricula: "12121bcc028", descricaoId: "descricao-12121bcc028" }, // Giovane
    { matricula: "12211bcc054", descricaoId: "descricao-12211bcc054" }, // Arthur
    { matricula: "12211bcc034", descricaoId: "descricao-12211bcc034" }, // Malcon
    { matricula: "12211bcc020", descricaoId: "descricao-12211bcc020" }, // Felipe
    { matricula: "12011bcc049", descricaoId: "descricao-12011bcc049" }  // Eduardo
];

document.getElementById("verificarBtn").addEventListener("click", function () {
    const matriculaDigitada = document.getElementById("matriculaInput").value.trim();
    const mensagem = document.getElementById("mensagem");

    if (matriculaDigitada === "") {
        mensagem.style.color = "#cc0000";
        mensagem.textContent = "Digite um número de matrícula antes de verificar.";
        return;
    } else {
        mensagem.textContent = "";
    }

    let encontrou = false;
    for (let i = 0; i < alunos.length; i++) {
        if (alunos[i].matricula === matriculaDigitada) {
            const paragrafoDescricao = document.getElementById(alunos[i].descricaoId);
            paragrafoDescricao.innerHTML = paragInicial;
            encontrou = true;
            break;
        }
    }

    if (encontrou) {
        mensagem.style.color = "#006400";
        mensagem.textContent = "Descrição substituída com sucesso!";
    } else {
        mensagem.style.color = "#cc0000";
        mensagem.textContent = "Matrícula não encontrada entre os integrantes do grupo.";
    }
});

