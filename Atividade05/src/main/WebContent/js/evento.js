// URL base da API (Assumindo que seus servlets estão mapeados em /api/...)
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
    modal.addEventListener('click', function (e) {
        if (e.target === modal) fecharModal();
    });
});

// Função auxiliar para escapar aspas em strings
function escaparString(str) {
    return str.replace(/'/g, "\\'").replace(/"/g, '\\"');
}

// ==============================================================
// 1. CARREGAR CATEGORIAS (Do Banco para o Select)
// ==============================================================
function carregarCategorias() {
    fetch(`${API_URL}/categorias`) // Chama o CategoriaServlet
        .then(response => response.json())
        .then(categorias => {
            // Pega os dois selects (do cadastro e da edição)
            const selects = [
                document.getElementById('categoria'),
                document.getElementById('edit-categoria')
            ];

            selects.forEach(select => {
                select.innerHTML = '<option value="" disabled selected>Selecione...</option>';

                categorias.forEach(cat => {
                    // Cria uma <option value="ID">NOME</option>
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
    fetch(`${API_URL}/eventos`) // Chama o EventoServlet (GET)
        .then(response => response.json())
        .then(eventos => {
            renderizarLista(eventos);
        })
        .catch(error => console.error('Erro ao listar eventos:', error));
}

function renderizarLista(eventos) {
    const lista = document.getElementById('event-list');
    lista.innerHTML = '';

    eventos.forEach(evento => {
        console.log(evento);
        const card = criarCardEvento(evento);
        lista.appendChild(card);
    });

    // Atualiza contador
    const count = eventos.length;
    document.getElementById('event-count').textContent = `${count} evento${count !== 1 ? 's' : ''}`;
}

function criarCardEvento(evento) {
    const card = document.createElement('div');
    card.className = 'user-card';
    card.setAttribute('data-id', evento.id);

    // Formata a data. O Java manda "yyyy-mm-dd", formatamos manualmente
    const [ano, mes, dia] = evento.data.split('-');
    const dataFormatada = `${dia.padStart(2, '0')}/${mes.padStart(2, '0')}/${ano}`;

    card.innerHTML = `
        <div class="user-info">
            <h3 class="user-name">${evento.nome}</h3>
            <p class="user-email"><strong>Data:</strong> ${dataFormatada}</p>
            <p class="user-phone"><strong>Local:</strong> ${evento.local}</p>
            <p style="font-size: 0.85rem; color: #666; margin-top: 5px;">
                <!-- Aqui usamos o nome da categoria vindo do JSON -->
                <span style="background: #eef; padding: 2px 6px; border-radius: 4px; color: #4a6fa5;">
                    ${evento.categoria}
                </span>
            </p>
        </div>
        <div class="user-actions">
            <!-- Passamos os dados para a função de editar -->
            <button class="btn btn-edit" onclick="prepararEdicao(${evento.id}, '${evento.nome}', '${evento.data}', '${evento.local}', ${evento.categoriaId})" title="Editar">
                Alterar
            </button>
            <button class="btn btn-delete" onclick="excluirEvento(${evento.id})" title="Excluir">
                Excluir
            </button>
        </div>
    `;
    return card;
}

// ==============================================================
// 3. ADICIONAR EVENTO (Do Form para o Banco)
// ==============================================================
function adicionarEvento(event) {
    event.preventDefault();

    // Prepara os dados para enviar como se fosse um formulário HTML padrão
    const formData = new URLSearchParams();
    formData.append('nome', document.getElementById('nome').value);
    formData.append('data', document.getElementById('data').value);
    formData.append('local', document.getElementById('local').value);
    formData.append('categoria', document.getElementById('categoria').value); // Envia o ID

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
                listarEventos(); // Recarrega a lista para mostrar o novo item
            } else {
                alert('Erro ao cadastrar evento.');
            }
        })
        .catch(error => console.error('Erro:', error));
}

// ==============================================================
// 4. EXCLUIR EVENTO
// ==============================================================
function excluirEvento(id) {
    if (!confirm("Deseja realmente excluir este evento?")) return;

    const formData = new URLSearchParams();
    formData.append('acao', 'deletar');
    formData.append('id', id);

    fetch(`${API_URL}/eventos/acao`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: formData
    })
        .then(() => {
            listarEventos(); // Atualiza a lista
        })
        .catch(error => console.error('Erro:', error));
}

// ==============================================================
// 5. EDITAR EVENTO
// ==============================================================
function prepararEdicao(id, nome, data, local, catId) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-nome').value = nome;
    document.getElementById('edit-data').value = data;
    document.getElementById('edit-local').value = local;
    document.getElementById('edit-categoria').value = catId; // Seleciona a opção correta no select

    abrirModal();
}

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
            listarEventos(); // Atualiza a lista
        })
        .catch(error => console.error('Erro:', error));
}

// ==============================================================
// UTILITARIOS DO MODAL
// ==============================================================
function abrirModal() {
    document.getElementById('modal-edicao').classList.remove('hidden');
    document.body.style.overflow = 'hidden';
}

function fecharModal() {
    document.getElementById('modal-edicao').classList.add('hidden');
    document.body.style.overflow = 'auto';
}