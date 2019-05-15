package com.alex;

/**
 * @author: Alex
 * @descripTion:
 * @date: Created in  21:29 2019/5/15
 * @modified By:
 */
public class Test {

    public static void main(String[] args) {
        PhoneNumberGeo geo = new PhoneNumberGeo();
        PhoneNumberInfo lookup = geo.lookup("15608072071");
        System.out.println(lookup);
    }
}
