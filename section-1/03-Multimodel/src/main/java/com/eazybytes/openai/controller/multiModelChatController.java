package com.eazybytes.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class multiModelChatController {

    private final ChatClient openAiChatClient;
    private final ChatClient ollamaChatClient;

    public multiModelChatController(@Qualifier("openAiChatClient") ChatClient openAiChatClient, @Qualifier("ollamaChatClient") ChatClient ollamaChatClient){ //dependency Injection

        this.openAiChatClient = openAiChatClient;
        this.ollamaChatClient = ollamaChatClient;

    }
    @GetMapping("/openai/chat")
    public String openAiChat(@RequestParam("message") String message){

        //call() method will take care of calling the LLM model behind the scenes
        //call() will give response in the form of the CallResponseSpec
        // which is going to have lot off info about the response like metadata
        //but instead of that we can invoke the method content() which going to
        // return me the actual response provided by the LLM as a string object

       return openAiChatClient.prompt(message).call().content();//logic to interact with the LLM model

        //return "hello"+ message;
    }

    @GetMapping("/ollama/chat")
    public String ollamaChat(@RequestParam("message") String message){
        return ollamaChatClient.prompt(message).call().content();
    }
}
