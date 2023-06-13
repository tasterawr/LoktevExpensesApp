package com.example.loktevexpensesapp.ui.util;

public class ArgumentManager {
    private static String argument;
    private static boolean argumentPresent = false;

    public static boolean hasArgument(){
        return argumentPresent;
    }

    public static String getArgument(){
        return argument;
    }

    public static void setArgument(String arg){
        argumentPresent = true;
        argument = arg;
    }

    public static void reset(){
        argumentPresent = false;
        argument = "";
    }
}
