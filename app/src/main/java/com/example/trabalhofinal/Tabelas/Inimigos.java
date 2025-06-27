package com.example.trabalhofinal.Tabelas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "inimigos")
public class Inimigos {

    @PrimaryKey(autoGenerate = true)
    public int idinimigos;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "apelido_nome_popular")
    public String apelidoNomePopular;


    @ColumnInfo(name = "armas")
    public String armas;

    @ColumnInfo(name = "hp")
    public int hp;


    @ColumnInfo(name = "forca")
    public int forca;


    @ColumnInfo(name = "estamina")
    public int estamina;

    @ColumnInfo(name = "agilidade")
    public int agilidade;

    @ColumnInfo(name = "defesa")
    public int defesa;

    @ColumnInfo(name = "intuicao")
    public int intuicao;

    @ColumnInfo(name = "energia")
    public int energia;

    @ColumnInfo(name = "akuma_no_mi")
    public String akumaNoMi;

    @ColumnInfo(name = "id_akuma")
    public int idakuma;

    @ColumnInfo(name = "associacao")
    public String associacao;

    @ColumnInfo(name = "tripulacao_organizacao")
    public String tripulacaoOrganizacao;

    @ColumnInfo(name = "recompensa")
    public String recompensa;

    @ColumnInfo(name = "estagio")
    public int estagio;

    @ColumnInfo(name = "tipo")
    public String tipo;

    @ColumnInfo(name = "titulo")
    public String titulo;

    @ColumnInfo(name = "origem")
    public String origem;

    @ColumnInfo(name = "sexo")
    public String sexo;

    @ColumnInfo(name = "raca")
    public String raca;

    @ColumnInfo(name = "hakiobs")
    public int hakiobs;

    @ColumnInfo(name = "hakiarm")
    public int hakiarm;

    @ColumnInfo(name = "hakirei")
    public int hakirei;

    @ColumnInfo(name = "fotoperfio")
    public String fotoperfio;

    @ColumnInfo(name = "fotocatalogo")
    public String fotocatalogo;

    @ColumnInfo(name = "fotocombate")
    public String fotocombate;

    @ColumnInfo(name = "vezesbatalha")
    public int vezesBatalha;


    public Inimigos(String nome, String apelidoNomePopular, String armas, int hp, int forca, int estamina, int agilidade, int defesa, int intuicao, int energia, String akumaNoMi, String associacao, String tripulacaoOrganizacao, String recompensa, int estagio, String tipo, String titulo, String origem, String sexo, String raca, int hakiobs, int hakiarm, String fotoperfio, String fotocatalogo, String fotocombate) {
        this.nome = nome;
        this.apelidoNomePopular = apelidoNomePopular;
        this.armas = armas;
        this.hp = hp;
        this.forca = forca;
        this.estamina = estamina;
        this.agilidade = agilidade;
        this.defesa = defesa;
        this.intuicao = intuicao;
        this.energia = energia;
        this.akumaNoMi = akumaNoMi;
        this.associacao = associacao;
        this.tripulacaoOrganizacao = tripulacaoOrganizacao;
        this.recompensa = recompensa;
        this.estagio = estagio;
        this.tipo = tipo;
        this.titulo = titulo;
        this.origem = origem;
        this.sexo = sexo;
        this.raca = raca;
        this.hakiobs = hakiobs;
        this.hakiarm = hakiarm;
        this.fotoperfio = fotoperfio;
        this.fotocatalogo = fotocatalogo;
        this.fotocombate = fotocombate;
    }

    public int getVezesBatalha() {
        return vezesBatalha;
    }

    public void setVezesBatalha(int vezesBatalha) {
        this.vezesBatalha = vezesBatalha;
    }

    public int getHakirei() {
        return hakirei;
    }

    public void setHakirei(int hakirei) {
        this.hakirei = hakirei;
    }

    public int getIdakuma() {
        return idakuma;
    }

    public void setIdakuma(int idakuma) {
        this.idakuma = idakuma;
    }

    public int getEstagio() {
        return estagio;
    }

    public void setEstagio(int estagio) {
        this.estagio = estagio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFotocatalogo() {
        return fotocatalogo;
    }

    public void setFotocatalogo(String fotometade) {
        this.fotocatalogo = fotocatalogo;
    }

    public String getFotocombate() {
        return fotocombate;
    }

    public void setFotocombate(String fotocombate) {
        this.fotocombate = fotocombate;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getHakiobs() {
        return hakiobs;
    }

    public void setHakiobs(int hakiobs) {
        this.hakiobs = hakiobs;
    }

    public int getHakiarm() {
        return hakiarm;
    }

    public void setHakiarm(int hakiarm) {
        this.hakiarm = hakiarm;
    }

    public int getIdinimigos() {
        return idinimigos;
    }

    public void setIdinimigos(int idinimigos) {
        this.idinimigos = idinimigos;
    }


    public String getApelidoNomePopular() {
        return apelidoNomePopular;
    }

    public void setApelidoNomePopular(String apelidoNomePopular) {
        this.apelidoNomePopular = apelidoNomePopular;
    }

    public String getArmas() {
        return armas;
    }

    public void setArmas(String armas) {
        this.armas = armas;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public int getEstamina() {
        return estamina;
    }

    public void setEstamina(int estamina) {
        this.estamina = estamina;
    }

    public int getAgilidade() {
        return agilidade;
    }

    public void setAgilidade(int agilidade) {
        this.agilidade = agilidade;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getIntuicao() {
        return intuicao;
    }

    public void setIntuicao(int intuicao) {
        this.intuicao = intuicao;
    }

    public String getAkumaNoMi() {
        return akumaNoMi;
    }

    public void setAkumaNoMi(String akumaNoMi) {
        this.akumaNoMi = akumaNoMi;
    }

    public String getAssociacao() {
        return associacao;
    }

    public void setAssociacao(String associacao) {
        this.associacao = associacao;
    }

    public String getTripulacaoOrganizacao() {
        return tripulacaoOrganizacao;
    }

    public void setTripulacaoOrganizacao(String tripulacaoOrganizacao) {
        this.tripulacaoOrganizacao = tripulacaoOrganizacao;
    }

    public String getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getFotoperfio() {
        return fotoperfio;
    }

    public void setFotoperfio(String fotoperfio) {
        this.fotoperfio = fotoperfio;
    }

    @Override
    public String toString() {
        return "Inimigos{" +
                "idinimigos=" + idinimigos +
                ", nome='" + nome + '\'' +
                ", apelidoNomePopular='" + apelidoNomePopular + '\'' +
                ", armas='" + armas + '\'' +
                ", hp=" + hp +
                ", forca=" + forca +
                ", estamina=" + estamina +
                ", agilidade=" + agilidade +
                ", defesa=" + defesa +
                ", intuicao=" + intuicao +
                ", energia=" + energia +
                ", akumaNoMi='" + akumaNoMi + '\'' +
                ", associacao='" + associacao + '\'' +
                ", tripulacaoOrganizacao='" + tripulacaoOrganizacao + '\'' +
                ", recompensa='" + recompensa + '\'' +
                ", estagio=" + estagio +
                ", tipo='" + tipo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", origem='" + origem + '\'' +
                ", sexo='" + sexo + '\'' +
                ", raca='" + raca + '\'' +
                ", hakiobs=" + hakiobs +
                ", hakiarm=" + hakiarm +
                ", fotoperfio='" + fotoperfio + '\'' +
                ", fotocatalogo='" + fotocatalogo + '\'' +
                ", fotocombate='" + fotocombate + '\'' +
                '}';
    }
}
