package api.steps;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import pojo.RegisterRq;
import pojo.SignInRq;

import static config.Config.*;
import static io.restassured.RestAssured.given;


public class UserSteps {
    public static RequestSpecification rqSpec =
            new RequestSpecBuilder()
                    .setBaseUri(BASE_URL + "/api")
                    .setBasePath(AUTH_PATH)
                    .setContentType(ContentType.JSON)
                    .build();

    @Step("Создание пользователя")
    public static ValidatableResponse createUser(RegisterRq body) {
        return given()
                .spec(rqSpec)
                .body(body)
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(200);
    }

    @Step("Удаление пользователя")
    public static ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(rqSpec)
                .header("Authorization", accessToken)
                .when()
                .delete(USER_PATH)
                .then()
                .statusCode(202);
    }

    @Step("Авторизация пользователя")
    public static ValidatableResponse signIn(String email, String password) {
        SignInRq body = new SignInRq(email, password);
        return given()
                .spec(rqSpec)
                .body(body)
                .when()
                .post(LOGIN_PATH)
                .then()
                .statusCode(200);
    }
}
