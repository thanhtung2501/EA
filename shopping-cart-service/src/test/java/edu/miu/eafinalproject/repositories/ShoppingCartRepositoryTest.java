package edu.miu.eafinalproject.repositories;

import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;
import edu.miu.eafinalproject.shoppingcart.repositories.ShoppingCartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ShoppingCartRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    ShoppingCart shoppingCart;
    List<ShoppingCart> shoppingCarts;

    @Before
    public void setup() {
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setCartItems(new ArrayList<>());
        shoppingCart.setCustomerId(2L);
        shoppingCart.setTotalPrice(10);
        shoppingCart.setShoppingCartNumber(100L);

        shoppingCarts = new ArrayList<>();
        shoppingCarts.add(shoppingCart);
    }

    @Test
    public void whenFindByShoppingCartNumber_thenReturnShoppingCart() {
        //given
        entityManager.persist(shoppingCart);
        entityManager.flush();

        //when
        ShoppingCart found = shoppingCartRepository.findByShoppingCartNumber(shoppingCart.getShoppingCartNumber()).get();

        //then
        assertEquals(found.getCustomerId(), shoppingCart.getCustomerId());

    }
}
