package com.example.todolist.common.config;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.example.todolist.common.domain.CodeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.QueryParametersSnippet;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import(TestSecurityConfig.class)    // 테스트 실행 시 RestDocsConfiguration.class 의 구성을 불러옴, TestSecurityConfig.class JWT 인증과정을 무시하기 위해 사용
@ExtendWith(RestDocumentationExtension.class)   // 확장..?
public abstract class AbstractRestDocsTests {
    // RestDocs 에 대한 설정을 모든 테스트 클래스에 적지 않기 위해 abstract 클래스로 만들어 각 테스트들이 상속받아 사용 가능하도록 생성

    // 목록 조회 시 기본 페이지 쿼리 파라미터
    private static final List<ParameterDescriptor> PAGE_PARAMS = List.of(
            parameterWithName("page").description("요청 페이지 번호\n\n기본값: 0").optional(),
            parameterWithName("size").description("한 페이지 당 조회할 크기\n\n기본값: 20").optional(),
            parameterWithName("sort").description("정렬 기준").optional()
    );

    // 목록 조회시 페이지 관련 응답
    private static final List<FieldDescriptor> PAGE_FIELDS = List.of(
            fieldWithPath("pageable.pageNumber").description("패이지 번호(0번 부터 시작)"),
            fieldWithPath("pageable.pageSize").description("한 페이지에서의 데이터 수(게시글 수)"),
            fieldWithPath("pageable.sort.empty").description("요청 정렬 여부"),
            fieldWithPath("pageable.sort.sorted").description("요청 정렬 여부"),
            fieldWithPath("pageable.sort.unsorted").description("요청 정렬 여부"),
            fieldWithPath("pageable.offset").description("해당 페이지에 첫 번째 데이터 번호"),
            fieldWithPath("pageable.paged").description("페이징 여부"),
            fieldWithPath("pageable.unpaged").description("페이징 여부"),
            fieldWithPath("totalPages").description("전체 페이지 수"),
            fieldWithPath("totalElements").description("전체 데이터 수"),
            fieldWithPath("last").description("마지막 페이지 여부"),
            fieldWithPath("size").description("현재 페이지 크기"),
            fieldWithPath("number").description("현재 페이지 수"),
            fieldWithPath("sort.empty").description("정렬 여부"),
            fieldWithPath("sort.sorted").description("정렬 여부"),
            fieldWithPath("sort.unsorted").description("정렬 여부"),
            fieldWithPath("numberOfElements").description("현재 페이지이 데이터 수"),
            fieldWithPath("first").description("첫번쨰 페이지 여부"),
            fieldWithPath("empty").description("")
    );

    @Autowired
    protected MockMvc mockMvc;

    protected static QueryParametersSnippet commonWithPageRequestParams(ParameterDescriptor... descriptors) {
        List<ParameterDescriptor> result = Stream.concat(Arrays.stream(descriptors), PAGE_PARAMS.stream()).toList();
        return queryParameters(result);
    }

    protected static ResponseFieldsSnippet commonWithPageResponseFields(FieldDescriptor... descriptors) {
        List<FieldDescriptor> result = Stream.concat(Arrays.stream(descriptors), PAGE_FIELDS.stream()).toList();
        return responseFields(result);
    }

    protected static <T extends Enum<T> & CodeEnum> Attributes.Attribute constraintsAttribute(Class<T> enumClass) {
        String text = EnumSet.allOf(enumClass)
                .stream()
                .map(it -> String.format("%s: %s", it.getCode(), it.getDescription()))
                .collect(Collectors.joining("\n\n"));
        return key("constraints").value(text);
    }

    @BeforeEach // Junit5 에서 사용 | 테스트메서드가 실행되기 전에 실행해야 될 메서드를 지정하는데 사용됨 (예: 테스트 조건을 설정하는 용도 등..)
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {

        /* webAppContextSetup(context) : 컨트롤러와 함께 Service, Repository 와 같은 다른 레이어에 대한 통합테스트가 가능함
           apply(documentationConfiguration(restDocumentation)) : Spring REST Docs 의 문서화를 위한 구성 적용 - API 문서화 작업을 테스트 코드와 함께 관리 가능
           alwaysDo(MockMvcResultHandlers.print()) : 모든 요청에 대한 상세 정보를 콘솔에 출력, 디버그에 유용함
           alwaysDo(restDocs) : 주어진 'MockMvc' 인스턴스에 대한 REST Docs 를 구성해 모든 테스트에서 문서화를 수행할 수 있도록 함.
           addFilters(new CharacterEncodingFilter("UTF-8", true)) : 요청과 응답에 utf-8 인코딩을 강제로 적용하는 필터 추가
           build() : 위 설정을 바탕으로 최종적인 MockMvc 인스턴스를 빌드
         */
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    protected RestDocumentationResultHandler document(ResourceSnippetDetails resourceSnippetDetails, Snippet... snippets) {
        return MockMvcRestDocumentationWrapper.document("{class-name}/{method-name}",
                                                                resourceSnippetDetails,
                                                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), // restdocs json(request) 형식 예쁘게 나오게 함
                                                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()), // restdocs json(response) 형식 예쁘게 나오게 함
                                                                snippets);
    }
}
