package org.school.diary.service;

import lombok.*;
import org.school.diary.dto.Quote;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuotesRestService {

    private final String url = "https://mquotesapi.herokuapp.com/random";

    public Quote getRandomQuote() {
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject(url, Quote.class);
        return Optional.ofNullable(quote).orElseGet(() -> new Quote("", ""));
    }

}

