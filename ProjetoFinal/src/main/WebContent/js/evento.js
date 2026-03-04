// URLs das APIs
const API_EVENTOS = 'api/eventos';
const API_EVENTOS_ACAO = 'api/eventos/acao';
const API_CATEGORIAS = 'api/categorias';

document.addEventListener('DOMContentLoaded', function () {
    carregarCategorias();
    carregarEventos();

    document.getElementById('form-evento').addEventListener('submit', adicionarEvento);
    document.getElementById('form-edicao').addEventListener('submit', salvarEdicao);

    const modal = document.getElementById('modal-edicao');
    window.onclick = function (event) {
        if (event.target === modal) {
            fecharModal();
        }
    }
});

function escaparString(str) {
    return str ? str.replace(/'/g, "\\'").replace(/"/g, '\\"') : '';
}

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
            // Formata a data
            let dataFormatada = evento.data;
            if (evento.data && evento.data.includes('-')) {
                const [ano, mes, dia] = evento.data.split('-');
                dataFormatada = `${dia}/${mes}/${ano}`;
            }

            // Define se mostra a imagem ou um placeholder (quadrado cinza com ícone)
            let imgHTML = evento.imagem
                ? `<img src="uploads/${evento.imagem}" alt="Capa" style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px; margin-right: 15px; border: 1px solid #ddd;">`
                : `<div style="width: 80px; height: 80px; background: #f1f5f9; border-radius: 8px; margin-right: 15px; display: flex; align-items: center; justify-content: center; font-size: 2rem; border: 1px solid #e2e8f0;">📅</div>`;

            // O Card principal agora usa display: flex para alinhar a imagem com o texto
            return `
            <div class="user-item" style="display: flex; align-items: center;">
                ${imgHTML}
                <div class="user-info" style="flex: 1;">
                    <h3 style="margin-bottom: 5px;">${evento.nome}</h3>
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
// 3. ADICIONAR EVENTO (Modificado para suportar arquivo)
// ==============================================================
function adicionarEvento(event) {
    event.preventDefault();

    // SUBSTITUÍDO: URLSearchParams por FormData
    const formData = new FormData();
    formData.append('nome', document.getElementById('nome').value);
    formData.append('data', document.getElementById('data').value);
    formData.append('local', document.getElementById('local').value);
    formData.append('categoria', document.getElementById('categoria').value);

    // Adiciona o arquivo da imagem se o usuário tiver selecionado um
    const imagemInput = document.getElementById('imagem');
    if (imagemInput.files.length > 0) {
        formData.append('imagem', imagemInput.files[0]);
    }

    fetch(API_EVENTOS, {
        method: 'POST',
        // IMPORTANTE: Ao usar FormData, não definimos o 'Content-Type'.
        // O navegador define automaticamente como multipart/form-data e gera o "boundary".
        body: formData
    })
        .then(response => {
            if (response.ok) {
                alert('Evento cadastrado com sucesso!');
                event.target.reset();
                carregarEventos();
            } else {
                alert('Erro ao cadastrar evento.');
            }
        })
        .catch(error => console.error('Erro:', error));
}

// ==============================================================
// 4. REMOVER EVENTO (Mantido intacto)
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
            carregarEventos();
            alert('Evento removido com sucesso!');
        })
        .catch(error => console.error('Erro:', error));
}

function abrirModalEdicao(id, nome, data, local, catId) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-nome').value = nome;
    document.getElementById('edit-data').value = data;
    document.getElementById('edit-local').value = local;
    document.getElementById('edit-categoria').value = catId;

    document.getElementById('modal-edicao').classList.remove('hidden');
}

// ==============================================================
// 6. SALVAR EDIÇÃO (Modificado para suportar arquivo)
// ==============================================================
function salvarEdicao(event) {
    event.preventDefault();

    // SUBSTITUÍDO: URLSearchParams por FormData
    const formData = new FormData();
    formData.append('acao', 'alterar');
    formData.append('id', document.getElementById('edit-id').value);
    formData.append('nome', document.getElementById('edit-nome').value);
    formData.append('data', document.getElementById('edit-data').value);
    formData.append('local', document.getElementById('edit-local').value);
    formData.append('categoria', document.getElementById('edit-categoria').value);

    // Adiciona o arquivo da imagem se o usuário tiver selecionado um no edit
    const editImagemInput = document.getElementById('edit-imagem');
    if (editImagemInput.files.length > 0) {
        formData.append('imagem', editImagemInput.files[0]);
    }

    fetch(API_EVENTOS_ACAO, {
        method: 'POST',
        // Novamente, sem headers fixos para o navegador injetar o boundary do multipart
        body: formData
    })
        .then(() => {
            alert('Evento alterado com sucesso!');
            fecharModal();
            carregarEventos();
        })
        .catch(error => console.error('Erro:', error));
}

function fecharModal() {
    document.getElementById('modal-edicao').classList.add('hidden');
    document.getElementById('form-edicao').reset();
}