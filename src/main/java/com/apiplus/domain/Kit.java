package com.apiplus.domain;

import com.apiplus.domain.enumeration.StatusKit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Kit.
 */
@Entity
@Table(name = "kit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Kit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_kit")
    private StatusKit statusKit;

    @JsonIgnoreProperties(value = { "kit" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Bateria bateria;

    @JsonIgnoreProperties(value = { "kit" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Equipamento equipamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusKit getStatusKit() {
        return this.statusKit;
    }

    public Kit statusKit(StatusKit statusKit) {
        this.setStatusKit(statusKit);
        return this;
    }

    public void setStatusKit(StatusKit statusKit) {
        this.statusKit = statusKit;
    }

    public Bateria getBateria() {
        return this.bateria;
    }

    public void setBateria(Bateria bateria) {
        this.bateria = bateria;
    }

    public Kit bateria(Bateria bateria) {
        this.setBateria(bateria);
        return this;
    }

    public Equipamento getEquipamento() {
        return this.equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Kit equipamento(Equipamento equipamento) {
        this.setEquipamento(equipamento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kit)) {
            return false;
        }
        return id != null && id.equals(((Kit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kit{" +
            "id=" + getId() +
            ", statusKit='" + getStatusKit() + "'" +
            "}";
    }
}
