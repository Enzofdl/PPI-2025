/**
 * ================================================
 * ARQUIVO: script.js
 * DESCRICAO: Funcoes JavaScript para a pagina de
 *            gerenciamento de usuarios
 * ================================================
 */

// ================================================
// DADOS ESTATICOS DE EXEMPLO
// Array que simula os usuarios cadastrados
// Em uma aplicacao real, esses dados viriam do backend
// ================================================
let usuarios = [
    {
        id: 1,
        nome: "Maria Aparecida Silva",
        email: "maria.silva@email.com",
        telefone: "(11) 98765-4321"
    },
    {
        id: 2,
        nome: "Joao Santos",
        email: "joao.santos@email.com",
        telefone: "(21) 99876-5432"
    },
    {
        id: 3,
        nome: "Ana Carolina Oliveira",
        email: "ana.oliveira@email.com",
        telefone: "(31) 97654-3210"
    },
    {
        id: 4,
        nome: "Pedro Henrique Costa",
        email: "pedro.costa@email.com",
        telefone: "(41) 96543-2109"
    },
    {
        id: 5,
        nome: "Luisa Fernandes",
        email: "luisa.fernandes@email.com",
        telefone: "(51) 95432-1098"
    }
];

// Variavel para controlar o proximo ID a ser atribuido
let proximoId = 6;

// ================================================
// FUNCOES DE MANIPULACAO DO DOM
// ================================================

/**
 * Funcao executada quando a pagina e carregada
 * Inicializa os event listeners dos formularios
 */
document.addEventListener('DOMContentLoaded', function() {
    // Adiciona listener ao formulario de cadastro
    const formUsuario = document.getElementById('form-usuario');
    formUsuario.addEventListener('submit', adicionarUsuario);
    
    // Adiciona listener ao formulario de edicao
    const formEdicao = document.getElementById('form-edicao');
    formEdicao.addEventListener('submit', salvarEdicao);
    
    // Adiciona listener para fechar modal ao clicar fora
    const modal = document.getElementById('modal-edicao');
    modal.addEventListener('click', function(e) {
        if (e.target === modal) {
            fecharModal();
        }
    });
    
    console.log('Pagina carregada e listeners configurados!');
});

/**
 * Adiciona um novo usuario a lista
 * Chamada quando o formulario de cadastro e submetido
 * @param {Event} event - Evento de submit do formulario
 */
function adicionarUsuario(event) {
    // Previne o comportamento padrao do formulario (recarregar pagina)
    event.preventDefault();
    
    // Obtem os valores dos campos do formulario
    const nome = document.getElementById('nome').value.trim();
    const email = document.getElementById('email').value.trim();
    const telefone = document.getElementById('telefone').value.trim();
    
    // Validacao basica
    if (!nome || !email || !telefone) {
        alert('Por favor, preencha todos os campos!');
        return;
    }
    
    // Cria o objeto do novo usuario
    const novoUsuario = {
        id: proximoId++,
        nome: nome,
        email: email,
        telefone: telefone
    };
    
    // Adiciona ao array de usuarios
    usuarios.push(novoUsuario);
    
    // Cria e adiciona o card do novo usuario na lista
    const userList = document.getElementById('user-list');
    const novoCard = criarCardUsuario(novoUsuario);
    userList.appendChild(novoCard);
    
    // Atualiza o contador de usuarios
    atualizarContador();
    
    // Limpa o formulario
    event.target.reset();
    
    // Exibe mensagem de sucesso no console
    console.log('Usuario "' + nome + '" adicionado com sucesso!');
    
    // Feedback visual para o usuario
    alert('Usuario "' + nome + '" adicionado com sucesso!');
}

/**
 * Cria um elemento card para exibir um usuario
 * @param {Object} usuario - Objeto com dados do usuario
 * @returns {HTMLElement} - Elemento div do card
 */
function criarCardUsuario(usuario) {
    // Cria o container principal do card
    const card = document.createElement('div');
    card.className = 'user-card';
    card.setAttribute('data-id', usuario.id);
    
    // Define o HTML interno do card
    card.innerHTML = `
        <div class="user-info">
            <h3 class="user-name">${usuario.nome}</h3>
            <p class="user-email">${usuario.email}</p>
            <p class="user-phone">${usuario.telefone}</p>
        </div>
        <div class="user-actions">
            <button class="btn btn-edit" onclick="editarUsuario(${usuario.id})" title="Editar usuario">
                Alterar
            </button>
            <button class="btn btn-delete" onclick="excluirUsuario(${usuario.id})" title="Excluir usuario">
                Excluir
            </button>
        </div>
    `;
    
    return card;
}

/**
 * Atualiza o contador de usuarios exibido na pagina
 */
function atualizarContador() {
    const contador = document.getElementById('user-count');
    const total = usuarios.length;
    contador.textContent = total + ' usuario' + (total !== 1 ? 's' : '');
}

// ================================================
// FUNCOES DE EDICAO DE USUARIO
// ================================================

/**
 * Abre o modal de edicao com os dados do usuario
 * @param {number} id - ID do usuario a ser editado
 */
function editarUsuario(id) {
    // Busca o usuario no array
    const usuario = usuarios.find(u => u.id === id);
    
    if (!usuario) {
        alert('Usuario nao encontrado!');
        return;
    }
    
    // Preenche os campos do formulario de edicao
    document.getElementById('edit-id').value = usuario.id;
    document.getElementById('edit-nome').value = usuario.nome;
    document.getElementById('edit-email').value = usuario.email;
    document.getElementById('edit-telefone').value = usuario.telefone;
    
    // Exibe o modal
    abrirModal();
    
    console.log('Editando usuario: ' + usuario.nome + ' (ID: ' + id + ')');
}

