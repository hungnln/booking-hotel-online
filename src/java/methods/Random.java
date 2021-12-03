/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

/**
 *
 * @author SE140018
 */
public class Random {
    private static final int ORDER_ID_LENGTH = 99999;
    public static String getRandomBookingID() {
        java.util.Random random = new java.util.Random();
        String randomIDStr = String.valueOf(random.nextInt(ORDER_ID_LENGTH));
        return randomIDStr;
    }
}
