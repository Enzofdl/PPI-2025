// URLs das APIs
const API_USUARIO = 'api/usuarios';
const API_USUARIO_ACAO = 'api/usuarios/acao';

// Carrega dados ao iniciar
document.addEventListener('DOMContentLoaded', () => {
    carregarUsuarios();
});

// Carrega lista de usuários
async function carregarUsuarios() {
    try {
        const response = await fetch(API_USUARIO);
        const usuarios = await response.json();
        
        const lista = document.getElementById('usuario-list');
        const count = document.getElementById('usuario-count');
        
        count.textContent = `${usuarios.length} usuário${usuarios.length !== 1 ? 's' : ''}`;
        
        if (usuarios.length === 0) {
            lista.innerHTML = '<div class="user-item"><p>Nenhum usuário cadastrado.</p></div>';
            return;
        }
        
        lista.innerHTML = usuarios.map(user => `
            <div class="user-item">
                <div class="user-info">
                    <h3>${user.nome}</h3>
                    <p><strong>E-mail:</strong> ${user.email}</p>
                    <p><strong>Telefone:</strong> ${user.telefone}</p>
                </div>
                <div class="user-actions">
                    <button class="btn-action btn-edit" onclick="abrirModalEdicao(${user.id}, '${escaparString(user.nome)}', '${escaparString(user.email)}', '${escaparString(user.telefone)}')" title="Editar">
                        ✏️
                    </button>
                    <button class="btn-action btn-delete" onclick="removerUsuario(${user.id})" title="Excluir">
                        🗑️
                    </button>
                </div>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Erro ao carregar usuários:', error);
        alert('Erro ao carregar usuários');
    }
}

// Função auxiliar para escapar aspas em strings
function escaparString(str) {
    return str.replace(/'/g, "\\'").replace(/"/g, '\\"');
}

// Adiciona novo usuário
document.getElementById('form-usuario').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const telefone = document.getElementById('telefone').value;
    
    // Validação no frontend
    if (!nome || !email || !telefone) {
        alert('Por favor, preencha todos os campos!');
        return;
    }
    
    console.log('Enviando:', { nome, email, telefone });
    
    // Enviar como URL encoded
    const params = new URLSearchParams();
    params.append('nome', nome);
    params.append('email', email);
    params.append('telefone', telefone);
    
    try {
        const response = await fetch(API_USUARIO, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        });
        
        if (response.ok) {
            alert('Usuário adicionado com sucesso!');
            e.target.reset();
            carregarUsuarios();
        } else {
            const errorText = await response.text();
            console.error('Erro do servidor:', errorText);
            alert('Erro ao adicionar usuário: ' + errorText);
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao adicionar usuário: ' + error.message);
    }
});

// Remove usuário
async function removerUsuario(id) {
    if (!confirm('Tem certeza que deseja remover este usuário?')) {
        return;
    }
    
    try {
        const params = new URLSearchParams();
        params.append('acao', 'deletar');
        params.append('id', id);
        
        const response = await fetch(API_USUARIO_ACAO, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        });
        
        if (response.ok) {
            alert('Usuário removido com sucesso!');
            carregarUsuarios();
        } else {
            alert('Erro ao remover usuário');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao remover usuário');
    }
}

// Abre modal de edição
function abrirModalEdicao(id, nome, email, telefone) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-nome').value = nome;
    document.getElementById('edit-email').value = email;
    document.getElementById('edit-telefone').value = telefone;
    
    document.getElementById('modal-edicao').classList.remove('hidden');
}

// Fecha modal
function fecharModal() {
    document.getElementById('modal-edicao').classList.add('hidden');
    document.getElementById('form-edicao').reset();
}

// Fecha modal ao clicar fora
window.onclick = function(event) {
    const modal = document.getElementById('modal-edicao');
    if (event.target === modal) {
        fecharModal();
    }
}

// Edita usuário
document.getElementById('form-edicao').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const id = document.getElementById('edit-id').value;
    const nome = document.getElementById('edit-nome').value;
    const email = document.getElementById('edit-email').value;
    const telefone = document.getElementById('edit-telefone').value;
    
    const params = new URLSearchParams();
    params.append('acao', 'alterar');
    params.append('id', id);
    params.append('nome', nome);
    params.append('email', email);
    params.append('telefone', telefone);
    
    try {
        const response = await fetch(API_USUARIO_ACAO, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        });
        
        if (response.ok) {
            alert('Usuário atualizado com sucesso!');
            fecharModal();
            carregarUsuarios();
        } else {
            alert('Erro ao atualizar usuário');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao atualizar usuário');
    }
});

