package com.eazybytes.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/rag")
public class RAGController {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final ChatClient webSearchchatClient;

    @Value("classpath:/promptTemplates/systemPromptRandomDataTemplate.st")
    Resource promptTemplate;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource hrSystemTemplate;
    public RAGController(@Qualifier("chatMemoryChatClient") ChatClient chatClient,
                         @Qualifier("webSearchRAGChatClient") ChatClient webSearchchatClient,
                         VectorStore vectorStore){
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
        this.webSearchchatClient = webSearchchatClient;
    }

    //create a prompt template file in prompt template under resources section
   @GetMapping("/random-chat")
    public ResponseEntity<String> randomChat(@RequestHeader("username") String userName,
                                             @RequestParam("message") String message){

        //inorder to perform a search operation on to the vector store
        // we need to populate an object with name SearchRequest
        /**SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3)
                .similarityThreshold(0.5).build();
        //only top 3 relevant documents will be searched and given

        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);//o/p will be list of documents

        String similarContext = similarDocs.stream()
                                            .map(Document::getText)
                                            .collect(Collectors.joining(System.lineSeparator()));
        //trying to collect as a single string,
        // where each document text is separated by the line separator*/

        String answer = chatClient.prompt()
               /** .system(
                promptSystemSpec -> promptSystemSpec
                        .text(promptTemplate)
                      .param("documents",similarContext)
                )*/
                .advisors(a -> a.param(CONVERSATION_ID, userName))
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(answer);


    }

    @GetMapping("/document-chat")
    public ResponseEntity<String> documentChat(@RequestHeader("username") String userName,
                                             @RequestParam("message") String message){

        /**SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3)
                .similarityThreshold(0.5).build();

        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);//o/p will be list of documents

        String similarContext = similarDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));*/
        String answer = chatClient.prompt()
                /**.system(
                        promptSystemSpec -> promptSystemSpec
                                .text(hrSystemTemplate)
                                .param("documents",similarContext)
                )*/
                .advisors(a -> a.param(CONVERSATION_ID, userName))
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/web-search/chat")
    public ResponseEntity<String> webSearchChat(@RequestHeader("username")
                                                String username, @RequestParam("message") String message) {
        String answer = webSearchchatClient.prompt()
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(message)
                .call().content();
        return ResponseEntity.ok(answer);
    }
}
