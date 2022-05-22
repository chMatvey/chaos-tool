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

import java.util.List;

import static com.github.chMatvey.chaosTool.chaosTestUtil.ChaosTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    public static final String ORDER_SERVICE_URL = "http://localhost:8000/api/v1/order";
    public static final String ORDER_SERVICE_NAME = "Order";
    public static final String WAREHOUSE_SERVICE_NAME = "warehouse";
    public static final String DELIVERY_SERVICE_NAME = "delivery";

    /**
     * Three services: Order, Warehouse, Delivery
     */
    @Test
    void createOrder1() {
        HttpPost httpRequest = createOrderRequest();

        chaosTest(httpRequest, httpResponse -> {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            switch (statusCode) {
                case 201 -> {
                    CreateOrderResponse createOrderResponse = extractCreateOrderResponseBody(httpResponse);
                    if (createOrderResponse.canDeliver()) {
                        assertFalse(wasFaultInjected());
                    } else {
                        assertTrue(wasFaultInjected());
                    }
                }
                case 500, 503 -> {
                    assertTrue(wasFaultInjected());
                }
                default -> {
                    throw new RuntimeException("Unexpected response status");
                }
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
            switch (statusCode) {
                case 201 -> {
                    CreateOrderResponse createOrderResponse = extractCreateOrderResponseBody(httpResponse);
                    if (createOrderResponse.canDeliver()) {
                        assertFalse(wasFaultInjected());
                    } else {
                        assertTrue(wasFaultInjected());
                        InjectedFaultInfo faultInfo = injectedFaultInfo();
                        List<String> servicesNames = faultInfo.getServiceName();
                        assertEquals(1, servicesNames.size());
                        assertEquals(DELIVERY_SERVICE_NAME, servicesNames.get(0));
                    }
                }
                case 500, 503 -> {
                    assertTrue(wasFaultInjected());
                    InjectedFaultInfo faultInfo = injectedFaultInfo();
                    List<String> servicesNames = faultInfo.getServiceName();
                    assertTrue(servicesNames.contains(ORDER_SERVICE_NAME) ||
                            servicesNames.contains(WAREHOUSE_SERVICE_NAME));
                }
                default -> {
                    throw new RuntimeException("Unexpected response status");
                }
            }
        });
    }

    /**
     * Three services: Order, Warehouse, Delivery
     */
    @Test
    void createOrder3() {
        HttpPost httpRequest = createOrderRequest();

        chaosTest(httpRequest, httpResponse -> {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            switch (statusCode) {
                case 201 -> {
                    CreateOrderResponse createOrderResponse = extractCreateOrderResponseBody(httpResponse);
                    if (createOrderResponse.canDeliver()) {
                        assertFalse(wasFaultInjected());
                    } else {
                        assertTrue(wasFaultInjected());
                        InjectedFaultInfo faultInfo = injectedFaultInfo();
                        List<String> servicesNames = faultInfo.getServiceName();
                        assertEquals(1, servicesNames.size());
                        assertEquals(DELIVERY_SERVICE_NAME, servicesNames.get(0));
                    }
                }
                case 404 -> {
                    assertTrue(wasFaultInjected());
                    InjectedFaultInfo faultInfo = injectedFaultInfo();
                    List<String> servicesNames = faultInfo.getServiceName();
                    List<Integer> errorCodes = faultInfo.getErrorCode();
                    assertTrue(servicesNames.contains(WAREHOUSE_SERVICE_NAME));
                    int warehouseIndex = servicesNames.indexOf(WAREHOUSE_SERVICE_NAME);
                    assertEquals(404, errorCodes.get(warehouseIndex));
                    if (servicesNames.size() > 1) {
                        assertTrue(servicesNames.contains(DELIVERY_SERVICE_NAME));
                    }
                }
                case 500, 503 -> {
                    assertTrue(wasFaultInjected());
                    InjectedFaultInfo faultInfo = injectedFaultInfo();
                    List<String> servicesNames = faultInfo.getServiceName();
                    assertTrue(servicesNames.contains(ORDER_SERVICE_NAME) ||
                            servicesNames.contains(WAREHOUSE_SERVICE_NAME));
                }
                default -> {
                    throw new RuntimeException("Unexpected response status");
                }
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
