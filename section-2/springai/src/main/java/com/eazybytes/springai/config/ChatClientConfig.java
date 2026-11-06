package com.eazybytes.springai.config;

import com.eazybytes.springai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder){

        var options = OpenAiChatOptions.builder().model("gpt-5.4-mini").temperature(0.8)
                .maxCompletionTokens(100);

        //chatClientBuilder is used to build the bean of chatClient
        return chatClientBuilder
                .defaultOptions(options)/**using this we can set alist of chat options
                // that can be leveraged by the framework whwneever someone is
                // trying to invoke the LLM model by using this chatclient bean */
                //.defaultAdvisors(new SimpleLoggerAdvisor())
                //.defaultAdvisors(new TokenUsageAuditAdvisor())
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                //telling out llm to act as an internal HR assistant
                .defaultSystem("""
                You are an internal HR assistant. Your role is to help\s
                employees with questions related to HR Policies. If a user\s
                asks other those policies, kindly inform the that you can\s
                only assist them with queries related to HR policies.""")
                .defaultUser("How can you help me today?")
                .build();

    }
}
