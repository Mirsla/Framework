package com.alex;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author: Mirsla
 * @descripTion:
 * @date: Created in  22:57 2019/4/24
 * @modified By:
 */
//@SpringBootApplication
//@EnableConfigurationProperties
public class RunApp {

    static class testint {
        Integer c;

        public Integer getC() {
            return c;
        }

        public void setC(Integer c) {
            this.c = c;
        }

        @Override
        public String toString() {
            return "testint{" +
                    "c=" + c +
                    '}';
        }
    }

    public static void main(String[] args) {
        int a = 600;
        Integer b = new Integer(500);
        testint c = new testint();
        c.setC(700);
        System.out.println("a--->: " + a);
        System.out.println("b--->: " + b.toString());
        System.out.println("c--->: " + c.toString());
        test(a, b, c);
        System.out.println("a--->: " + a);
        System.out.println("b--->: " + b.toString());
        System.out.println("c--->: " + c.toString());
    }

    public static void test(int a, Integer b, testint c) {
        a = 1000;
        b = 2000;
        c.setC(3000);
        System.out.println("a--->: " + a);
        System.out.println("b--->: " + b.toString());
        System.out.println("c--->: " + c.toString());
    }
}
