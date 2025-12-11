package Model;

import java.sql.Date;

public class Evento {
    private int id;
    private String nome;
    private String local;
    private Date data;
    private int idCategoria;

    public Evento(String nome, String local, Date data, int idCategoria) {
        this.nome = nome;
        this.local = local;
        this.data = data;
        this.idCategoria = idCategoria;
    }

    public Evento() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
