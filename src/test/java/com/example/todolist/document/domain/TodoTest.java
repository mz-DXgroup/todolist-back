//package com.example.todolist.document.domain;
//
//import com.example.todolist.document.application.dto.request.TodoRequest;
//import com.example.todolist.document.domain.entity.Period;
//import com.example.todolist.document.domain.entity.Todo;
//import com.example.todolist.document.domain.repository.TodoRepository;
//import jakarta.transaction.Transactional;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//
//@DisplayName("todoTest")
//@SpringBootTest
//class TodoTest {
//
//    @Autowired
//    TodoRepository todoRepository;
//
//    public  static  final  Period 시간 = new Period(LocalDateTime.now(),LocalDateTime.now().plusHours(1)) ;
//    public  static  final TodoRequest 할일_요청 = new TodoRequest("1월 10일 할 일 없음", "설명 ㅋ", 시간,true,1);
//
//    @DisplayName("할일 생성이 잘 된다")
//    @Transactional
//    @Test
//    void  createDocumentTest(){
//        Todo todo = 할일_요청.toEntity();
//        Todo savedTodo=  todoRepository.save(todo);
//        Todo result= todoRepository.findById(savedTodo.getId()).orElseThrow(()->new IllegalArgumentException("할 일 null 임"));
//        Assertions.assertThat(savedTodo).isEqualTo(result);
//    }
//}