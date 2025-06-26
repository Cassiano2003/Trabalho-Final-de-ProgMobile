package com.example.trabalhofinal.Tabelas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.annotation.Native;

@Entity(tableName = "jogador")
public class Jogador {
    @PrimaryKey(autoGenerate = true)
    public int idpersonagens;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "nivel")
    public int nivel;

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
    public int akumaNoMi;

    @ColumnInfo(name = "associacao")
    public String associacao;

    @ColumnInfo(name = "tipo")
    public String tipo;

    @ColumnInfo(name = "recompensa")
    public String recompensa;

    @ColumnInfo(name = "titulo")
    public String titulo;

    @ColumnInfo(name = "origem")
    public String origem;

    @ColumnInfo(name = "sexo")
    public String sexo;

    @ColumnInfo(name = "raca")
    public String raca;

    @ColumnInfo(name = "pontos")
    public int pontos;

    @ColumnInfo(name = "vitorias5")
    public int vitorias5;
    @ColumnInfo(name = "vitorias10")
    public int vitorias10;
    @ColumnInfo(name = "vitorias25")
    public int vitorias25;

    @ColumnInfo(name = "hakirei")
    public int hakirei;
    @ColumnInfo(name = "hakiobs")
    public int hakiobs;
    @ColumnInfo(name = "hakiarm")
    public int hakiarm;
    @ColumnInfo(name = "idTripula")
    public int idTripula;

    @ColumnInfo(name = "imagenpersona")
    public String imagenPersona;

    @ColumnInfo(name = "desbloquio_ataques")
    public int qntataquesdesbloqueados;

    @ColumnInfo(name = "turnohakirei")
    public int turnohakirei;

    public Jogador(String nome, int nivel, String armas, int hp, int forca, int estamina, int agilidade, int defesa, int intuicao, int energia, int akumaNoMi, String associacao, String tipo, String titulo, String origem, String recompensa, String sexo, String raca, int hakirei, int hakiobs, int hakiarm, int qntataquesdesbloqueados) {
        this.nome = nome;
        this.nivel = nivel;
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
        this.tipo = tipo;
        this.titulo = titulo;
        this.origem = origem;
        this.recompensa = recompensa;
        this.sexo = sexo;
        this.raca = raca;
        this.hakirei = hakirei;
        this.hakiobs = hakiobs;
        this.hakiarm = hakiarm;
        this.qntataquesdesbloqueados = qntataquesdesbloqueados;
    }

    public int getTurnohakirei() {
        return turnohakirei;
    }

    public void setTurnohakirei(int turnohakirei) {
        this.turnohakirei = turnohakirei;
    }

    public String getImagenPersona() {
        return imagenPersona;
    }

    public int getQntataquesdesbloqueados() {
        return qntataquesdesbloqueados;
    }

    public void setQntataquesdesbloqueados(int qntataquesdesbloqueados) {
        this.qntataquesdesbloqueados = qntataquesdesbloqueados;
    }

    public void setImagenPersona(String imagenPersona) {
        this.imagenPersona = imagenPersona;
    }

    public int getIdTripula() {
        return idTripula;
    }

    public void setIdTripula(int idTripula) {
        this.idTripula = idTripula;
    }

    public int getVitorias5() {
        return vitorias5;
    }

    public void setVitorias5(int vitorias5) {
        this.vitorias5 = vitorias5;
    }

    public int getVitorias10() {
        return vitorias10;
    }

    public void setVitorias10(int vitorias10) {
        this.vitorias10 = vitorias10;
    }

    public int getVitorias25() {
        return vitorias25;
    }

    public void setVitorias25(int vitorias25) {
        this.vitorias25 = vitorias25;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getHakirei() {
        return hakirei;
    }

    public void setHakirei(int hakirei) {
        this.hakirei = hakirei;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getIdpersonagens() {
        return idpersonagens;
    }

    public void setIdpersonagens(int idpersonagens) {
        this.idpersonagens = idpersonagens;
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

    public int getAkumaNoMi() {
        return akumaNoMi;
    }

    public void setAkumaNoMi(int akumaNoMi) {
        this.akumaNoMi = akumaNoMi;
    }

    public String getAssociacao() {
        return associacao;
    }

    public void setAssociacao(String associacao) {
        this.associacao = associacao;
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

    @Override
    public String toString() {
        return nome+" : "+"LV "+nivel;
    }
}
