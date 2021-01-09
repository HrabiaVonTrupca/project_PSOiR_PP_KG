package com.project_PSOiR_PP_KG.api;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
public class FactorizedNumberController {

    private final static String QUEUE_NAME = "hello";
    private Connection connection;
    private Channel channel;

    @GetMapping("/factorize")
    public String factorizedNumber(@RequestParam long number) throws Exception{
        String request = new JSONObject()
                .put("number",number)
                .toString();
        String response="Something went wrong.";

        try (FactorClient factorClient = new FactorClient()) {
            response = factorClient.call(request);

        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();

        }
        return response;
    }
}