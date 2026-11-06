package com.eazybytes.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptTemplateController {

    private final ChatClient chatClient;

    public PromptTemplateController(ChatClient chatClient){
        this.chatClient = chatClient;
    }


    /*building a PromptTemplate
    String promptTemplate =
            """
             A customer named{customerName} sent the following message:
             {customerMessage}
            
             Write a polite and helpful email response addressing the issue. Maintain a professional
             tone and provide reassurance.
            
             Respond as if you are writing the email body only. Don't include subject,
             signature
            """;*/

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;//import resource from springframework.core.io not from jakarta


    @GetMapping("/email")
    public String emailResponse(@RequestParam("customerName") String customerName,
                                @RequestParam("customerMessage") String customerMessage){

        return chatClient.prompt()
                .system("""
                        You are a professional customer service assistant which helps drafting email
                        responses to improve the productivity of the customer support team
                        """)
                .user(promptTemplateSpec ->
                        promptTemplateSpec.text(userPromptTemplate)
                                .param("customerName", customerName)
                                .param("customerMessage", customerMessage)
                )
                .call()
                .content();//logic to interact with the LLM model
    }


}
