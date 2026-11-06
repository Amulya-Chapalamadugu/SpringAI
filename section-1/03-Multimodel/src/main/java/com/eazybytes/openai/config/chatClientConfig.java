package com.eazybytes.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class chatClientConfig {

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel){

        return ChatClient.create(openAiChatModel);
        //with this single line of code BTS by using openaichatmodel,
        // it is going to create a chatclient bean
        //using which we can interact with an openai LLM models
        //this is one style of crating a chatclient bean
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel){

        ChatClient.Builder chatClientBuilder = ChatClient.builder(ollamaChatModel);
        return chatClientBuilder.build();
        //with this single line of code BTS by using openaichatmodel,
        // it is going to create a chatclient bean
        //using which we can interact with an openai LLM models
        //this is one style of crating a chatclient bean
    }
}
