package com.eazybytes.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    //Understanding message roles in LLM's
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder){ //dependency Injection

        this.chatClient = chatClientBuilder.build();
        //with chatClient we can interact with the LLM of the openai model

    }
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message){

        //call() method will take care of calling the LLM model behind the scenes
        //call() will give response in the form of the CallResponseSpec
        // which is going to have lot off info about the response like metadata
        //but instead of that we can invoke the method content() which going to
        // return me the actual response provided by the LLM as a string object

       return chatClient.prompt(message).call().content();//logic to interact with the LLM model

        //return "hello"+ message;
    }
}
