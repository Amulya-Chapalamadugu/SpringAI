package com.eazybytes.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class StreamController   {

    private final ChatClient chatClient;

    public StreamController(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    //copy and paste from chat controller class
    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam("message") String message){
        //main advantage of Flux is - it is not going to block the
        // thread until the entire response is received
        //postman does not support streaming of response so go with browser
        return chatClient
                .prompt()
                .user(message)
                .stream()
                .content();//logic to interact with the LLM model

        //return "hello"+ message;
    }

}
