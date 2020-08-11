package com.example;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LoadBalancer {

    public static final int MAXIMUM_PROVIDERS = 10;
    private final Set<Provider> providers = new HashSet<>();
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

    public Set<Provider> getProviders() {
        return providers;
    }

    public Provider get() {
        if (providers.size() == 0) {
            throw new IllegalStateException("No available providers");
        }
        return strategy.next(providers);
    }
}
