package br.com.erudio.mockito.staticwithparams;

public class MyUtils {

    public static String getWelcomeMessage(String username, boolean isCustomer) {
        
        if (isCustomer) {
            return "Dear " + username;
        } else {
            return "Hello " + username;
        }
    }
}