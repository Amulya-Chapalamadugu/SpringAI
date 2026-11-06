package com.eazybytes.springai.rag;

import jakarta.annotation.PostConstruct;
import org.apache.tika.Tika;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HRPolicyDataLoader {

    public final VectorStore vectorStore;
    @Value("classpath:/Eazybytes_HR_Policies.pdf")
    Resource policyFile;
    public HRPolicyDataLoader(VectorStore vectorStore){

        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadPDF(){

        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(policyFile);
        List<Document> docs = tikaDocumentReader.get();
        System.out.println("Docs loaded: "+docs.size());
        //vectorStore.add(docs);
        //System.out.println("Added to the vector store");
        TextSplitter textSplitter = TokenTextSplitter.builder()
                                    .withChunkSize(200).withMaxNumChunks(400).build();
        //Splitting the docs
        vectorStore.add(textSplitter.split(docs));
    }
}
