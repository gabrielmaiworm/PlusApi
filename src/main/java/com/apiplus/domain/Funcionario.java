package com.apiplus.domain;

import com.apiplus.domain.enumeration.NivelAcesso;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Funcionario.
 */
@Entity
@Table(name = "funcionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_acesso")
    private NivelAcesso nivelAcesso;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobrenome;

    @Column(name = "documento")
    private String documento;

    @Column(name = "telefone")
    private String telefone;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Funcionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NivelAcesso getNivelAcesso() {
        return this.nivelAcesso;
    }

    public Funcionario nivelAcesso(NivelAcesso nivelAcesso) {
        this.setNivelAcesso(nivelAcesso);
        return this;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getNome() {
        return this.nome;
    }

    public Funcionario nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public Funcionario sobrenome(String sobrenome) {
        this.setSobrenome(sobrenome);
        return this;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDocumento() {
        return this.documento;
    }

    public Funcionario documento(String documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Funcionario telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Funcionario user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funcionario)) {
            return false;
        }
        return id != null && id.equals(((Funcionario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Funcionario{" +
            "id=" + getId() +
            ", nivelAcesso='" + getNivelAcesso() + "'" +
            ", nome='" + getNome() + "'" +
            ", sobrenome='" + getSobrenome() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
