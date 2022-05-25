package com.apiplus.domain;

import com.apiplus.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bateria.
 */
@Entity
@Table(name = "bateria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bateria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_serie")
    private Integer numeroSerie;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "carga")
    private Integer carga;

    @JsonIgnoreProperties(value = { "bateria", "equipamento" }, allowSetters = true)
    @OneToOne(mappedBy = "bateria")
    private Kit kit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bateria id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroSerie() {
        return this.numeroSerie;
    }

    public Bateria numeroSerie(Integer numeroSerie) {
        this.setNumeroSerie(numeroSerie);
        return this;
    }

    public void setNumeroSerie(Integer numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Status getStatus() {
        return this.status;
    }

    public Bateria status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getCarga() {
        return this.carga;
    }

    public Bateria carga(Integer carga) {
        this.setCarga(carga);
        return this;
    }

    public void setCarga(Integer carga) {
        this.carga = carga;
    }

    public Kit getKit() {
        return this.kit;
    }

    public void setKit(Kit kit) {
        if (this.kit != null) {
            this.kit.setBateria(null);
        }
        if (kit != null) {
            kit.setBateria(this);
        }
        this.kit = kit;
    }

    public Bateria kit(Kit kit) {
        this.setKit(kit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bateria)) {
            return false;
        }
        return id != null && id.equals(((Bateria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bateria{" +
            "id=" + getId() +
            ", numeroSerie=" + getNumeroSerie() +
            ", status='" + getStatus() + "'" +
            ", carga=" + getCarga() +
            "}";
    }
}
