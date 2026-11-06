package com.eazybytes.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatMemoryController {

    private final ChatClient chatClient;

    public ChatMemoryController(@Qualifier("chatMemoryChatClient") ChatClient theChatClient){
        chatClient = theChatClient;
    }

    @GetMapping("/chat-memory")
    public ResponseEntity<String> chatMemory(@RequestHeader("username") String userName,
                                             @RequestParam("message") String message){

        return ResponseEntity.ok(chatClient.prompt()
                        .user(message)
                        .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,userName))
                        //.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,"default"))
                /**this is the conversation id key that the framework has
                 * to consider to store all the conversations
                 * that are going to happen */
                        .call()
                        .content()
        );
    }
}
