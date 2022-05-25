package com.apiplus.domain;

import com.apiplus.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Equipamento.
 */
@Entity
@Table(name = "equipamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Equipamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_serie")
    private Integer numeroSerie;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Lob
    @Column(name = "foto_equipamento")
    private byte[] fotoEquipamento;

    @Column(name = "foto_equipamento_content_type")
    private String fotoEquipamentoContentType;

    @JsonIgnoreProperties(value = { "bateria", "equipamento" }, allowSetters = true)
    @OneToOne(mappedBy = "equipamento")
    private Kit kit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Equipamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroSerie() {
        return this.numeroSerie;
    }

    public Equipamento numeroSerie(Integer numeroSerie) {
        this.setNumeroSerie(numeroSerie);
        return this;
    }

    public void setNumeroSerie(Integer numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNome() {
        return this.nome;
    }

    public Equipamento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Status getStatus() {
        return this.status;
    }

    public Equipamento status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public byte[] getFotoEquipamento() {
        return this.fotoEquipamento;
    }

    public Equipamento fotoEquipamento(byte[] fotoEquipamento) {
        this.setFotoEquipamento(fotoEquipamento);
        return this;
    }

    public void setFotoEquipamento(byte[] fotoEquipamento) {
        this.fotoEquipamento = fotoEquipamento;
    }

    public String getFotoEquipamentoContentType() {
        return this.fotoEquipamentoContentType;
    }

    public Equipamento fotoEquipamentoContentType(String fotoEquipamentoContentType) {
        this.fotoEquipamentoContentType = fotoEquipamentoContentType;
        return this;
    }

    public void setFotoEquipamentoContentType(String fotoEquipamentoContentType) {
        this.fotoEquipamentoContentType = fotoEquipamentoContentType;
    }

    public Kit getKit() {
        return this.kit;
    }

    public void setKit(Kit kit) {
        if (this.kit != null) {
            this.kit.setEquipamento(null);
        }
        if (kit != null) {
            kit.setEquipamento(this);
        }
        this.kit = kit;
    }

    public Equipamento kit(Kit kit) {
        this.setKit(kit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipamento)) {
            return false;
        }
        return id != null && id.equals(((Equipamento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipamento{" +
            "id=" + getId() +
            ", numeroSerie=" + getNumeroSerie() +
            ", nome='" + getNome() + "'" +
            ", status='" + getStatus() + "'" +
            ", fotoEquipamento='" + getFotoEquipamento() + "'" +
            ", fotoEquipamentoContentType='" + getFotoEquipamentoContentType() + "'" +
            "}";
    }
}
