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
            "}";
    }
}
