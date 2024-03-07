package com.example.todolist.common.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;

@TestConfiguration  // 테스트 시에만 빈 등록 되도록
public class RestDocsConfiguration {

    @Bean
    public RestDocumentationResultHandler write() {
        return document(
                "{class-name}/{method-name}", // identifier
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
        );
    }
}
