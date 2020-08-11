package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoadBalancer {

    public static final int MAXIMUM_PROVIDERS = 10;
    private final List<Provider> providers = new ArrayList<>();
    private final BalancingStrategy strategy;

    public LoadBalancer() {
        this(new RandomBalancingStrategy());
    }

    public LoadBalancer(BalancingStrategy strategy) {
        this.strategy = strategy;
    }

    public void register(Provider provider) {
        Objects.requireNonNull(provider, "Null-objects are bad");
        if (providers.size() >= MAXIMUM_PROVIDERS) {
            throw new IllegalStateException("Maximum limit of providers reached");
        }
        boolean exists = providers.stream()
            .anyMatch(registeredProvider -> provider.getAddress().equals(registeredProvider.getAddress()));

        if (exists) {
            throw new IllegalStateException("Duplicate detected");
        }
        providers.add(provider);
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public Provider get() {
        if (providers.size() == 0) {
            throw new IllegalStateException("No available providers");
        }
        return strategy.next(providers);
    }
}
