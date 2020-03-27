import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class Request {
      @Before
      public void setUp(){
          RestAssured.baseURI = "http://localhost:3000/";
      }

    public static Integer  getUserIdFromRequest(){
        Response response =
                given()
                        .log().all()
                        .when().get("/users").prettyPeek()
                        .then().extract().response();

        Random random = new Random();
        int rastgeleSayi = random.nextInt(Integer.parseInt(response.jsonPath().getString("id.size")));
        int randomId = response.jsonPath().get("["+rastgeleSayi+"].id");
        System.out.println(randomId);
        return randomId;
    }
    public static void getRequest(){
        Response response =
                given()
                .log().all()
                .when().get("/users").prettyPeek()
                .then().extract().response();
    }

    public static void postRequest(int userId,String firstName,String lastName,String email){
          Response response =
                  given()
                  .contentType("application/json")
                  .body("{\n" +
                          "    \"id\": "+userId+",\n" +
                          "    \"first_name\": \""+firstName+"\",\n" +
                          "    \"last_name\": \""+lastName+"\",\n" +
                          "    \"email\": \""+email+"\"\n" +
                          "  }")
                  .when()
                  .post("/users").prettyPeek()
                  .then()
                  .statusCode(201).extract().response();
    }
    public static void putRequestForRandomId(String firstName,String lastName,String email){
          int userId = getUserIdFromRequest();
        Response response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"id\": "+userId+",\n" +
                                "    \"first_name\": \""+firstName+"\",\n" +
                                "    \"last_name\": \""+lastName+"\",\n" +
                                "    \"email\": \""+email+"\"\n" +
                                "  }")
                        .when()
                        .put("/users/"+userId).prettyPeek()
                        .then()
                        .statusCode(200).extract().response();
    }
    public static void putRequest(int userId,String firstName,String lastName,String email){

        Response response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"id\": "+userId+",\n" +
                                "    \"first_name\": \""+firstName+"\",\n" +
                                "    \"last_name\": \""+lastName+"\",\n" +
                                "    \"email\": \""+email+"\"\n" +
                                "  }")
                        .when()
                        .put("/users/"+userId).prettyPeek()
                        .then()
                        .statusCode(200).extract().response();
    }
           public static void deleteRequestForRandomId() {
               int randomIdDelete = getUserIdFromRequest();
              Response response =
                given()
                        .log().all()
                        .when()
                        .delete("/users/" + randomIdDelete).prettyPeek()
                        .then()
                        .statusCode(200).extract().response();
    }
    public static void deleteRequest(int userId) {
        Response response =
                given()
                        .log().all()
                        .when()
                        .delete("/users/" + userId).prettyPeek()
                        .then()
                        .statusCode(200).extract().response();
    }


    @Test
    public void scenarioRequest(){
         getRequest();
         putRequestForRandomId("degisenisim","degisensoyisim","degisenmail@yahoo.com");
         postRequest(7,"Nevzat","Aldırtmaz","nevzataldrtmz1234@gmail.com");
         deleteRequestForRandomId();

    }
}
