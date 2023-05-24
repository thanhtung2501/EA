package edu.miu.eafinalproject.controllers;

import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ShoppingCartControllerTest {

    @BeforeClass
    public static void setup() {
        RestAssured.port = 8282;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "";
    }

    @Test
    public void testGetOneShoppingCart() {
        // add the shoppingCart to be fetched
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setCartItems(new ArrayList<>());
        shoppingCart.setCustomerId(2L);
        shoppingCart.setTotalPrice(10);
        shoppingCart.setShoppingCartNumber(100L);

        given()
                .contentType("application/json")
                .body(shoppingCart)
                .when().post("/carts").then()
                .statusCode(200);
        // test getting the shoppingCart by shoppingcart number
        given()
                .when()
                .get("carts/100")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("message", equalTo("Success"));
    }
}
