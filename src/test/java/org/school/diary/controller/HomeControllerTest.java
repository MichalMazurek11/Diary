package org.school.diary.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.school.diary.dto.Quote;
import org.school.diary.model.Subject;
import org.school.diary.service.QuotesRestService;
import org.school.diary.service.SubjectService;
import org.school.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = HomeController.class)
@WebAppConfiguration
public class HomeControllerTest {

    private MockMvc mockMvc;
    @Autowired
    public WebApplicationContext webApplicationContext;
    @MockBean
    private QuotesRestService quotesRestService;
    @MockBean
    private UserService userService;


    @Test
    public void shouldGetTeacher() throws Exception {
        Quote mockedQuote = new Quote("Gdybym nie był sobą to byłbym kimś innym", "Albert Einstein");
        Mockito.when(quotesRestService.getRandomQuote()).thenReturn(mockedQuote);
        mockMvc=MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("quote"))
                .andExpect(MockMvcResultMatchers.model().attribute("quote",mockedQuote));
    }
}