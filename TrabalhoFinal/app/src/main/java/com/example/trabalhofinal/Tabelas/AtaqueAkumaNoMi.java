package com.example.trabalhofinal.Tabelas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ataques_akuma_no_mi")
public class AtaqueAkumaNoMi {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "akuma_no_mi_relacionada")
    public String akumaNoMiRelacionada;

    @ColumnInfo(name = "nome_do_ataque")
    public String nomeDoAtaque;

    @ColumnInfo(name = "nivel_desbloqueado")
    public int nivelDesbloqueado;

    @ColumnInfo(name = "descricao")
    public String descricao;

    @ColumnInfo(name = "tipo_de_ataque")
    public String tipoDeAtaque;

    @ColumnInfo(name = "custo")
    public int custo;

    public AtaqueAkumaNoMi(String akumaNoMiRelacionada, String nomeDoAtaque, int nivelDesbloqueado, String descricao, String tipoDeAtaque, int custo) {
        this.akumaNoMiRelacionada = akumaNoMiRelacionada;
        this.nomeDoAtaque = nomeDoAtaque;
        this.nivelDesbloqueado = nivelDesbloqueado;
        this.descricao = descricao;
        this.tipoDeAtaque = tipoDeAtaque;
        this.custo = custo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAkumaNoMiRelacionada() {
        return akumaNoMiRelacionada;
    }

    public void setAkumaNoMiRelacionada(String akumaNoMiRelacionada) {
        this.akumaNoMiRelacionada = akumaNoMiRelacionada;
    }

    public String getNomeDoAtaque() {
        return nomeDoAtaque;
    }

    public void setNomeDoAtaque(String nomeDoAtaque) {
        this.nomeDoAtaque = nomeDoAtaque;
    }

    public int getNivelDesbloqueado() {
        return nivelDesbloqueado;
    }

    public void setNivelDesbloqueado(int nivelDesbloqueado) {
        this.nivelDesbloqueado = nivelDesbloqueado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoDeAtaque() {
        return tipoDeAtaque;
    }

    public void setTipoDeAtaque(String tipoDeAtaque) {
        this.tipoDeAtaque = tipoDeAtaque;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    @Override
    public String toString() {
        return "AtaqueAkumaNoMi{" +
                "id=" + id +
                ", akumaNoMiRelacionada='" + akumaNoMiRelacionada + '\'' +
                ", nomeDoAtaque='" + nomeDoAtaque + '\'' +
                ", nivelDesbloqueado=" + nivelDesbloqueado +
                ", descricao='" + descricao + '\'' +
                ", tipoDeAtaque='" + tipoDeAtaque + '\'' +
                ", custo=" + custo +
                '}';
    }
}
