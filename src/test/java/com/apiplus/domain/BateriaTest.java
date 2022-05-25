package com.apiplus.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apiplus.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BateriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bateria.class);
        Bateria bateria1 = new Bateria();
        bateria1.setId(1L);
        Bateria bateria2 = new Bateria();
        bateria2.setId(bateria1.getId());
        assertThat(bateria1).isEqualTo(bateria2);
        bateria2.setId(2L);
        assertThat(bateria1).isNotEqualTo(bateria2);
        bateria1.setId(null);
        assertThat(bateria1).isNotEqualTo(bateria2);
    }
}
