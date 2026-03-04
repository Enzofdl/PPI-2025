// URLs das APIs
const API_EVENTOS = 'api/eventos';
const API_EVENTOS_ACAO = 'api/eventos/acao';
const API_CATEGORIAS = 'api/categorias';

document.addEventListener('DOMContentLoaded', function () {
    // 1. Carrega as categorias do banco para preencher os <select>
    carregarCategorias();

    // 2. Carrega a lista de eventos (agora chamando a função correta)
    carregarEventos();

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
    fetch(API_CATEGORIAS)
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
// 2. CARREGAR EVENTOS (Idêntico ao usuario.js)
// ==============================================================
async function carregarEventos() {
    try {
        const response = await fetch(API_EVENTOS);
        const eventos = await response.json();

        const lista = document.getElementById('event-list');
        const count = document.getElementById('event-count');

        count.textContent = `${eventos.length} evento${eventos.length !== 1 ? 's' : ''}`;

        if (eventos.length === 0) {
            lista.innerHTML = '<div class="user-item"><p>Nenhum evento cadastrado.</p></div>';
            return;
        }

        lista.innerHTML = eventos.map(evento => {
            // Formata a data de yyyy-mm-dd para dd/mm/yyyy
            let dataFormatada = evento.data;
            if (evento.data && evento.data.includes('-')) {
                const [ano, mes, dia] = evento.data.split('-');
                dataFormatada = `${dia}/${mes}/${ano}`;
            }

            // A estrutura HTML abaixo (user-item) herda o CSS perfeitamente
            return `
            <div class="user-item">
                <div class="user-info">
                    <h3>${evento.nome}</h3>
                    <p><strong>Data:</strong> ${dataFormatada}</p>
                    <p><strong>Local:</strong> ${evento.local}</p>
                    <p><strong>Categoria:</strong> ${evento.categoriaNome || evento.categoria}</p>
                </div>
                <div class="user-actions">
                    <button class="btn-action btn-edit" onclick="abrirModalEdicao(${evento.id}, '${escaparString(evento.nome)}', '${evento.data}', '${escaparString(evento.local)}', ${evento.categoriaId || evento.categoria})" title="Editar">
                        ✏️
                    </button>
                    <button class="btn-action btn-delete" onclick="removerEvento(${evento.id})" title="Excluir">
                        🗑️
                    </button>
                </div>
            </div>
            `;
        }).join('');

    } catch (error) {
        console.error('Erro ao carregar eventos:', error);
        alert('Erro ao carregar eventos');
    }
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

    fetch(API_EVENTOS, {
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
                carregarEventos(); // Recarrega a lista
            } else {
                alert('Erro ao cadastrar evento.');
            }
        })
        .catch(error => console.error('Erro:', error));
}

// ==============================================================
// 4. REMOVER EVENTO
// ==============================================================
function removerEvento(id) {
    if (!confirm("Tem certeza que deseja remover este evento?")) return;

    const formData = new URLSearchParams();
    formData.append('acao', 'deletar');
    formData.append('id', id);

    fetch(API_EVENTOS_ACAO, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: formData
    })
        .then(() => {
            carregarEventos(); // Atualiza a lista
            alert('Evento removido com sucesso!');
        })
        .catch(error => console.error('Erro:', error));
}

// ==============================================================
// 5. ABRIR MODAL EDIÇÃO
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

    fetch(API_EVENTOS_ACAO, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: formData
    })
        .then(() => {
            alert('Evento alterado com sucesso!');
            fecharModal();
            carregarEventos(); // Atualiza a lista
        })
        .catch(error => console.error('Erro:', error));
}

// ==============================================================
// UTILITARIOS DO MODAL
// ==============================================================
function fecharModal() {
    document.getElementById('modal-edicao').classList.add('hidden');
    document.getElementById('form-edicao').reset();
}