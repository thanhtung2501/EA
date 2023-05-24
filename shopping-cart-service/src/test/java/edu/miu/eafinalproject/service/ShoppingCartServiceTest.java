package edu.miu.eafinalproject.service;

import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartDTO;
import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;
import edu.miu.eafinalproject.shoppingcart.repositories.ShoppingCartRepository;
import edu.miu.eafinalproject.shoppingcart.services.ShoppingCartService;
import edu.miu.eafinalproject.shoppingcart.services.ShoppingCartServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class ShoppingCartServiceTest {
    @TestConfiguration
    static class ShoppingCartServiceImplTestContextConfiguration {
        @Bean
        public ShoppingCartService shoppingCartServiceTest() {
            return new ShoppingCartServiceImpl();
        }
    }

    @Autowired
    private ShoppingCartService shoppingCartServiceTest;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @Before
    public void setUp() {
        Long shoppingCartNumber = 100L;

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setCartItems(new ArrayList<>());
        shoppingCart.setCustomerId(2L);
        shoppingCart.setTotalPrice(10);
        shoppingCart.setShoppingCartNumber(shoppingCartNumber);

        Optional<ShoppingCart> optionalShoppingCart = Optional.of(shoppingCart);
        Mockito.when(shoppingCartRepository.findByShoppingCartNumber(shoppingCartNumber))
                .thenReturn(optionalShoppingCart);
    }

    @Test
    public void whenValidCustomerNumberThenCustomerShouldBeFound() {
        Long shoppingCartNumber = 100L;
        ShoppingCartDTO found = shoppingCartServiceTest.findByShoppingCartNumber(shoppingCartNumber);
        Assertions.assertThat(found.getShoppingCartNumber()).isEqualTo(shoppingCartNumber);
    }
}
