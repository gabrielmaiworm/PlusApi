package com.apiplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Estabelecimento.
 */
@Entity
@Table(name = "estabelecimento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Estabelecimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "nota_media")
    private String notaMedia;

    @Column(name = "localizacao")
    private String localizacao;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @Column(name = "preco_filter")
    private Integer precoFilter;

    @Column(name = "place_id")
    private String placeId;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @OneToMany(mappedBy = "estabelecimento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estabelecimento", "infoAdicional" }, allowSetters = true)
    private Set<Avaliacao> avaliacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Estabelecimento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public Estabelecimento endereco(String endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return this.nome;
    }

    public Estabelecimento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Estabelecimento telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public Estabelecimento categoria(String categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNotaMedia() {
        return this.notaMedia;
    }

    public Estabelecimento notaMedia(String notaMedia) {
        this.setNotaMedia(notaMedia);
        return this;
    }

    public void setNotaMedia(String notaMedia) {
        this.notaMedia = notaMedia;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public Estabelecimento localizacao(String localizacao) {
        this.setLocalizacao(localizacao);
        return this;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public byte[] getImagem() {
        return this.imagem;
    }

    public Estabelecimento imagem(byte[] imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return this.imagemContentType;
    }

    public Estabelecimento imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public Integer getPrecoFilter() {
        return this.precoFilter;
    }

    public Estabelecimento precoFilter(Integer precoFilter) {
        this.setPrecoFilter(precoFilter);
        return this;
    }

    public void setPrecoFilter(Integer precoFilter) {
        this.precoFilter = precoFilter;
    }

    public String getPlaceId() {
        return this.placeId;
    }

    public Estabelecimento placeId(String placeId) {
        this.setPlaceId(placeId);
        return this;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    public Estabelecimento latitude(Float latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public Estabelecimento longitude(Float longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Set<Avaliacao> getAvaliacaos() {
        return this.avaliacaos;
    }

    public void setAvaliacaos(Set<Avaliacao> avaliacaos) {
        if (this.avaliacaos != null) {
            this.avaliacaos.forEach(i -> i.setEstabelecimento(null));
        }
        if (avaliacaos != null) {
            avaliacaos.forEach(i -> i.setEstabelecimento(this));
        }
        this.avaliacaos = avaliacaos;
    }

    public Estabelecimento avaliacaos(Set<Avaliacao> avaliacaos) {
        this.setAvaliacaos(avaliacaos);
        return this;
    }

    public Estabelecimento addAvaliacao(Avaliacao avaliacao) {
        this.avaliacaos.add(avaliacao);
        avaliacao.setEstabelecimento(this);
        return this;
    }

    public Estabelecimento removeAvaliacao(Avaliacao avaliacao) {
        this.avaliacaos.remove(avaliacao);
        avaliacao.setEstabelecimento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estabelecimento)) {
            return false;
        }
        return id != null && id.equals(((Estabelecimento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estabelecimento{" +
            "id=" + getId() +
            ", endereco='" + getEndereco() + "'" +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", notaMedia='" + getNotaMedia() + "'" +
            ", localizacao='" + getLocalizacao() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", precoFilter=" + getPrecoFilter() +
            ", placeId='" + getPlaceId() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
