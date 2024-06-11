package com.bring.back.member;

import com.bring.back.member.dto.MemberJoinRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext context;

    @Test
    @DisplayName("회원가입 테스트")
    public void join() throws Exception {
        //given
        MockHttpSession session=new MockHttpSession();

        //when
        //then
        ResultActions req = mvc.perform(post("/member/join")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{" +
                        "    \"id\": \"5\"," +
                        "    \"name\": \"유관순\"," +
                        "    \"pwd\": \"1234\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").exists());

    }

    @Test
    @DisplayName("로그인 테스트")
    public void login() throws Exception {
        //given
        MockHttpSession session=new MockHttpSession();

        //when
        //then
        ResultActions req = mvc.perform(post("/member")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{" +
                                "    \"id\": \"1\"," +
                                "    \"pwd\": \"1234\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("msg").exists());

    }

}