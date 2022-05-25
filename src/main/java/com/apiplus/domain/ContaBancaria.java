package com.apiplus.domain;

import com.apiplus.domain.enumeration.Tipo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContaBancaria.
 */
@Entity
@Table(name = "conta_bancaria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "banco")
    private String banco;

    @Column(name = "agencia")
    private Integer agencia;

    @Column(name = "numero_conta")
    private Integer numeroConta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private Tipo tipo;

    @JsonIgnoreProperties(value = { "endereco", "contaBancaria", "user" }, allowSetters = true)
    @OneToOne(mappedBy = "contaBancaria")
    private Parceiro parceiro;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContaBancaria id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBanco() {
        return this.banco;
    }

    public ContaBancaria banco(String banco) {
        this.setBanco(banco);
        return this;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Integer getAgencia() {
        return this.agencia;
    }

    public ContaBancaria agencia(Integer agencia) {
        this.setAgencia(agencia);
        return this;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public Integer getNumeroConta() {
        return this.numeroConta;
    }

    public ContaBancaria numeroConta(Integer numeroConta) {
        this.setNumeroConta(numeroConta);
        return this;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public ContaBancaria tipo(Tipo tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Parceiro getParceiro() {
        return this.parceiro;
    }

    public void setParceiro(Parceiro parceiro) {
        if (this.parceiro != null) {
            this.parceiro.setContaBancaria(null);
        }
        if (parceiro != null) {
            parceiro.setContaBancaria(this);
        }
        this.parceiro = parceiro;
    }

    public ContaBancaria parceiro(Parceiro parceiro) {
        this.setParceiro(parceiro);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContaBancaria)) {
            return false;
        }
        return id != null && id.equals(((ContaBancaria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContaBancaria{" +
            "id=" + getId() +
            ", banco='" + getBanco() + "'" +
            ", agencia=" + getAgencia() +
            ", numeroConta=" + getNumeroConta() +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
