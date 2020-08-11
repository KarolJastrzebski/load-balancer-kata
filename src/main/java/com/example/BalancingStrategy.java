package com.example;

import java.util.Set;

public interface BalancingStrategy {

    Provider next(Set<Provider> providers);
}
