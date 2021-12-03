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
public class Validate {
    private final static String CHECK_EMAIL = "^[[\\w_\\.+]*[\\w_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]]{11,50}$";
    private static final String USER_NAME_VALIDATE = "^[[([A-Z][a-z]*((\\s)))+[A-Z][a-z]][\\p{L}-]]{1,30}$";///checked
    private static final String USER_PHONE_VALIDATE = "^([0])\\d{9}$";//checked
    private static final String USER_ADDRESS_VALIDATE = "^[(\\w)(\\d)(\\s)(.,@)[\\p{L}-]]{10,90}$";//check 
    private static final String USER_PASSWORD_VALIDATE = "^[(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%?=*&])]{8,30}$";//cheked

    public static boolean checkEmail(String email){
        return email.matches(CHECK_EMAIL);
    }
    public static boolean checkUserPasswordValidate(String Password){
        return Password.matches(USER_PASSWORD_VALIDATE);
    }
    public static boolean checkUserPhoneValidate(String phoneNumber){
        return phoneNumber.matches(USER_PHONE_VALIDATE);
    }
    public static boolean checkUserNameValidate(String userName){
        return userName.matches(USER_NAME_VALIDATE);
    }
    public static boolean checkUserAddressValidate(String userAddress){
        return userAddress.matches(USER_ADDRESS_VALIDATE);
    }
}
