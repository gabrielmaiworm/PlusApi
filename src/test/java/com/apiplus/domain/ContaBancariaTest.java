package com.apiplus.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apiplus.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContaBancariaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContaBancaria.class);
        ContaBancaria contaBancaria1 = new ContaBancaria();
        contaBancaria1.setId(1L);
        ContaBancaria contaBancaria2 = new ContaBancaria();
        contaBancaria2.setId(contaBancaria1.getId());
        assertThat(contaBancaria1).isEqualTo(contaBancaria2);
        contaBancaria2.setId(2L);
        assertThat(contaBancaria1).isNotEqualTo(contaBancaria2);
        contaBancaria1.setId(null);
        assertThat(contaBancaria1).isNotEqualTo(contaBancaria2);
    }
}
