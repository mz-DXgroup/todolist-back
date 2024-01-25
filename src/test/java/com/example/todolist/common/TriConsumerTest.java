package com.example.todolist.common;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TriConsumerTest {
    public static void main(String[] args) {
    }
    @Test
    @DisplayName("인터페이스를 익명 객체로 구현")
    public void interfaceFunctionalTest1(){
        System.out.println("인터페이스를 익명 객체로써 구현 :::: ");
        TriConsumer<Integer,String,Long> function = (a, b, c) -> System.out.println("int : " + a + " , String : " +b + " , " + "Long : " + c);
        good(function);
        System.out.println();
    }

    @Test
    @DisplayName("인터페이스를 익명 객체로 구현")
    public void interfaceFunctionalTest3(){
        System.out.println("인터페이스를 메소드 레퍼런스로써 구현 :::: ");
        good((a, b, c) -> function(a, b, c));
        System.out.println();
    }

    @Test
    @DisplayName("인터페이스를 익명 객체로 구현")
    public void interfaceFunctionalTest2(){
        System.out.println("인터페이스를 람다식으써 구현 :::: ");
        good((a,b,c)-> System.out.println("int : " + a + " , String : " +b + " , " + "Long : " + c));
        System.out.println();
    }

    private static void good(TriConsumer<Integer,String,Long> function){
        function.accept(2,"3",4L);
    }

    private static void function(Integer a, String b, Long c){
        System.out.println("int : " + a + " , String : " +b + " , " + "Long : " + c);
    }
}