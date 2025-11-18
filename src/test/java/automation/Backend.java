package automation;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
    public class Backend {
        private static String token;

        @BeforeAll
        public static void setup() {
            JSONObject loginBody = new JSONObject();
            loginBody.put("Username", "Testercandidate");
            loginBody.put("Password", "Abc_123");
            loginBody.put("DeviceType", "Web");
            loginBody.put("Model", "IntelliJ");

            Response loginResponse = RestAssured.given()
                    .contentType("application/json")
                    .body(loginBody.toString())
                    .post("https://identity.newdevsrv.matgry.net/api/Account/Login");

            System.out.println("Login status: " + loginResponse.getStatusCode());
            System.out.println("Login response: " + loginResponse.getBody().asString());

            if (loginResponse.getStatusCode() == 200) {
                token = loginResponse.jsonPath().getString("response.accessToken");
                System.out.println("Login successful, token: " + token);
            } else {
                throw new RuntimeException("Login failed! Status: " + loginResponse.getStatusCode());
            }
        }


        @Test
        public void testAddItem() {
            JSONObject addItemBody = new JSONObject();
            addItemBody.put("BalanceType", 0);
            addItemBody.put("Code", "45300");
            addItemBody.put("HasSerials", false);
            addItemBody.put("InsertedBy", "78983815-f9f3-4c80-80e8-011769d4ecbc");
            addItemBody.put("InsertedDate", "2025-11-17T20:36:58.563Z");
            addItemBody.put("IsActivated", true);
            addItemBody.put("IsAllowedToSellWithoutBalance", false);
            addItemBody.put("IsBalance", false);
            addItemBody.put("IsNonTexable", false);
            addItemBody.put("IsSellPriceNeglected", false);
            addItemBody.put("IsServiceWithDatesFromTo", false);
            addItemBody.put("IsServiceWithoutBalance", false);
            addItemBody.put("ItemCategoryid", "884f81bd-80e1-40fe-2969-08ddf9cf8d5c");
            addItemBody.put("ItemImageBase64", "");
            addItemBody.put("ItemImageURL", "");
            addItemBody.put("Name", "bebo");
            addItemBody.put("NameAR", "hoba");
            addItemBody.put("RedialLimit", "5");
            addItemBody.put("UOMid", "be2381e3-4a68-4b2e-0447-08dd5722024d");
            addItemBody.put("UpdatedBy", "78983815-f9f3-4c80-80e8-011769d4ecbc");
            addItemBody.put("UpdatedDate", "2025-11-17T20:36:58.563Z");

            Response addItemResponse = RestAssured.given()
                    .contentType("application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(addItemBody.toString())
                    .post("https://warehouse.newdevsrv.matgry.net/api/Items/AddItem");

            assertEquals(200, addItemResponse.getStatusCode(), "Status code should be 200");
            assertTrue(addItemResponse.getBody().asString().contains("45300"), "Response should contain item code");

            System.out.println("AddItem response: " + addItemResponse.getBody().asString());
        }
    }
