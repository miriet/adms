package postech.adms.common.util;

public class StringUtil {

    /*
    *  'YYYYMMDD' 포멧의 문자열을 받아서 'YYYY-MM-DD' 포멧으로 변환
    * */
    public static String changeDateFormat(String dateString){
        String result = "";
        if (dateString != null && dateString.length() == 8) {
            result += dateString.substring(0,4) + "-" + dateString.substring(4,6) + "-" + dateString.substring(6);
        } else if(dateString.length()==10){
            result = dateString.substring(0,4) + dateString.substring(5,7) + dateString.substring(8);
        }

        System.out.println("dateString:: " + dateString + ", changedDate::" + result);
        return result;
    }

    public static void main(String[] args) {
        StringUtil.changeDateFormat("20010101");
        StringUtil.changeDateFormat("1971-09-02");
    }
}
