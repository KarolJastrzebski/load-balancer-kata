package com.example;

import java.util.Set;

public class RandomBalancingStrategy implements BalancingStrategy {

    @Override
    public Provider next(Set<Provider> providers) {
        return providers.iterator().next();
    }
}
