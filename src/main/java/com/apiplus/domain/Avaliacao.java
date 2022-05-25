package com.apiplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Avaliacao.
 */
@Entity
@Table(name = "avaliacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "nota")
    private Double nota;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @Column(name = "acesso_entrada_principal")
    private Boolean acessoEntradaPrincipal;

    @Column(name = "acesso_banheiro")
    private Boolean acessoBanheiro;

    @Column(name = "acesso_audio_visual")
    private Boolean acessoAudioVisual;

    @Column(name = "acesso_circulacao_interna")
    private Boolean acessoCirculacaoInterna;

    @Column(name = "acesso_estacionamento")
    private Boolean acessoEstacionamento;

    @Column(name = "acesso_calcada")
    private Boolean acessoCalcada;

    @ManyToOne
    @JsonIgnoreProperties(value = { "avaliacaos", "user" }, allowSetters = true)
    private InfoAdicional infoAdicional;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Avaliacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return this.comentario;
    }

    public Avaliacao comentario(String comentario) {
        this.setComentario(comentario);
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getNota() {
        return this.nota;
    }

    public Avaliacao nota(Double nota) {
        this.setNota(nota);
        return this;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public byte[] getImagem() {
        return this.imagem;
    }

    public Avaliacao imagem(byte[] imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return this.imagemContentType;
    }

    public Avaliacao imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public Boolean getAcessoEntradaPrincipal() {
        return this.acessoEntradaPrincipal;
    }

    public Avaliacao acessoEntradaPrincipal(Boolean acessoEntradaPrincipal) {
        this.setAcessoEntradaPrincipal(acessoEntradaPrincipal);
        return this;
    }

    public void setAcessoEntradaPrincipal(Boolean acessoEntradaPrincipal) {
        this.acessoEntradaPrincipal = acessoEntradaPrincipal;
    }

    public Boolean getAcessoBanheiro() {
        return this.acessoBanheiro;
    }

    public Avaliacao acessoBanheiro(Boolean acessoBanheiro) {
        this.setAcessoBanheiro(acessoBanheiro);
        return this;
    }

    public void setAcessoBanheiro(Boolean acessoBanheiro) {
        this.acessoBanheiro = acessoBanheiro;
    }

    public Boolean getAcessoAudioVisual() {
        return this.acessoAudioVisual;
    }

    public Avaliacao acessoAudioVisual(Boolean acessoAudioVisual) {
        this.setAcessoAudioVisual(acessoAudioVisual);
        return this;
    }

    public void setAcessoAudioVisual(Boolean acessoAudioVisual) {
        this.acessoAudioVisual = acessoAudioVisual;
    }

    public Boolean getAcessoCirculacaoInterna() {
        return this.acessoCirculacaoInterna;
    }

    public Avaliacao acessoCirculacaoInterna(Boolean acessoCirculacaoInterna) {
        this.setAcessoCirculacaoInterna(acessoCirculacaoInterna);
        return this;
    }

    public void setAcessoCirculacaoInterna(Boolean acessoCirculacaoInterna) {
        this.acessoCirculacaoInterna = acessoCirculacaoInterna;
    }

    public Boolean getAcessoEstacionamento() {
        return this.acessoEstacionamento;
    }

    public Avaliacao acessoEstacionamento(Boolean acessoEstacionamento) {
        this.setAcessoEstacionamento(acessoEstacionamento);
        return this;
    }

    public void setAcessoEstacionamento(Boolean acessoEstacionamento) {
        this.acessoEstacionamento = acessoEstacionamento;
    }

    public Boolean getAcessoCalcada() {
        return this.acessoCalcada;
    }

    public Avaliacao acessoCalcada(Boolean acessoCalcada) {
        this.setAcessoCalcada(acessoCalcada);
        return this;
    }

    public void setAcessoCalcada(Boolean acessoCalcada) {
        this.acessoCalcada = acessoCalcada;
    }

    public InfoAdicional getInfoAdicional() {
        return this.infoAdicional;
    }

    public void setInfoAdicional(InfoAdicional infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public Avaliacao infoAdicional(InfoAdicional infoAdicional) {
        this.setInfoAdicional(infoAdicional);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avaliacao)) {
            return false;
        }
        return id != null && id.equals(((Avaliacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avaliacao{" +
            "id=" + getId() +
            ", comentario='" + getComentario() + "'" +
            ", nota=" + getNota() +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", acessoEntradaPrincipal='" + getAcessoEntradaPrincipal() + "'" +
            ", acessoBanheiro='" + getAcessoBanheiro() + "'" +
            ", acessoAudioVisual='" + getAcessoAudioVisual() + "'" +
            ", acessoCirculacaoInterna='" + getAcessoCirculacaoInterna() + "'" +
            ", acessoEstacionamento='" + getAcessoEstacionamento() + "'" +
            ", acessoCalcada='" + getAcessoCalcada() + "'" +
            "}";
    }
}
