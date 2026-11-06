package com.eazybytes.springai.controller;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import com.eazybytes.springai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StructuredOutputController {

    private final ChatClient chatClient;

    //whatever the bean we have created(built) here it is specific
    //to this particular class
    public StructuredOutputController(ChatClient.Builder chatClientBuilder){
        //this.chatClient = chatClientBuilder.build();
        //advisors are used to see log contents in the console

        this.chatClient = chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
    }

    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCities> chatBean(@RequestParam("message") String message){
        CountryCities countryCities = chatClient
                .prompt()
                .user(message)
                .call()
                //.entity(new BeanOutputConverter<>(CountryCities.class));
                .entity(CountryCities.class);
                //.content();//if we use the content method,
                // the output will be in string format

        return ResponseEntity.ok(countryCities);
    }

    //to get the city names in list
    @GetMapping("/chat-list")
    public ResponseEntity<List<String>> chatList(@RequestParam("message") String message){
        List<String> countryCities = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter());
        //.content();//if we use the content method,
        // the output will be in string format

        return ResponseEntity.ok(countryCities);
    }


    @GetMapping("/chat-map")
    public ResponseEntity<Map<String,Object>> chatMap(@RequestParam("message") String message){
        Map<String,Object> countryCities = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new MapOutputConverter());
        //.content();//if we use the content method,
        // the output will be in string format

        return ResponseEntity.ok(countryCities);
    }

    /**Mapping the AI response to List<Pojo> using ParameterizedTypeReference*/
    @GetMapping("/chat-bean-list")
    public ResponseEntity<List<CountryCities>> chatBeanList(@RequestParam("message") String message){
        List<CountryCities> countryCities = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });
        //.content();//if we use the content method,
        // the output will be in string format

        return ResponseEntity.ok(countryCities);
    }


}
