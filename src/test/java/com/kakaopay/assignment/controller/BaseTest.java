package com.kakaopay.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Ignore
public class BaseTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected <T> ResultActions assertPostResult(String url, T dto, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(post(url)
                                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                                   .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(resultMatcher);
    }

    protected <T> ResultActions assertGetResult(String url, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(get(url))
            .andDo(print())
            .andExpect(resultMatcher);
    }
}
