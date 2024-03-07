package com.example.todolist.document.web;

import com.example.todolist.common.config.AbstractRestDocsTests;
import com.example.todolist.document.application.DocumentService;
import com.example.todolist.document.application.dto.request.DocumentRequest;
import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.status.DayStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("API 문서화 - Document")
@MockBean(JpaMetamodelMappingContext.class) // 웹 계층의 경우 JPA 계층을 로드하지 않아 JPA 와 관련된 부분을 신경 쓰지 않기 위해 사용
@WebMvcTest(DocumentController.class)
class DocumentControllerTest extends AbstractRestDocsTests {

    @MockBean
    private DocumentService documentService;

    @Autowired
    private ObjectMapper objectMapper;  // Jackson 라이브러리의 클래스 - Java 객체와 JSON 사이의 변환을 담당하는 역할

    @DisplayName("Document 등록")
    @Test
    void createDocument() throws Exception {

        // given
        DocumentRequest request = new DocumentRequest(new Period(LocalDate.now(), LocalDate.now()),
                "제목1", "설명1", "string", DayStatus.VERYGOOD);

        BDDMockito.given(documentService.createDocument(request)).willReturn(1);

        // ...
        mockMvc.perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document(
                        resourceDetails()
                                .summary("Document 등록")
                                .description("Document 등록")
                                .tag("Document"),
                        requestFields(
                                fieldWithPath("period.startDate").description("시작일"),
                                fieldWithPath("period.endDate").description("종료일"),
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("description").description("설명"),
                                fieldWithPath("userId").description("사용자아이디"),
                                fieldWithPath("dayStatus").description("상태").optional()
                        )
                ));
    }
}