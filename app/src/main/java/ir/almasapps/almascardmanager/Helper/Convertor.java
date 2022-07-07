package ir.almasapps.almascardmanager.Helper;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahdi on 12/31/2017 AD.
 */

public class Convertor {

    public static class CardMoney{

        public static long makeSimpleMoney(String Money){
            String str = Money;
            final List<String> arrayMoney = Arrays.asList(str.split(","));
            String tmp = "";
            for(int i =0;i<arrayMoney.size();i++){
                tmp += arrayMoney.get(i);
            }
            return  Long.valueOf(tmp);
        }


        public static String makeSplitMoney(long Money) {

            List<String> myNumbers = Arrays.asList(NumberFormat.getNumberInstance(Locale.US).format(Money).split(","));
            long size = myNumbers.size();
            String SplitMoney = "";
            for (int i = 0; i < size; i++) {
                String tmp = myNumbers.get(i).toString();
                SplitMoney += tmp + ",";
            }
            return SplitMoney.substring(0, SplitMoney.length() - 1);
        }

    }
    public static class CardNumber{

        public static String makeSplitNumber(String number){

            String myNumber = number;
            String result = "";
            for(int i=0;i<4;i++){
                try{

                    String str = myNumber.substring(i,i+4);
                    myNumber = myNumber.substring(4);
                    result += str + "-";

                }
                catch (Exception e){
                    result += myNumber;
                }

            }
            return result.substring(0,result.length());
        }

        public static long makeSimpleNumber(String Number){
            String str = Number;
            final List<String> arrayNumber = Arrays.asList(str.split(" - "));
            String tmp = "";
            for(int i =0;i<arrayNumber.size();i++){
                tmp += arrayNumber.get(i);
            }
            return  Integer.valueOf(tmp);
        }

    }
}