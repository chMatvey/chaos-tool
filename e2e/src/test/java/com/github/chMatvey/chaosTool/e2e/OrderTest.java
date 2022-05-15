package com.github.chMatvey.chaosTool.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.chMatvey.chaosTool.chaosTestUtil.model.InjectedFaultInfo;
import com.github.chMatvey.chaosTool.clients.order.CreateOrderRequest;
import com.github.chMatvey.chaosTool.clients.order.CreateOrderResponse;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import static com.github.chMatvey.chaosTool.chaosTestUtil.ChaosTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTest {
    public static final String ORDER_SERVICE_URL = "http://localhost:8000/api/v1/order";
    public static final String ORDER_SERVICE_NAME = "Order";
    public static final String WAREHOUSE_SERVICE_NAME = "Warehouse";
    public static final String DELIVERY_SERVICE_NAME = "Delivery";

    /**
     * Three services: Order, Warehouse, Delivery
     */
    @Test
    void createOrder1() {
        HttpPost httpRequest = createOrderRequest();

        chaosTest(httpRequest, httpResponse -> {
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 201) {
                CreateOrderResponse createOrderResponse = extractCreateOrderResponseBody(httpResponse);
                if (createOrderResponse.canDeliver()) {
                    assertFalse(wasFaultInjected());
                } else {
                    assertTrue(wasFaultInjected());
                }
            } else if (statusCode == 500 || statusCode == 503) {
                assertTrue(wasFaultInjected());
            } else {
                throw new RuntimeException("Unexpected response status");
            }
        });
    }

    /**
     * Three services: Order, Warehouse, Delivery
     */
    @Test
    void createOrder2() {
        HttpPost httpRequest = createOrderRequest();

        chaosTest(httpRequest, httpResponse -> {
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 201) {
                CreateOrderResponse createOrderResponse = extractCreateOrderResponseBody(httpResponse);
                if (createOrderResponse.canDeliver()) {
                    assertFalse(wasFaultInjected());
                } else {
                    assertTrue(wasFaultInjected());
                    InjectedFaultInfo faultInfo = injectedFaultInfo();
                    assertTrue(faultInfo.getServiceName().equalsIgnoreCase(DELIVERY_SERVICE_NAME));
                }
            } else if (statusCode == 500 || statusCode == 503) {
                assertTrue(wasFaultInjected());
                InjectedFaultInfo faultInfo = injectedFaultInfo();
                String serviceName = faultInfo.getServiceName();
                assertTrue(serviceName.equalsIgnoreCase(ORDER_SERVICE_NAME) ||
                        serviceName.equalsIgnoreCase(WAREHOUSE_SERVICE_NAME));
            } else {
                throw new RuntimeException("Unexpected response status");
            }
        });
    }

    @SneakyThrows
    private HttpPost createOrderRequest() {
        HttpPost createOrderRequest = new HttpPost(ORDER_SERVICE_URL);

        createOrderRequest.setHeader("Accept", "application/json");
        createOrderRequest.setHeader("Content-type", "application/json");

        CreateOrderRequest orderRequestBody = new CreateOrderRequest("Test Chaos Tool");
        ObjectMapper objectMapper = new ObjectMapper();
        String orderRequestBodyAsJsonString = objectMapper.writeValueAsString(orderRequestBody);
        StringEntity createOrderRequestEntity = new StringEntity(orderRequestBodyAsJsonString);

        createOrderRequest.setEntity(createOrderRequestEntity);

        return createOrderRequest;
    }

    @SneakyThrows
    private CreateOrderResponse extractCreateOrderResponseBody(HttpResponse httpResponse) {
        String jsonBodyAsString = EntityUtils.toString(httpResponse.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonBodyAsString, CreateOrderResponse.class);
    }
}
