// URLs das APIs
const API_INSCRICAO = '/api/inscricoes';
const API_INSCRICAO_ACAO = '/api/inscricoes/acao';
const API_USUARIO = '/api/usuarios';
const API_EVENTO = '/api/eventos';

// Carrega dados ao iniciar
document.addEventListener('DOMContentLoaded', () => {
    carregarInscricoes();
    carregarUsuarios();
    carregarEventos();

    // Define data de hoje como padrão
    const hoje = new Date().toISOString().split('T')[0];
    document.getElementById('dataInscricao').value = hoje;
});

// Carrega lista de inscrições
async function carregarInscricoes() {
    try {
        const response = await fetch(API_INSCRICAO);
        const inscricoes = await response.json();

        const lista = document.getElementById('inscricao-list');
        const count = document.getElementById('inscricao-count');

        count.textContent = `${inscricoes.length} inscrição${inscricoes.length !== 1 ? 'ões' : ''}`;

        if (inscricoes.length === 0) {
            lista.innerHTML = '<div class="user-item"><p>Nenhuma inscrição cadastrada.</p></div>';
            return;
        }

        lista.innerHTML = inscricoes.map(insc => `
            <div class="user-item">
                <div class="user-info">
                    <h3>${insc.nomeUsuario}</h3>
                    <p><strong>Evento:</strong> ${insc.nomeEvento}</p>
                    <p><strong>Data de Inscrição:</strong> ${formatarData(insc.dataInscricao)}</p>
                </div>
                <div class="user-actions">
                    <button class="btn-action btn-edit" onclick="abrirModalEdicao(${insc.id}, ${insc.idUsuario}, ${insc.idEvento}, '${insc.dataInscricao}')" title="Editar">
                        ✏️
                    </button>
                    <button class="btn-action btn-delete" onclick="removerInscricao(${insc.id})" title="Excluir">
                        🗑️
                    </button>
                </div>
            </div>
        `).join('');

    } catch (error) {
        console.error('Erro ao carregar inscrições:', error);
        alert('Erro ao carregar inscrições');
    }
}

// Carrega lista de usuários para os selects
async function carregarUsuarios() {
    try {
        const response = await fetch(API_USUARIO);
        const usuarios = await response.json();

        const selectAdd = document.getElementById('idUsuario');
        const selectEdit = document.getElementById('edit-idUsuario');

        const options = usuarios.map(u =>
            `<option value="${u.id}">${u.nome}</option>`
        ).join('');

        selectAdd.innerHTML = '<option value="" disabled selected>Selecione um usuário</option>' + options;
        selectEdit.innerHTML = '<option value="" disabled selected>Selecione um usuário</option>' + options;

    } catch (error) {
        console.error('Erro ao carregar usuários:', error);
    }
}

// Carrega lista de eventos para os selects
async function carregarEventos() {
    try {
        const response = await fetch(API_EVENTO);
        const eventos = await response.json();

        const selectAdd = document.getElementById('idEvento');
        const selectEdit = document.getElementById('edit-idEvento');

        const options = eventos.map(e =>
            `<option value="${e.id}">${e.nome} - ${formatarData(e.data)}</option>`
        ).join('');

        selectAdd.innerHTML = '<option value="" disabled selected>Selecione um evento</option>' + options;
        selectEdit.innerHTML = '<option value="" disabled selected>Selecione um evento</option>' + options;

    } catch (error) {
        console.error('Erro ao carregar eventos:', error);
    }
}

// Adiciona nova inscrição
document.getElementById('form-inscricao').addEventListener('submit', async (e) => {
    e.preventDefault();

    const idUsuario = document.getElementById('idUsuario').value;
    const idEvento = document.getElementById('idEvento').value;
    const dataInscricao = document.getElementById('dataInscricao').value;

    // Validação no frontend
    if (!idUsuario || !idEvento || !dataInscricao) {
        alert('Por favor, preencha todos os campos!');
        return;
    }

    console.log('Enviando:', { idUsuario, idEvento, dataInscricao });

    // Enviar como URL encoded (igual ao formulário de eventos)
    const params = new URLSearchParams();
    params.append('idUsuario', idUsuario);
    params.append('idEvento', idEvento);
    params.append('dataInscricao', dataInscricao);

    try {
        const response = await fetch(API_INSCRICAO, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        });

        if (response.ok) {
            alert('Inscrição adicionada com sucesso!');
            e.target.reset();

            // Define data de hoje novamente
            const hoje = new Date().toISOString().split('T')[0];
            document.getElementById('dataInscricao').value = hoje;

            carregarInscricoes();
        } else {
            const errorText = await response.text();
            console.error('Erro do servidor:', errorText);
            alert('Erro ao adicionar inscrição: ' + errorText);
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao adicionar inscrição: ' + error.message);
    }
});

// Remove inscrição
async function removerInscricao(id) {
    if (!confirm('Tem certeza que deseja remover esta inscrição?')) {
        return;
    }

    try {
        const params = new URLSearchParams();
        params.append('acao', 'deletar');
        params.append('id', id);

        const response = await fetch(API_INSCRICAO_ACAO, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        });

        if (response.ok) {
            alert('Inscrição removida com sucesso!');
            carregarInscricoes();
        } else {
            alert('Erro ao remover inscrição');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao remover inscrição');
    }
}

// Abre modal de edição
function abrirModalEdicao(id, idUsuario, idEvento, dataInscricao) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-idUsuario').value = idUsuario;
    document.getElementById('edit-idEvento').value = idEvento;
    document.getElementById('edit-dataInscricao').value = dataInscricao;

    document.getElementById('modal-edicao').classList.remove('hidden');
}

// Fecha modal
function fecharModal() {
    document.getElementById('modal-edicao').classList.add('hidden');
    document.getElementById('form-edicao').reset();
}

// Fecha modal ao clicar fora
window.onclick = function (event) {
    const modal = document.getElementById('modal-edicao');
    if (event.target === modal) {
        fecharModal();
    }
}

// Edita inscrição
document.getElementById('form-edicao').addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('edit-id').value;
    const idUsuario = document.getElementById('edit-idUsuario').value;
    const idEvento = document.getElementById('edit-idEvento').value;
    const dataInscricao = document.getElementById('edit-dataInscricao').value;

    const params = new URLSearchParams();
    params.append('acao', 'alterar');
    params.append('id', id);
    params.append('idUsuario', idUsuario);
    params.append('idEvento', idEvento);
    params.append('dataInscricao', dataInscricao);

    try {
        const response = await fetch(API_INSCRICAO_ACAO, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        });

        if (response.ok) {
            alert('Inscrição atualizada com sucesso!');
            fecharModal();
            carregarInscricoes();
        } else {
            alert('Erro ao atualizar inscrição');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao atualizar inscrição');
    }
});

// Formata data para formato brasileiro
function formatarData(dataStr) {
    const [ano, mes, dia] = dataStr.split('-');
    return `${dia.padStart(2, '0')}/${mes.padStart(2, '0')}/${ano}`;
}

