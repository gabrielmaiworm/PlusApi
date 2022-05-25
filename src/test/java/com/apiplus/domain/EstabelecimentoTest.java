package com.apiplus.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apiplus.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstabelecimentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estabelecimento.class);
        Estabelecimento estabelecimento1 = new Estabelecimento();
        estabelecimento1.setId(1L);
        Estabelecimento estabelecimento2 = new Estabelecimento();
        estabelecimento2.setId(estabelecimento1.getId());
        assertThat(estabelecimento1).isEqualTo(estabelecimento2);
        estabelecimento2.setId(2L);
        assertThat(estabelecimento1).isNotEqualTo(estabelecimento2);
        estabelecimento1.setId(null);
        assertThat(estabelecimento1).isNotEqualTo(estabelecimento2);
    }
}
