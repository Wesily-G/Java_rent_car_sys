package DBManager;
//import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * ����java.util.Date��java.sql.Timestamp��String֮��Ļ���ת���ķ���
 * @Description: TODO
 * @CreateTime: 2017��10��25�� ����3:20:44
 * @version V1.0
 */
public class DateFormat {

    /**
     * ��String�ַ���ת��Ϊjava.util.Date��ʽ����
     *
     * @param strDate
     *            ��ʾ���ڵ��ַ���
     * @param dateFormat
     *            �����ַ��������ڱ�ʾ��ʽ���磺"yyyy-MM-dd HH:mm:ss"��
     * @return java.util.Date�������ڶ������ת��ʧ���򷵻�null��
     */
    public static java.util.Date strToUtilDate(String strDate, String dateFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        java.util.Date date = null;
        try {
            date = sf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * ��String�ַ���ת��Ϊjava.sql.Timestamp��ʽ����,�������ݿⱣ��
     *
     * @param strDate
     *            ��ʾ���ڵ��ַ���
     * @param dateFormat
     *            �����ַ��������ڱ�ʾ��ʽ���磺"yyyy-MM-dd HH:mm:ss"��
     * @return java.sql.Timestamp�������ڶ������ת��ʧ���򷵻�null��
     */
    public static java.sql.Timestamp strToSqlDate(String strDate, String dateFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        java.util.Date date = null;
        try {
            date = sf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
        return dateSQL;
    }

    /**
     * ��java.util.Date����ת��ΪString�ַ���
     *
     * @param date
     *            Ҫ��ʽ��java.util.Date����
     * @param strFormat
     *            �����String�ַ�����ʽ���޶����磺"yyyy-MM-dd HH:mm:ss"��
     * @return ��ʾ���ڵ��ַ���
     */
    public static String dateToStr(java.util.Date date, String strFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(strFormat);
        String str = sf.format(date);
        return str;
    }

    /**
     * ��java.sql.Timestamp����ת��ΪString�ַ���
     *
     * @param time
     *            Ҫ��ʽ��java.sql.Timestamp����
     * @param strFormat
     *            �����String�ַ�����ʽ���޶����磺"yyyy-MM-dd HH:mm:ss"��
     * @return ��ʾ���ڵ��ַ���
     */
    public static String dateToStr(java.sql.Timestamp time, String strFormat) {
        SimpleDateFormat df = new SimpleDateFormat(strFormat);
        String str = df.format(time);
        return str;
    }

    /**
     * ��java.sql.Timestamp����ת��Ϊjava.util.Date����
     *
     * @param time
     *            Ҫת����java.sql.Timestamp����
     * @return ת�����java.util.Date����
     */
    public static java.util.Date timeToDate(java.sql.Timestamp time) {
        return time;
    }

    /**
     * ��java.util.Date����ת��Ϊjava.sql.Timestamp����
     *
     * @param date
     *            Ҫת����java.util.Date����
     * @return ת�����java.sql.Timestamp����
     */
    public static java.sql.Timestamp dateToTime(java.util.Date date) {
        String strDate = dateToStr(date, "yyyy-MM-dd HH:mm:ss SSS");
        return strToSqlDate(strDate, "yyyy-MM-dd HH:mm:ss SSS");
    }

    /**
     * ���ر�ʾϵͳ��ǰʱ���java.util.Date����
     * @return  ���ر�ʾϵͳ��ǰʱ���java.util.Date����
     */
    public static java.util.Date nowDate(){
        return new java.util.Date();
    }

    /**
     * ���ر�ʾϵͳ��ǰʱ���java.sql.Timestamp����
     * @return  ���ر�ʾϵͳ��ǰʱ���java.sql.Timestamp����
     */
    public static java.sql.Timestamp nowTime(){
        return dateToTime(new java.util.Date());
    }
    
    public static java.util.Date sqldateToUtilDate(java.sql.Date sqlDate){
    	
    	return null;
    }
    
    
}