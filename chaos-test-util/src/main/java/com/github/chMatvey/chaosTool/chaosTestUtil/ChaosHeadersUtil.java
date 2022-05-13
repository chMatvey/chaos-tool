package com.github.chMatvey.chaosTool.chaosTestUtil;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import static com.github.chMatvey.chaosTool.chaosModels.ChaosHeaders.*;
import static com.github.chMatvey.chaosTool.chaosTestUtil.Util.ofNullableOrThrowError;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class ChaosHeadersUtil {
    static Integer getSessionIdFromResponseHeaders(HttpResponse response) {
        Header header = ofNullableOrThrowError(
                response.getFirstHeader(CHAOS_SESSION_ID_HEADER),
                format("Header %s does not present", CHAOS_TEST_CASE_STEP_HEADER)
        );
        String headerValueAsString = ofNullableOrThrowError(
                header.getValue(),
                format("Header %s value does not present", CHAOS_TEST_CASE_STEP_HEADER)
        );
        try {
            return parseInt(headerValueAsString);
        } catch (NumberFormatException e) {
            throw new ChaosTestUtilException(format("Cannot parse %s header value", CHAOS_TEST_CASE_ID_HEADER), e);
        }
    }
}
