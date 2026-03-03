// URL base da API
const API_URL = "api";

document.addEventListener('DOMContentLoaded', function () {
    // 1. Carrega as categorias do banco para preencher os <select>
    carregarCategorias();

    // 2. Carrega a lista de eventos salva no banco
    listarEventos();

    // Listeners dos formulários
    document.getElementById('form-evento').addEventListener('submit', adicionarEvento);
    document.getElementById('form-edicao').addEventListener('submit', salvarEdicao);

    // Fechar modal ao clicar fora
    const modal = document.getElementById('modal-edicao');
    window.onclick = function (event) {
        if (event.target === modal) {
            fecharModal();
        }
    }
});

// Função auxiliar para escapar aspas em strings
function escaparString(str) {
    return str ? str.replace(/'/g, "\\'").replace(/"/g, '\\"') : '';
}

// ==============================================================
// 1. CARREGAR CATEGORIAS (Do Banco para o Select)
// ==============================================================
function carregarCategorias() {
    fetch(`${API_URL}/categorias`)
        .then(response => response.json())
        .then(categorias => {
            const selects = [
                document.getElementById('categoria'),
                document.getElementById('edit-categoria')
            ];

            selects.forEach(select => {
                select.innerHTML = '<option value="" disabled selected>Selecione...</option>';
                categorias.forEach(cat => {
                    const option = document.createElement('option');
                    option.value = cat.id;
                    option.textContent = cat.nome;
                    select.appendChild(option);
                });
            });
        })
        .catch(error => console.error('Erro ao carregar categorias:', error));
}

// ==============================================================
// 2. LISTAR EVENTOS (Do Banco para a Tela)
// ==============================================================
function listarEventos() {
    fetch(`${API_URL}/eventos`)
        .then(response => response.json())
        .then(eventos => {
            renderizarLista(eventos);
        })
        .catch(error => console.error('Erro ao listar eventos:', error));
}

function renderizarLista(eventos) {
    const lista = document.getElementById('event-list');
    lista.innerHTML = '';

    const count = eventos.length;
    document.getElementById('event-count').textContent = `${count} evento${count !== 1 ? 's' : ''}`;

    if (eventos.length === 0) {
        lista.innerHTML = '<div class="user-card"><p style="padding:10px">Nenhum evento cadastrado.</p></div>';
        return;
    }

    eventos.forEach(evento => {
        const card = criarCardEvento(evento);
        lista.appendChild(card);
    });
}

function criarCardEvento(evento) {
    const card = document.createElement('div');
    card.className = 'user-card';
    card.setAttribute('data-id', evento.id);

    // Formata a data (yyyy-mm-dd -> dd/mm/yyyy)
    const [ano, mes, dia] = evento.data.split('-');
    const dataFormatada = `${dia}/${mes}/${ano}`;

    // ALTERAÇÃO AQUI: Estrutura visual igual ao categoria.js
    card.innerHTML = `
        <div class="user-info">
            <h3 class="user-name">${evento.nome}</h3>
            <p class="user-email"><strong>Data:</strong> ${dataFormatada}</p>
            <p class="user-phone"><strong>Local:</strong> ${evento.local}</p>
            <p style="font-size: 0.85rem; color: #666; margin-top: 5px;">
                <span style="background: #eef; padding: 2px 6px; border-radius: 4px; color: #4a6fa5;">
                    ${evento.categoriaNome || 'Categoria ' + evento.categoria}
                </span>
            </p>
        </div>
        <div class="user-actions">
            <button class="btn-action btn-edit" onclick="abrirModalEdicao(${evento.id}, '${escaparString(evento.nome)}', '${evento.data}', '${escaparString(evento.local)}', ${evento.categoria})" title="Editar">
                ✏️
            </button>
            <button class="btn-action btn-delete" onclick="removerEvento(${evento.id})" title="Excluir">
                🗑️
            </button>
        </div>
    `;
    return card;
}

// ==============================================================
// 3. ADICIONAR EVENTO
// ==============================================================
function adicionarEvento(event) {
    event.preventDefault();

    const formData = new URLSearchParams();
    formData.append('nome', document.getElementById('nome').value);
    formData.append('data', document.getElementById('data').value);
    formData.append('local', document.getElementById('local').value);
    formData.append('categoria', document.getElementById('categoria').value);

    fetch(`${API_URL}/eventos`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: formData
    })
        .then(response => {
            if (response.ok) {
                alert('Evento cadastrado com sucesso!');
                event.target.reset();
                listarEventos();
            } else {
                alert('Erro ao cadastrar evento.');
            }
        })
        .catch(error => console.error('Erro:', error));
}

// ==============================================================
// 4. REMOVER EVENTO (Renomeado de excluirEvento)
// ==============================================================
function removerEvento(id) {
    if (!confirm("Tem certeza que deseja remover este evento?")) return;

    const formData = new URLSearchParams();
    formData.append('acao', 'deletar');
    formData.append('id', id);

    fetch(`${API_URL}/eventos/acao`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: formData
    })
        .then(() => {
            listarEventos();
            alert('Evento removido com sucesso!');
        })
        .catch(error => console.error('Erro:', error));
}

// ==============================================================
// 5. ABRIR MODAL EDIÇÃO (Renomeado de prepararEdicao)
// ==============================================================
function abrirModalEdicao(id, nome, data, local, catId) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-nome').value = nome;
    document.getElementById('edit-data').value = data;
    document.getElementById('edit-local').value = local;
    document.getElementById('edit-categoria').value = catId;

    document.getElementById('modal-edicao').classList.remove('hidden');
}

// ==============================================================
// 6. SALVAR EDIÇÃO
// ==============================================================
function salvarEdicao(event) {
    event.preventDefault();

    const formData = new URLSearchParams();
    formData.append('acao', 'alterar');
    formData.append('id', document.getElementById('edit-id').value);
    formData.append('nome', document.getElementById('edit-nome').value);
    formData.append('data', document.getElementById('edit-data').value);
    formData.append('local', document.getElementById('edit-local').value);
    formData.append('categoria', document.getElementById('edit-categoria').value);

    fetch(`${API_URL}/eventos/acao`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: formData
    })
        .then(() => {
            alert('Evento alterado com sucesso!');
            fecharModal();
            listarEventos();
        })
        .catch(error => console.error('Erro:', error));
}

// ==============================================================
// UTILITARIOS DO MODAL
// ==============================================================
function fecharModal() {
    document.getElementById('modal-edicao').classList.add('hidden');
    // Limpa o form de edição ao fechar para evitar dados antigos
    document.getElementById('form-edicao').reset();
}