package com.example;

import java.util.List;

public class RandomBalancingStrategy implements BalancingStrategy {

    @Override
    public Provider next(List<Provider> providers) {
        return providers.get(0);
    }
}
