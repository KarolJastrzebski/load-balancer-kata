package com.example;

import java.net.URI;
import java.util.UUID;

public class Provider {

    private final URI address;

    public Provider(URI address) {
        this.address = address;
    }

    public Provider(String address) {
        this(URI.create(address));
    }

    public static Provider random() {
        return new Provider(URI.create(UUID.randomUUID().toString()));
    }

    public URI getAddress() {
        return address;
    }
}
