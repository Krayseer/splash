package ru.anykeyers.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@SpringBootApplication
@RestController
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @GetMapping("/token")
    public Mono<Principal> getToken(Principal principal) {
        return Mono.just(principal);
//        return Mono.just(authorizedClient.getAccessToken().getTokenValue());
    }


}
