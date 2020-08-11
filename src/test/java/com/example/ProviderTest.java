package com.example;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProviderTest {

    @Test
    public void provider_has_an_address() {
        Provider a = new Provider("a");
        assertThat(a.getAddress().toString()).isEqualTo("a");
    }
}
