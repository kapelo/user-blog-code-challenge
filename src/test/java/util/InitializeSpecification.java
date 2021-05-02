package util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.Matchers.*;

class InitializeSpecification {
    private static Logger logger = TestLogger.logger;

    static RequestSpecification createRequestSpecification() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(PropertyValues.BASE_URL);
        requestSpecBuilder.addHeader("Content-Type", "application/json");

        logger.info("Request spec initialized!");

        return requestSpecBuilder.build();
    }

    static ResponseSpecification createSuccessResponseSpecification() {

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(is(both(greaterThanOrEqualTo(100)).and(lessThanOrEqualTo(399))));
        responseSpecBuilder.expectContentType(ContentType.JSON);

        logger.info("Response (success) spec initialized!");

        return responseSpecBuilder.build();
    }

    static ResponseSpecification createErrorResponseSpecification() {

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(is(both(greaterThanOrEqualTo(400)).and(lessThanOrEqualTo(505))));
        responseSpecBuilder.expectContentType(ContentType.JSON);

        logger.info("Response (error) spec initialized!");

        return responseSpecBuilder.build();
    }
}
