package com.example.trabalhofinal.Tabelas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "ataquesakuma")
public class AtaqueAkumaNoMi {

    @PrimaryKey(autoGenerate = true)
    public int idataque;

    @ColumnInfo(name = "quantos_ataques")
    public int qntataques;

    @ColumnInfo(name = "nome_ataque")
    public String[] nomeDoAtaque;

    @ColumnInfo(name = "descricao")
    public String[] descricao;

    @ColumnInfo(name = "tipo_ataque")
    public String[] tipoDeAtaque;

    @ColumnInfo(name = "custo")
    public int[] custo;

    @ColumnInfo(name = "hp_jogador")
    public int[] hpjogador;

    @ColumnInfo(name = "forca_jogador")
    public int[] forcajogador;

    @ColumnInfo(name = "estamina_jogador")
    public int[] estaminajogador;

    @ColumnInfo(name = "agilidade_jogador")
    public int[] agilidadejogador;

    @ColumnInfo(name = "defesa_jogador")
    public int[] defesajogador;

    @ColumnInfo(name = "intuicao_jogador")
    public int[] intuicaojogador;

    @ColumnInfo(name = "dano_jogador")
    public int[] danojogador;

    @ColumnInfo(name = "hp_inimigo")
    public int[] hpinimigo;

    @ColumnInfo(name = "forca_inimigo")
    public int[] forcainimigo;

    @ColumnInfo(name = "estamina_inimigo")
    public int[] estaminainimigo;

    @ColumnInfo(name = "agilidade_inimigo")
    public int[] agilidadeinimigo;

    @ColumnInfo(name = "defesa_inimigo")
    public int[] defesainimigo;

    @ColumnInfo(name = "intuicao_inimigo")
    public int[] intuicaoinimigo;

    @ColumnInfo(name = "dano_inimigo")
    public int[] danoinimigo;

    public AtaqueAkumaNoMi(int qntataques, String[] nomeDoAtaque, String[] descricao, String[] tipoDeAtaque, int[] custo, int[] hpjogador, int[] forcajogador, int[] estaminajogador, int[] agilidadejogador, int[] defesajogador, int[] intuicaojogador, int[] danojogador, int[] hpinimigo, int[] forcainimigo, int[] estaminainimigo, int[] agilidadeinimigo, int[] defesainimigo, int[] intuicaoinimigo, int[] danoinimigo) {
        this.qntataques = qntataques;
        this.nomeDoAtaque = nomeDoAtaque;
        this.descricao = descricao;
        this.tipoDeAtaque = tipoDeAtaque;
        this.custo = custo;
        this.hpjogador = hpjogador;
        this.forcajogador = forcajogador;
        this.estaminajogador = estaminajogador;
        this.agilidadejogador = agilidadejogador;
        this.defesajogador = defesajogador;
        this.intuicaojogador = intuicaojogador;
        this.danojogador = danojogador;
        this.hpinimigo = hpinimigo;
        this.forcainimigo = forcainimigo;
        this.estaminainimigo = estaminainimigo;
        this.agilidadeinimigo = agilidadeinimigo;
        this.defesainimigo = defesainimigo;
        this.intuicaoinimigo = intuicaoinimigo;
        this.danoinimigo = danoinimigo;
    }

    public int getIdataque() {
        return idataque;
    }

    public void setIdataque(int idataque) {
        this.idataque = idataque;
    }

    public int getQntataques() {
        return qntataques;
    }

    public void setQntataques(int qntataques) {
        this.qntataques = qntataques;
    }

    public String[] getNomeDoAtaque() {
        return nomeDoAtaque;
    }

    public void setNomeDoAtaque(String[] nomeDoAtaque) {
        this.nomeDoAtaque = nomeDoAtaque;
    }

    public String[] getDescricao() {
        return descricao;
    }

    public void setDescricao(String[] descricao) {
        this.descricao = descricao;
    }

    public String[] getTipoDeAtaque() {
        return tipoDeAtaque;
    }

    public void setTipoDeAtaque(String[] tipoDeAtaque) {
        this.tipoDeAtaque = tipoDeAtaque;
    }

    public int[] getCusto() {
        return custo;
    }

    public void setCusto(int[] custo) {
        this.custo = custo;
    }

    public int[] getHpjogador() {
        return hpjogador;
    }

    public void setHpjogador(int[] hpjogador) {
        this.hpjogador = hpjogador;
    }

    public int[] getForcajogador() {
        return forcajogador;
    }

    public void setForcajogador(int[] forcajogador) {
        this.forcajogador = forcajogador;
    }

    public int[] getEstaminajogador() {
        return estaminajogador;
    }

    public void setEstaminajogador(int[] estaminajogador) {
        this.estaminajogador = estaminajogador;
    }

    public int[] getAgilidadejogador() {
        return agilidadejogador;
    }

    public void setAgilidadejogador(int[] agilidadejogador) {
        this.agilidadejogador = agilidadejogador;
    }

    public int[] getDefesajogador() {
        return defesajogador;
    }

    public void setDefesajogador(int[] defesajogador) {
        this.defesajogador = defesajogador;
    }

    public int[] getIntuicaojogador() {
        return intuicaojogador;
    }

    public void setIntuicaojogador(int[] intuicaojogador) {
        this.intuicaojogador = intuicaojogador;
    }

    public int[] getDanojogador() {
        return danojogador;
    }

    public void setDanojogador(int[] danojogador) {
        this.danojogador = danojogador;
    }

    public int[] getHpinimigo() {
        return hpinimigo;
    }

    public void setHpinimigo(int[] hpinimigo) {
        this.hpinimigo = hpinimigo;
    }

    public int[] getForcainimigo() {
        return forcainimigo;
    }

    public void setForcainimigo(int[] forcainimigo) {
        this.forcainimigo = forcainimigo;
    }

    public int[] getEstaminainimigo() {
        return estaminainimigo;
    }

    public void setEstaminainimigo(int[] estaminainimigo) {
        this.estaminainimigo = estaminainimigo;
    }

    public int[] getAgilidadeinimigo() {
        return agilidadeinimigo;
    }

    public void setAgilidadeinimigo(int[] agilidadeinimigo) {
        this.agilidadeinimigo = agilidadeinimigo;
    }

    public int[] getDefesainimigo() {
        return defesainimigo;
    }

    public void setDefesainimigo(int[] defesainimigo) {
        this.defesainimigo = defesainimigo;
    }

    public int[] getIntuicaoinimigo() {
        return intuicaoinimigo;
    }

    public void setIntuicaoinimigo(int[] intuicaoinimigo) {
        this.intuicaoinimigo = intuicaoinimigo;
    }

    public int[] getDanoinimigo() {
        return danoinimigo;
    }

    public void setDanoinimigo(int[] danoinimigo) {
        this.danoinimigo = danoinimigo;
    }

    @Override
    public String toString() {
        return "AtaqueAkumaNoMi{" +
                "idataque=" + idataque +
                ", qntataques=" + qntataques +
                ", nomeDoAtaque=" + Arrays.toString(nomeDoAtaque) +
                ", descricao=" + Arrays.toString(descricao) +
                ", tipoDeAtaque=" + Arrays.toString(tipoDeAtaque) +
                ", custo=" + Arrays.toString(custo) +
                '}';
    }
}
