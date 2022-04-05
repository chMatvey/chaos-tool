package com.github.chMatvey.chaosTool.e2e;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static com.github.chMatvey.chaosTool.e2e.ChaosTest.wasFautInjected;
import static org.junit.jupiter.api.Assertions.*;

public class CreateOrderTest {
    @Test
    void createOrder() {
        Request request = new Request();
        ClassLoader loader = getClass().getClassLoader();
        URL resource = loader.getResource("create-order.json");
        Response response = request.post("/api/order", resource);
        Json jsonBody = response.jsonBody();
        int status = response.status();

        if (status == 200) {
            assertTrue(jsonBody.hasField("id"));
            assertTrue(jsonBody.hasField("name"));

            if (wasFautInjected(response)) {
                assertFalse(jsonBody.hasField("canDeliver"));
            } else {
                assertTrue(jsonBody.hasField("canDeliver"));
            }
        } else if (status == 500 || status == 503) {
            assertTrue(wasFautInjected(response));
        } else {
            throw new RuntimeException("Unexpected response status");
        }
    }
}
