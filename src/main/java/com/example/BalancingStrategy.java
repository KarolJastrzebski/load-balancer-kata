package com.example;

import java.util.List;

public interface BalancingStrategy {

    Provider next(List<Provider> providers);
}
