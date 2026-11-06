package com.eazybytes.springai.controller;

import com.eazybytes.springai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class ChatController {

    private final ChatClient chatClient;

    /**public ChatController(ChatClient.Builder chatClientBuilder){ //dependency Injection

        //setting a default system instructions for any number of rest api's
        this.chatClient = chatClientBuilder.defaultSystem("""
                You are an internal HR assistant. Your role is to help\s
                employees with questions related to HR Policies. If a user\s
                asks other those policies, kindly inform the that you can\s
                only assist them with queries related to HR policies.""")
                .defaultUser("How can you help me today?").build();

    }**/

    public ChatController(ChatClient chatClient){
        this.chatClient = chatClient;
    }
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message){
        //setting the behavior or role of the AI assistant
        //and overriding the default system instructions
        // which is specific to this get rest api

        return chatClient.prompt()
                //.advisors(new TokenUsageAuditAdvisor())
                .system("""
                        You are an internal IT helpdesk assistant. Your role is to assist \s
                        employees with IT-related issues such as resetting passwords, 
                        unlocking accounts, and answering questions related to IT policies.
                        If a user requests help with anything outside of these \s
                        responsibilities, respond politely and inform them that you are \s
                        only able to assist with IT support tasks within your defined scope.
                        """)
                .user(message)
                .call()
                .content();//logic to interact with the LLM model

        //return "hello"+ message;
    }

}
