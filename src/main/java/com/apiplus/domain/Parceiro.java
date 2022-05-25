package com.apiplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Parceiro.
 */
@Entity
@Table(name = "parceiro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Parceiro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "tipo_servico")
    private String tipoServico;

    @Column(name = "nome_gestor")
    private String nomeGestor;

    @Column(name = "sobrenome_gestor")
    private String sobrenomeGestor;

    @Column(name = "documento_gestor")
    private Integer documentoGestor;

    @Column(name = "telefone_gestor")
    private String telefoneGestor;

    @JsonIgnoreProperties(value = { "parceiro", "usuario" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    @JsonIgnoreProperties(value = { "parceiro" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ContaBancaria contaBancaria;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Parceiro id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public Parceiro razaoSocial(String razaoSocial) {
        this.setRazaoSocial(razaoSocial);
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    public Parceiro nomeFantasia(String nomeFantasia) {
        this.setNomeFantasia(nomeFantasia);
        return this;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public Parceiro cnpj(String cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return this.inscricaoEstadual;
    }

    public Parceiro inscricaoEstadual(String inscricaoEstadual) {
        this.setInscricaoEstadual(inscricaoEstadual);
        return this;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getEmail() {
        return this.email;
    }

    public Parceiro email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Parceiro telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoServico() {
        return this.tipoServico;
    }

    public Parceiro tipoServico(String tipoServico) {
        this.setTipoServico(tipoServico);
        return this;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getNomeGestor() {
        return this.nomeGestor;
    }

    public Parceiro nomeGestor(String nomeGestor) {
        this.setNomeGestor(nomeGestor);
        return this;
    }

    public void setNomeGestor(String nomeGestor) {
        this.nomeGestor = nomeGestor;
    }

    public String getSobrenomeGestor() {
        return this.sobrenomeGestor;
    }

    public Parceiro sobrenomeGestor(String sobrenomeGestor) {
        this.setSobrenomeGestor(sobrenomeGestor);
        return this;
    }

    public void setSobrenomeGestor(String sobrenomeGestor) {
        this.sobrenomeGestor = sobrenomeGestor;
    }

    public Integer getDocumentoGestor() {
        return this.documentoGestor;
    }

    public Parceiro documentoGestor(Integer documentoGestor) {
        this.setDocumentoGestor(documentoGestor);
        return this;
    }

    public void setDocumentoGestor(Integer documentoGestor) {
        this.documentoGestor = documentoGestor;
    }

    public String getTelefoneGestor() {
        return this.telefoneGestor;
    }

    public Parceiro telefoneGestor(String telefoneGestor) {
        this.setTelefoneGestor(telefoneGestor);
        return this;
    }

    public void setTelefoneGestor(String telefoneGestor) {
        this.telefoneGestor = telefoneGestor;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Parceiro endereco(Endereco endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public ContaBancaria getContaBancaria() {
        return this.contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public Parceiro contaBancaria(ContaBancaria contaBancaria) {
        this.setContaBancaria(contaBancaria);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Parceiro user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parceiro)) {
            return false;
        }
        return id != null && id.equals(((Parceiro) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parceiro{" +
            "id=" + getId() +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", nomeFantasia='" + getNomeFantasia() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", inscricaoEstadual='" + getInscricaoEstadual() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", tipoServico='" + getTipoServico() + "'" +
            ", nomeGestor='" + getNomeGestor() + "'" +
            ", sobrenomeGestor='" + getSobrenomeGestor() + "'" +
            ", documentoGestor=" + getDocumentoGestor() +
            ", telefoneGestor='" + getTelefoneGestor() + "'" +
            "}";
    }
}
