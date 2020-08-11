package com.example;

import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class LoadBalancerTest {

    @Test
    public void can_register_provider() {
        LoadBalancer loadBalancer = new LoadBalancer();
        loadBalancer.register(Provider.random());

        assertThat(loadBalancer.getProviders()).hasSize(1);
    }

    @Test
    public void initially_is_empty() {
        LoadBalancer loadBalancer = new LoadBalancer();

        assertThat(loadBalancer.getProviders()).hasSize(0);
    }

    @Test
    public void can_register_maximum_of_10_providers() {
        LoadBalancer loadBalancer = new LoadBalancer();
        for (int i = 0; i < 20; i++) {
            try {
                loadBalancer.register(Provider.random());
            } catch (Exception e) {
                // suppress
            }
        }
        assertThat(loadBalancer.getProviders()).hasSize(10);
    }

    @Test
    public void throws_an_exception_when_registering_providers_above_limit() {
        LoadBalancer loadBalancer = new LoadBalancer();
        Throwable throwable = catchThrowable(() -> {
            for (int i = 0; i < 11; i++) {
                loadBalancer.register(Provider.random());
            }
        });
        assertThat(throwable)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Maximum limit of providers reached");
    }

    @Test
    public void each_registered_provider_should_have_a_unique_address() {
        LoadBalancer loadBalancer = new LoadBalancer();
        loadBalancer.register(new Provider("a"));

        Throwable throwable = catchThrowable(() -> loadBalancer.register(new Provider("a")));

        assertThat(throwable)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Duplicate detected");
    }

    @Test
    public void expects_registered_provider_to_not_be_null() {
        LoadBalancer loadBalancer = new LoadBalancer();

        Throwable throwable = catchThrowable(() -> loadBalancer.register(null));

        assertThat(throwable)
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Null-objects are bad");
    }

    @Test
    public void chooses_from_registered_providers() {
        LoadBalancer loadBalancer = new LoadBalancer();
        Provider aProvider = Provider.random();
        loadBalancer.register(aProvider);

        assertThat(loadBalancer.get()).isEqualTo(aProvider);
    }

    @Test
    public void randomly_chooses_from_registered_providers() {
        BalancingStrategy aStrategy = Mockito.mock(BalancingStrategy.class);
        LoadBalancer loadBalancer = new LoadBalancer(aStrategy);
        Provider aProvider = Provider.random();
        loadBalancer.register(aProvider);

        loadBalancer.get();

        Mockito.verify(aStrategy, Mockito.atLeastOnce()).next(Mockito.any());
    }

    @Test
    public void calling_get_without_any_registered_provider_throws_an_exception() {
        LoadBalancer aLoadBalancer = new LoadBalancer();

        Throwable throwable = catchThrowable(aLoadBalancer::get);

        assertThat(throwable)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("No available providers");
    }
}
