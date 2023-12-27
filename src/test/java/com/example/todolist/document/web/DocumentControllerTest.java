package com.example.todolist.document.web;

import com.example.todolist.document.application.TodoService;
import com.example.todolist.document.application.dto.TodoRequest;
import com.example.todolist.document.domain.entity.Period;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@DisplayName("API 문서화 - 내 문서")
@WebMvcTest(TodoController.class)
public class DocumentControllerTest {

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

//    @BeforeEach
//    protected void  setup(WebApplicationContext context){
//        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
//    }

    @BeforeEach
    protected void  setup(WebApplicationContext context, RestDocumentationContextProvider restDocumentation){
        mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }


    @DisplayName("todo 생성 테스트")
    @Test
    void createDocument() throws Exception {
        Period 날짜 = new Period(LocalDateTime.now(), LocalDateTime.now());
        TodoRequest todoRequest = new TodoRequest("이거할거임 ㅋ", "설명입니다", 날짜, true, 4);

        BDDMockito.given(todoService.createTodo(any())).willReturn(1);


        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(todoRequest)))
                .andExpect(status().isCreated());
    }
}
