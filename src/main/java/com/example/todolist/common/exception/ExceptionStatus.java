package com.example.todolist.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {

    LOGIN_INFO_DO_NOT_MATCH(401, "아이디 또는 비밀번호가 일치 하지 않습니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    AUTHORIZATION_EXCEPTION(403, "접근할 수 있는 권한이 없습니다."),
    POST_IS_EMPTY(404, " 존재 하지 않습니다."),
    USER_IS_NOT_EXIST(404, "사용자가 존재 하지 않습니다."),
    REQUEST_IS_EMPTY(404, "요청이 존재하지 않습니다."),
    PAGE_IS_NOT_EXIST(404, "요청하신 페이지 내역이 존재하지 않습니다."),
    USERNAME_IS_EXIST(409, "이미 등록된 정보입니다."),
    FILE_NOT_FOUND(404,"파일을 찾을 수 없습니다");


    private final int statusCode;
    private final String message;
}

