package com.example.todolist.member.web;

import com.epages.restdocs.apispec.Schema;
import com.example.todolist.common.config.AbstractRestDocsTests;
import com.example.todolist.member.MemberFixture;
import com.example.todolist.member.application.LoginService;
import com.example.todolist.member.application.request.MemberJoinRequest;
import com.example.todolist.member.application.request.MemberLoginRequest;
import com.example.todolist.member.application.response.MemberLoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("API 문서화 - 회원가입&로그인")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(MemberLoginController.class)
class MemberLoginControllerTest extends AbstractRestDocsTests {

    @MockBean
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원 등록")
    @Test
    void join() throws Exception {
        // given
        MemberJoinRequest request = MemberFixture.nonExistJoinMember;

        BDDMockito.given(loginService.join(request)).willReturn(null);

        // ...
        mockMvc.perform(post("/api/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(document(
                        resourceDetails()
                                .summary("회원등록")
                                .description("회원등록")
                                .tag("회원")
                                .requestSchema(Schema.schema("MemberJoinRequest")),
                        requestFields(
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("userId").description("사용자아이디"),
                                fieldWithPath("pw").description("비밀번호"),
                                fieldWithPath("role").description("역할")
                        )
                ));

        verify(loginService, times(1)).join(request);
    }

    @DisplayName("회원 로그인")
    @Test
    void login() throws Exception {
        MemberLoginRequest request = MemberFixture.existLoginMember;
        MemberLoginResponse response = new MemberLoginResponse("test", "string", "토큰발급");

        BDDMockito.given(loginService.login(request)).willReturn(response);

        //
        mockMvc.perform(post("/api/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.roles").exists())
                .andExpect(jsonPath("$.jwt").exists())
                .andDo(document(
                        resourceDetails()
                                .summary("로그인")
                                .description("로그인")
                                .tag("회원")
                                .requestSchema(Schema.schema("MemberLoginRequest"))
                                .responseSchema(Schema.schema("MemberLoginResponse")),
                        requestFields(
                                fieldWithPath("userId").description("사용자아이디"),
                                fieldWithPath("pw").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("사용자아이디"),
                                fieldWithPath("roles").description("역할"),
                                fieldWithPath("jwt").description("access token(토큰)")
                        )
                ));

        verify(loginService, times(1)).login(request);
    }
}