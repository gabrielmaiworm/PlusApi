package com.apiplus.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apiplus.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kit.class);
        Kit kit1 = new Kit();
        kit1.setId(1L);
        Kit kit2 = new Kit();
        kit2.setId(kit1.getId());
        assertThat(kit1).isEqualTo(kit2);
        kit2.setId(2L);
        assertThat(kit1).isNotEqualTo(kit2);
        kit1.setId(null);
        assertThat(kit1).isNotEqualTo(kit2);
    }
}
