package com.example.todolist.member;

import com.example.todolist.member.application.request.MemberJoinRequest;
import com.example.todolist.member.application.request.MemberLoginRequest;
import com.example.todolist.member.domain.entity.Member;

public class MemberFixture {

    // 회원가입 할 회원 데이터 라고 가정
    public static final MemberJoinRequest nonExistJoinMember = new MemberJoinRequest("홍길동", "test@test.com", "userId", "password", "ADMIN");

    // 이미 회원 디비에 아이디가 존재하는 데이터 라고 가정
    public static final MemberJoinRequest existJoinMember = new MemberJoinRequest("홍길동", "test@test.com", "test", "password", "ADMIN");

    // 디비에 저장되어 있는 회원 데이터 라고 가정
    public static final MemberLoginRequest existLoginMember = new MemberLoginRequest("test", "password");

    // 디비에 저장되어 있지 않는 회원 데이터 라고 가정
    public static final MemberLoginRequest nonExistLoginMember = new MemberLoginRequest("test1", "password");

    // 디비에 저장된 데이터 라고 가정
    public static final Member member = new Member("test", "password");
}
