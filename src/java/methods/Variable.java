/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author SE140018
 */
public class Variable {

    private static final int USER_ROLE = 1;
    private static final int ADMIN_ROLE = 2;

    public static int getADMIN_ROLE() {
        return ADMIN_ROLE;
    }

    public static int getUSER_ROLE() {
        return USER_ROLE;
    }

    public static java.sql.Timestamp SQL_CHECK_IN_DATE(String checkin) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        checkin += " 18:00:00";
        Date checkinDate = dateFormat.parse(checkin);
        java.sql.Timestamp sqlCheckinDate = new java.sql.Timestamp(checkinDate.getTime());
        return sqlCheckinDate;
    }

    public static java.sql.Timestamp SQL_CHECK_OUT_DATE(String checkout) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        checkout += " 12:00:00";
        Date checkoutDate = dateFormat.parse(checkout);
        java.sql.Timestamp sqlCheckoutDate = new java.sql.Timestamp(checkoutDate.getTime());
        return sqlCheckoutDate;
    }

    public static long getDaysWithCheckInAndCheckOut(String checkin, String checkout) throws Exception {
        TimeUnit time = TimeUnit.DAYS;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date checkinDate = dateFormat.parse(checkin);
        Date checkoutDate = dateFormat.parse(checkout);
        return time.convert(checkoutDate.getTime() - checkinDate.getTime(), TimeUnit.MILLISECONDS);

    }

    public static java.sql.Timestamp convertDateToTimestamp(Date date) throws Exception {
        java.sql.Timestamp sqlCheckoutDate = new java.sql.Timestamp(date.getTime());
        return sqlCheckoutDate;
    }
    public static java.sql.Timestamp convertDateStringToTimestamp(String dateString) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateString += " 00:00:00";
        Date date = dateFormat.parse(dateString);
        java.sql.Timestamp sqlCheckoutDate = new java.sql.Timestamp(date.getTime());
        return sqlCheckoutDate;
    }
    public static String convertTimestampToString(java.sql.Timestamp timestamp) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp.getTime());
        return dateFormat.format(date);
    }
    public static boolean  compareDatewithCurrenDate(String date) throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date checkDate = dateFormat.parse(date);
        Date currentDate = new Date();
        String currentDateSTr = dateFormat.format(currentDate);
        Date currentDateAfterFormat = dateFormat.parse(currentDateSTr);
        return checkDate.getTime() >= currentDateAfterFormat.getTime();        
    }
    public static void main(String[] args) throws Exception{
        System.out.println(compareDatewithCurrenDate("2021-10-10"));
    }

}
