package util;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.Logger;
import static io.restassured.RestAssured.given;

public class RequestHandler {
    private static Logger logger = TestLogger.logger;
    private static final RequestSpecification REQUEST_SPECIFICATION = InitializeSpecification.createRequestSpecification();
    private static final ResponseSpecification CHECK_SUCCESS_STATUS_CODE_AND_CONTENT_TYPE = InitializeSpecification.createSuccessResponseSpecification();
    private static final ResponseSpecification CHECK_ERROR_STATUS_CODE_AND_CONTENT_TYPE = InitializeSpecification.createErrorResponseSpecification();

    public static Response sendValidGetRequest(String path, String parameterName, String parameterValue) {
        Response response = given().spec(REQUEST_SPECIFICATION)
                .param(parameterName, parameterValue)
                .get(path);
        logger.info("GET /" + path + "?" + parameterName + "=" + parameterValue);
        response.then()
                .assertThat()
                .spec(CHECK_SUCCESS_STATUS_CODE_AND_CONTENT_TYPE);
        logger.info(response.getStatusCode());

        return response;
    }

    public static Response sendValidGetRequest(String path, String parameterName, int parameterValue) {
        Response response = given().spec(REQUEST_SPECIFICATION)
                .param(parameterName, parameterValue)
                .get(path);
        logger.info("GET /" + path + "?" + parameterName + "=" + parameterValue);
        response.then()
                .assertThat()
                .spec(CHECK_SUCCESS_STATUS_CODE_AND_CONTENT_TYPE);
        logger.info(response.getStatusCode());

        return response;
    }

    public static Response sendValidGetRequest(String path) {
        Response response = given().spec(REQUEST_SPECIFICATION)
                .get(path);
        logger.info("GET /" + path);
        response.then()
                .assertThat()
                .spec(CHECK_SUCCESS_STATUS_CODE_AND_CONTENT_TYPE);
        logger.info(response.getStatusCode());

        return response;
    }

    public static Response sendInvalidGetRequest(String path) {
        Response response = given().spec(REQUEST_SPECIFICATION)
                .get(path);
        logger.info("GET /" + path);
        response.then()
                .assertThat()
                .spec(CHECK_ERROR_STATUS_CODE_AND_CONTENT_TYPE);
        logger.info(response.getStatusCode());

        return response;
    }
}
