// URLs das APIs
const API_CATEGORIA = 'api/categorias';
const API_CATEGORIA_ACAO = 'api/categorias/acao';

// Carrega dados ao iniciar
document.addEventListener('DOMContentLoaded', () => {
    carregarCategorias();
});

// Carrega lista de categorias
async function carregarCategorias() {
    try {
        const response = await fetch(API_CATEGORIA);
        const categorias = await response.json();

        const lista = document.getElementById('categoria-list');
        const count = document.getElementById('categoria-count');

        count.textContent = `${categorias.length} categoria${categorias.length !== 1 ? 's' : ''}`;

        if (categorias.length === 0) {
            lista.innerHTML = '<div class="user-item"><p>Nenhuma categoria cadastrada.</p></div>';
            return;
        }

        lista.innerHTML = categorias.map(cat => `
            <div class="user-item">
                <div class="user-info">
                    <h3>${cat.nome}</h3>
                    <p style="color: #666; font-size: 0.9rem;">${cat.descricao || 'Sem descrição'}</p>
                </div>
                <div class="user-actions">
                    <button class="btn-action btn-edit" onclick="abrirModalEdicao(${cat.id}, '${escaparString(cat.nome)}', '${escaparString(cat.descricao || '')}')" title="Editar">
                        ✏️
                    </button>
                    <button class="btn-action btn-delete" onclick="removerCategoria(${cat.id})" title="Excluir">
                        🗑️
                    </button>
                </div>
            </div>
        `).join('');

    } catch (error) {
        console.error('Erro ao carregar categorias:', error);
        alert('Erro ao carregar categorias');
    }
}

// Função auxiliar para escapar aspas em strings
function escaparString(str) {
    return str.replace(/'/g, "\\'").replace(/"/g, '\\"');
}

// Adiciona nova categoria
document.getElementById('form-categoria').addEventListener('submit', async (e) => {
    e.preventDefault();

    const nome = document.getElementById('nome').value.trim();
    const descricao = document.getElementById('descricao').value.trim();

    // Validação no frontend
    if (!nome || nome.length === 0) {
        alert('Por favor, preencha o nome da categoria!');
        return;
    }

    if (!descricao || descricao.length === 0) {
        alert('Por favor, preencha a descrição da categoria!');
        return;
    }

    if (descricao.length > 500) {
        alert('A descrição deve ter no máximo 500 caracteres!');
        return;
    }

    console.log('Enviando:', { nome, descricao });

    // Enviar como URL encoded
    const params = new URLSearchParams();
    params.append('nome', nome);
    params.append('descricao', descricao);

    try {
        const response = await fetch(API_CATEGORIA, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        });

        if (response.ok) {
            alert('Categoria adicionada com sucesso!');
            e.target.reset();
            carregarCategorias();
        } else {
            const errorText = await response.text();
            console.error('Erro do servidor:', errorText);
            alert('Erro ao adicionar categoria: ' + errorText);
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao adicionar categoria: ' + error.message);
    }
});

// Remove categoria
async function removerCategoria(id) {
    if (!confirm('Tem certeza que deseja remover esta categoria? Todos os eventos desta categoria também serão removidos.')) {
        return;
    }

    try {
        const params = new URLSearchParams();
        params.append('acao', 'deletar');
        params.append('id', id);

        const response = await fetch(API_CATEGORIA_ACAO, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        });

        if (response.ok) {
            alert('Categoria removida com sucesso!');
            carregarCategorias();
        } else {
            alert('Erro ao remover categoria');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao remover categoria');
    }
}

// Abre modal de edição
function abrirModalEdicao(id, nome, descricao) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-nome').value = nome;
    document.getElementById('edit-descricao').value = descricao || '';

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

// Edita categoria
document.getElementById('form-edicao').addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('edit-id').value;
    const nome = document.getElementById('edit-nome').value;
    const descricao = document.getElementById('edit-descricao').value;

    // Validação no frontend
    if (!nome || nome.trim().length === 0) {
        alert('Por favor, preencha o nome da categoria!');
        return;
    }

    if (!descricao || descricao.trim().length === 0) {
        alert('Por favor, preencha a descrição da categoria!');
        return;
    }

    if (descricao.trim().length > 500) {
        alert('A descrição deve ter no máximo 500 caracteres!');
        return;
    }

    const params = new URLSearchParams();
    params.append('acao', 'alterar');
    params.append('id', id);
    params.append('nome', nome);
    params.append('descricao', descricao);

    try {
        const response = await fetch(API_CATEGORIA_ACAO, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        });

        if (response.ok) {
            alert('Categoria atualizada com sucesso!');
            fecharModal();
            carregarCategorias();
        } else {
            alert('Erro ao atualizar categoria');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao atualizar categoria');
    }
});

