package com.eazybytes.springai.controller;

import com.openai.models.ChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptStuffingController {

    private final ChatClient chatClient;

    public PromptStuffingController(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    /**@Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;//import resource from springframework.core.io not from jakarta
    */

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource systemPromptTemplate;

    //Prompt stuffing is used if you are trying to feed very limited amount of data
    //like 100 or 200 lines
    //But in reality there will bw scenarios where you will feed 100 or 200 pages of data
    //then we should not use prompt stuffing. if we use there will be some problems
    //Then there comes in the concept called RAG - Retrieval Augmented Generation
    //THE MAIN PROBLEM WITH PROMPT STUFFING IS TOKENS AND TOKENIZATION
    @GetMapping("/prompt-stuffing")
    public String promptStuffing(@RequestParam("Message") String Message){

        return chatClient.prompt()
                .options(OpenAiChatOptions.builder().model(ChatModel.GPT_4_1_NANO.asString())
                        .temperature(0.7))
                .system(systemPromptTemplate)
                .user(Message)
                .call()
                .content();//logic to interact with the LLM model
    }

}
