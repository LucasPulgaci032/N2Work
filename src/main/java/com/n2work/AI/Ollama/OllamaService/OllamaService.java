package com.n2work.AI.Ollama.OllamaService;

import com.n2work.AI.Ollama.AIService;
import com.n2work.AI.Ollama.OllamaResponse.OllamaResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class OllamaService implements AIService {
    private final WebClient webClient;

    public OllamaService(WebClient.Builder builder){
        this.webClient = builder
                .baseUrl("http://localhost:11434")
                .build();
    }
    @Override
    public String generateResponse(String prompt){
        Map<String,Object> request = Map.of("model","llama3",
                "prompt",prompt,
                "stream",false
        );
        return webClient.post()
                .uri("/api/generate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OllamaResponse.class)
                .map(OllamaResponse::getResponse)
                .block();

    }


}
