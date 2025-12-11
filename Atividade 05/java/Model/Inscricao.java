package Model;

import java.sql.Date;

public class Inscricao {
    private int id;
    private int idUsuario;
    private int idEvento;
    private Date dataInscricao;

    public Inscricao(int idUsuario, int idEvento, Date dataInscricao) {
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.dataInscricao = dataInscricao;
    }

    public Inscricao() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }
}
