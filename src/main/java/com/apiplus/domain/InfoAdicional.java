package com.apiplus.domain;

import com.apiplus.domain.enumeration.Deficiencia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InfoAdicional.
 */
@Entity
@Table(name = "info_adicional")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InfoAdicional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "telefone")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "deficiencia")
    private Deficiencia deficiencia;

    @OneToMany(mappedBy = "infoAdicional")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "infoAdicional" }, allowSetters = true)
    private Set<Avaliacao> avaliacaos = new HashSet<>();

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InfoAdicional id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public InfoAdicional nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public InfoAdicional cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public InfoAdicional telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Deficiencia getDeficiencia() {
        return this.deficiencia;
    }

    public InfoAdicional deficiencia(Deficiencia deficiencia) {
        this.setDeficiencia(deficiencia);
        return this;
    }

    public void setDeficiencia(Deficiencia deficiencia) {
        this.deficiencia = deficiencia;
    }

    public Set<Avaliacao> getAvaliacaos() {
        return this.avaliacaos;
    }

    public void setAvaliacaos(Set<Avaliacao> avaliacaos) {
        if (this.avaliacaos != null) {
            this.avaliacaos.forEach(i -> i.setInfoAdicional(null));
        }
        if (avaliacaos != null) {
            avaliacaos.forEach(i -> i.setInfoAdicional(this));
        }
        this.avaliacaos = avaliacaos;
    }

    public InfoAdicional avaliacaos(Set<Avaliacao> avaliacaos) {
        this.setAvaliacaos(avaliacaos);
        return this;
    }

    public InfoAdicional addAvaliacao(Avaliacao avaliacao) {
        this.avaliacaos.add(avaliacao);
        avaliacao.setInfoAdicional(this);
        return this;
    }

    public InfoAdicional removeAvaliacao(Avaliacao avaliacao) {
        this.avaliacaos.remove(avaliacao);
        avaliacao.setInfoAdicional(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public InfoAdicional user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoAdicional)) {
            return false;
        }
        return id != null && id.equals(((InfoAdicional) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoAdicional{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", deficiencia='" + getDeficiencia() + "'" +
            "}";
    }
}
