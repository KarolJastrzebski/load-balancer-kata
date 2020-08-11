package com.example;

import java.net.URI;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Provider)) {
            return false;
        }

        Provider provider = (Provider) o;

        return Objects.equals(address, provider.address);
    }

    @Override
    public int hashCode() {
        return address != null ? address.hashCode() : 0;
    }
}