/**
 * Salva as alteracoes feitas no usuario
 * Chamada quando o formulario de edicao e submetido
 * @param {Event} event - Evento de submit do formulario
 */
function salvarEdicao(event) {
    // Previne o comportamento padrao do formulario
    event.preventDefault();
    
    // Obtem os valores do formulario de edicao
    const id = parseInt(document.getElementById('edit-id').value);
    const nome = document.getElementById('edit-nome').value.trim();
    const email = document.getElementById('edit-email').value.trim();
    const telefone = document.getElementById('edit-telefone').value.trim();
    
    // Validacao basica
    if (!nome || !email || !telefone) {
        alert('Por favor, preencha todos os campos!');
        return;
    }
    
    // Busca o indice do usuario no array
    const indice = usuarios.findIndex(u => u.id === id);
    
    if (indice === -1) {
        alert('Usuario nao encontrado!');
        return;
    }
    
    // Atualiza os dados do usuario no array
    usuarios[indice] = {
        id: id,
        nome: nome,
        email: email,
        telefone: telefone
    };
    
    // Atualiza o card na interface
    atualizarCardUsuario(id, usuarios[indice]);
    
    // Fecha o modal
    fecharModal();
    
    console.log('Usuario "' + nome + '" atualizado com sucesso!');
    alert('Usuario "' + nome + '" atualizado com sucesso!');
}

/**
 * Atualiza visualmente o card de um usuario na lista
 * @param {number} id - ID do usuario
 * @param {Object} usuario - Dados atualizados do usuario
 */
function atualizarCardUsuario(id, usuario) {
    // Encontra o card pelo atributo data-id
    const card = document.querySelector('.user-card[data-id="' + id + '"]');
    
    if (card) {
        // Atualiza o conteudo do card
        card.querySelector('.user-name').textContent = usuario.nome;
        card.querySelector('.user-email').textContent = usuario.email;
        card.querySelector('.user-phone').textContent = usuario.telefone;
    }
}

// ================================================
// FUNCOES DE EXCLUSAO DE USUARIO
// ================================================

/**
 * Exclui um usuario da lista
 * @param {number} id - ID do usuario a ser excluido
 */
function excluirUsuario(id) {
    // Busca o usuario para mostrar o nome na confirmacao
    const usuario = usuarios.find(u => u.id === id);
    
    if (!usuario) {
        alert('Usuario nao encontrado!');
        return;
    }
    
    // Solicita confirmacao do usuario
    const confirmacao = confirm('Tem certeza que deseja excluir "' + usuario.nome + '"?\n\nEsta acao nao pode ser desfeita.');
    
    if (!confirmacao) {
        console.log('Exclusao cancelada pelo usuario.');
        return;
    }
    
    // Remove o usuario do array
    usuarios = usuarios.filter(u => u.id !== id);
    
    // Remove o card da interface com animacao
    const card = document.querySelector('.user-card[data-id="' + id + '"]');
    if (card) {
        // Adiciona efeito de fade-out antes de remover
        card.style.transition = 'all 0.3s ease';
        card.style.opacity = '0';
        card.style.transform = 'translateX(-20px)';
        
        // Remove o elemento apos a animacao
        setTimeout(() => {
            card.remove();
            atualizarContador();
        }, 300);
    }
    
    console.log('Usuario "' + usuario.nome + '" excluido com sucesso!');
}

// ================================================
// FUNCOES DO MODAL
// ================================================

/**
 * Abre o modal de edicao
 * Remove a classe 'hidden' para exibir o modal
 */
function abrirModal() {
    const modal = document.getElementById('modal-edicao');
    modal.classList.remove('hidden');
    
    // Foca no primeiro campo do formulario
    document.getElementById('edit-nome').focus();
    
    // Desabilita scroll do body quando modal esta aberto
    document.body.style.overflow = 'hidden';
}

/**
 * Fecha o modal de edicao
 * Adiciona a classe 'hidden' para ocultar o modal
 */
function fecharModal() {
    const modal = document.getElementById('modal-edicao');
    modal.classList.add('hidden');
    
    // Reabilita scroll do body
    document.body.style.overflow = 'auto';
    
    // Limpa o formulario de edicao
    document.getElementById('form-edicao').reset();
}

// ================================================
// FUNCOES UTILITARIAS
// ================================================

/**
 * Formata um numero de telefone para exibicao
 * @param {string} telefone - Numero de telefone
 * @returns {string} - Telefone formatado
 */
function formatarTelefone(telefone) {
    // Remove caracteres nao numericos
    const numeros = telefone.replace(/\D/g, '');
    
    // Aplica a mascara (XX) XXXXX-XXXX
    if (numeros.length === 11) {
        return '(' + numeros.slice(0,2) + ') ' + numeros.slice(2,7) + '-' + numeros.slice(7);
    }
    
    return telefone;
}

/**
 * Valida formato de email
 * @param {string} email - Email a ser validado
 * @returns {boolean} - true se valido, false caso contrario
 */
function validarEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

// ================================================
// LOG INICIAL
// ================================================
console.log('Sistema de Gerenciamento de Usuarios');
console.log('Total de usuarios carregados: ' + usuarios.length);
