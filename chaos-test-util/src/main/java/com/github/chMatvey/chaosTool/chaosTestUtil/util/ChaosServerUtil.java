package com.github.chMatvey.chaosTool.chaosTestUtil.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.chMatvey.chaosTool.chaosModels.ChaosSessionInfoResponse;
import com.github.chMatvey.chaosTool.chaosModels.WasFaultInjectedResponse;
import com.github.chMatvey.chaosTool.chaosTestUtil.model.ChaosTestUtilException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class ChaosServerUtil {
    public static final String CHAOS_SERVER_URL = "http://localhost:10501/api/v1/chaos-server";

    public static HttpGet createGetChaosSessionInfoRequest(int sessionId) {
        return new HttpGet(CHAOS_SERVER_URL + "/" + sessionId);
    }

    public static HttpGet createWasFaultInjectedRequest(int sessionId) {
        return new HttpGet(CHAOS_SERVER_URL + "/was-fault-injected/" + sessionId);
    }

    public static ChaosSessionInfoResponse parseChaosSessionInfoResponse(HttpResponse response) {
        try {
            String resultJsonString = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(resultJsonString, ChaosSessionInfoResponse.class);
        } catch (Exception e) {
            throw new ChaosTestUtilException("Cannot parse Chaos session info response");
        }
    }

    public static WasFaultInjectedResponse parseWasFaultInjectedResponse(HttpResponse response) {
        try {
            String resultJsonString = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(resultJsonString, WasFaultInjectedResponse.class);
        } catch (Exception e) {
            throw new ChaosTestUtilException("Cannot parse was Fault injected info response");
        }
    }
}
