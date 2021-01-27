package com.project_PSOiR_PP_KG.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@CrossOrigin(origins = "http://localhost")
@RestController
public class FactorizedNumberController {

//    @GetMapping("/factorize")
//    public String factorizedNumber(@RequestParam String number) throws Exception{
//        String response="Something went wrong.";
//
//        if (number.matches("[0-9]+")) {
////            String request = new JSONObject()
//////                    .put("number",number)
//////                    .toString();
//            String request = "{\"number\":" + number + "}";
//            try (FactorClient factorClient = new FactorClient()) {
//                response = factorClient.call(request);
//
//            } catch (IOException | TimeoutException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return response;
//    }

    @PostMapping("/factorize")
    public String postFactorizedNumber(@RequestBody String request) throws Exception{

        String number;
        JSONObject jsonObject = null;
        String response = new JSONObject()
                    .put("error","Something went wrong.")
                    .toString();

        try {
            jsonObject = new JSONObject(request);
        } catch (JSONException err){
            err.printStackTrace();
            return response;
        }

        number = jsonObject.get("number").toString();

        if (number.matches("[0-9]+")) {
            request = "{\"number\":" + number + "}";
            try (FactorClient factorClient = new FactorClient()) {
                response = factorClient.call(request);

            } catch (IOException | TimeoutException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}