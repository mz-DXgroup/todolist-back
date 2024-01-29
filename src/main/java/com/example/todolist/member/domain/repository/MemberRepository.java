package com.example.todolist.member.domain.repository;

import com.example.todolist.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByUserId(String userId);
}
