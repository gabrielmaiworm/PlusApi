package com.apiplus.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apiplus.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EquipamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipamento.class);
        Equipamento equipamento1 = new Equipamento();
        equipamento1.setId(1L);
        Equipamento equipamento2 = new Equipamento();
        equipamento2.setId(equipamento1.getId());
        assertThat(equipamento1).isEqualTo(equipamento2);
        equipamento2.setId(2L);
        assertThat(equipamento1).isNotEqualTo(equipamento2);
        equipamento1.setId(null);
        assertThat(equipamento1).isNotEqualTo(equipamento2);
    }
}
