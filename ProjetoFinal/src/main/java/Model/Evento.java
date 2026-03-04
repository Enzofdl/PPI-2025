package Model;

import java.util.Date;

public class Evento {
    private int id;
    private String nome;
    private Date data;
    private String local;
    private Categoria categoria;
    private String imagem; // NOVO CAMPO

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    // GETTER E SETTER DA IMAGEM
    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
}