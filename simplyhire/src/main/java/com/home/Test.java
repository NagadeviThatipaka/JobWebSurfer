package com.home;

public class Test {
    public static void main(String[] args){
        String text = "Automatic context help is \r\n" +
                "disabled. Use the toolbar to";

        String singleLine = text.replaceAll("[\r\n]+", " ");

        System.out.println(singleLine);
    }
}
