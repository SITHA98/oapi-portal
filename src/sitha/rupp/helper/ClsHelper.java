package sitha.rupp.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;

import java.math.BigDecimal;

public class ClsHelper extends GenericDaSupport {
	private static String datePattern = "dd-MMM-yyyy";
	public static ArrayList<String> lst = new ArrayList<>();
	public static String active = "<span class=\"label label-success1\">Active</span>";
	public static String disabled = "<span class=\"label label-danger1\">Disabled</span>";
	public static String close = "<span class=\"label label-danger1\">Close</span>";
	public static String open = "<span class=\"label label-success1\">Open</span>";
	public static String deleted = "<span class=\"label label-danger1\">Deleted</span>";
	public static String pandding = "<span class=\"label label-warning1\">Pandding</span>";
	public static String remove = "<button type=\"button\" class=\"btn btn-labeled btn-danger btn-xs\">"
			+ "<span class=\"btn-label\"><i class=\"glyphicon glyphicon-remove\"></i></span>Remove</button>";

	public static String OS = System.getProperty("os.name").toLowerCase();
	public static String img_stamp="images/Stamp_LHI.png";
	public static String img_ceoSignature="images/CEO_SIGNATURE.png";
	public static String img_letter_head="images/Letterhead_A4.jpg";
	public static String img_ceo_kh_name="images/CEO_KH_NAME.png";
	
	public static Date trim(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR, 0);

		return calendar.getTime();
	}

	public static double round(double value, int places) {
		/*
		 * if (places < 0) throw new IllegalArgumentException(); BigDecimal bd =
		 * new BigDecimal(value); bd = bd.setScale(places,
		 * RoundingMode.HALF_UP); return bd.doubleValue();
		 */
		DecimalFormat df = new DecimalFormat("#.###");
		value = Double.parseDouble(df.format(value));
		// return value;
		BigDecimal bigDecimal = new BigDecimal(value);
		double roundedWithScale = Double.parseDouble(bigDecimal.setScale(places, BigDecimal.ROUND_HALF_UP) + "");
		return roundedWithScale;
	}

	public static String round1(double value, int places) {
		/*
		 * if (places < 0) throw new IllegalArgumentException(); BigDecimal bd =
		 * new BigDecimal(value); bd = bd.setScale(places,
		 * RoundingMode.HALF_UP); return bd.doubleValue();
		 */
		DecimalFormat df = new DecimalFormat("###,###.00");
		String val = df.format(value);
		return val;
	}

	public static Date getDefaultDate01011900() {
		Calendar defaultDate = Calendar.getInstance();
		defaultDate.set(1900, 00, 01);
		return defaultDate.getTime();
	}

	public static String getCurrenctYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		// get current year time with Date()
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String format3Digits(String number) {
		number = number + "";
		return number = ("000" + number).substring(number.length());
	}

	public static String format4Digits(String number) {
		number = number + "";
		return number = ("0000" + number).substring(number.length());
	}

	public static String format6Digits(String number) {
		number = number + "";
		return number = ("000000" + number).substring(number.length());
	}

	public static String format8Digits(String number) {
		number = number + "";
		return number = ("00000000" + number).substring(number.length());
	}

	public static Date getFormatDate(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(datePattern);
		// get current date time with Date()
		Date date = null;
		try {
			date = dateFormat.parse(sDate);
		} catch (ParseException e) {
			DateFormat dateFormat2 = new SimpleDateFormat(datePattern);
			try {
				date = dateFormat2.parse(sDate);
				System.out.println("Date after format:" + date);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return date;
	}

	public static String formatNumber(String pattern, int number) {
		DecimalFormat dateFormat = new DecimalFormat(pattern);
		// System.out.println(dateFormat.format(number));
		return dateFormat.format(number);
	}

	public static String formatNumber(double number) {
		DecimalFormat dateFormat = new DecimalFormat("###,##0.00");
		// System.out.println(dateFormat.format(number));
		return dateFormat.format(number);
	}

	public static String formatNumber3Digit(int number) {
		DecimalFormat dateFormat = new DecimalFormat("000");
		// System.out.println(dateFormat.format(number));
		return dateFormat.format(number);
	}

	public static String formatNumber(String number) {
		if (number == null)
			number = "0";
		double value = Double.parseDouble(number);
		DecimalFormat dateFormat = new DecimalFormat("###,##0.00");
		return dateFormat.format(value);
	}

	static private SimpleDateFormat dFormat;

	public static String formatDate(Date date) {
		if (date == null)
			return "";
		dFormat = new SimpleDateFormat(datePattern);
		return dFormat.format(date);
	}

	public static String formatDate(String sDate) {
		Date date;
		dFormat = new SimpleDateFormat(datePattern);
		try {
			System.out.println(sDate);
			date = new SimpleDateFormat(datePattern).parse(sDate);
			return dFormat.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Date format error!";
	}

	public static String getDayNameKh(Date date) {
		String s = "";
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 2:
			s = "Monday";
			// "áž…áž“áŸ’áž‘áŸ�";
			break;
		case 3:
			s = "Tuesday";
			// "Ã¡Å¾Â¢Ã¡Å¾â€žÃ¡Å¸â€™Ã¡Å¾â€šÃ¡Å¾Â¶Ã¡Å¾Å¡";
			break;
		case 4:
			s = "Wednesday";
			// "Ã¡Å¾â€“Ã¡Å¾Â»Ã¡Å¾â€™";
			break;
		case 5:
			s = "Thursday";
			// "Ã¡Å¾â€“Ã¡Å¸â€™Ã¡Å¾Å¡Ã¡Å¾Â Ã¡Å¾Å¸Ã¡Å¸â€™Ã¡Å¾â€�Ã¡Å¾ï¿½Ã¡Å¸â€™Ã¡Å¾ï¿½Ã¡Å¾Â·";
			break;
		case 6:
			s = "Friday";
			// "Ã¡Å¾Å¸Ã¡Å¾Â»Ã¡Å¾â‚¬Ã¡Å¸â€™Ã¡Å¾Å¡";
			break;
		case 7:
			s = "Saturday";
			// "Ã¡Å¾Å¸Ã¡Å¸â€¦Ã¡Å¾Å¡Ã¡Å¸Å’";
			break;
		case 1:
			s = "Sunday";
			// "Ã¡Å¾Â¢Ã¡Å¾Â¶Ã¡Å¾â€˜Ã¡Å¾Â·Ã¡Å¾ï¿½Ã¡Å¸â€™Ã¡Å¾â„¢";
			break;
		}
		return s;
	}

	public static int compareDate(Date date1, Date date2) {
		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth()
				&& date1.getDate() == date2.getDate()) {
			return 0;
		} else if (date1.getYear() < date2.getYear()
				|| (date1.getYear() == date2.getYear() && date1.getMonth() < date2.getMonth())
				|| (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth()
						&& date1.getDate() < date2.getDate())) {
			return -1;
		} else {
			return 1;
		}
	}

	public static String formatStr3Digits(String str) {
		return ("000" + str).substring(str.length());
	}

	public static String formatStr8Digits(String str) {
		return ("00000000" + str).substring(str.length());
	}

	public static int formatInt(String st) {
		return Integer.parseInt(st);
	}

	public static double formatDouble(String st) {
		return Double.parseDouble(st);
	}

	public static boolean isJoined() {
		boolean b = false;
		if (System.getProperty("user.name").trim().equals("SAMReaksmey".trim())) {
			if (isClient()) {
				b = true;
			} else {
				b = false;
			}
		}
		return b;
	}

	public static boolean isClient() {
		InetAddress ip;
		boolean help = false;
		try {

			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			System.out.println(sb.toString());
			if (ip.toString().equalsIgnoreCase("192.168.1.254")) {
				if (sb.toString().equalsIgnoreCase("00-50-56-C0-00-08")) {
					help = true;
				}
			}
		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();

		}
		return help;
	}

	public static boolean edition() {
		Runtime rt;
		Process pr;
		BufferedReader in;
		String line = "";
		String sysInfo = "";
		String edition = "";
		String fullOSName = "";
		final String SEARCH_TERM = "OS Name:";
		final String[] EDITIONS = { "Basic", "Home", "Professional", "Enterprise" };

		try {
			rt = Runtime.getRuntime();
			pr = rt.exec("SYSTEMINFO");
			in = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			// add all the lines into a variable
			while ((line = in.readLine()) != null) {
				if (line.contains(SEARCH_TERM)) // found the OS you are using
				{
					// extract the full os name
					fullOSName = line.substring(line.lastIndexOf(SEARCH_TERM) + SEARCH_TERM.length(),
							line.length() - 1);
					break;
				}
			}

			// extract the edition of windows you are using
			for (String s : EDITIONS) {
				if (fullOSName.trim().contains(s)) {
					edition = s;
				}
			}

			System.out.println("The edition of Windows you are using is " + edition);

		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		return false;
	}

	public static String Begin() {
		return " BEGIN SAVEPOINT start_tran; \n";
	}

	public static String End() {
		return " EXCEPTION \n " + " WHEN OTHERS THEN \n" + " ROLLBACK TO start_tran;\n" + " RAISE;\n" + " END; ";
	}

	public static String left(String input, int len) {
		return input.substring(0, len);
	}

	public static String right(String input, int len) {
		return input.substring(input.length() - len);
	}

	public static String mid(String input, int index, int len) {
		return input.substring(index - 1, index + len - 1);
	}

	public static String mid(String input, int index) {
		return input.substring(index - 1);
	}
	
	public static String getCurrentDateTime(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); //2015/10/26 12:10:39
		
		String dt = dateFormat.format(date).toString();
		return dt;
	}
	
	public static String generateOTP() {
		int aNumber = 0;
		aNumber = (int) ((Math.random() * 900000) + 100000);
		System.out.print((aNumber));
		
		return aNumber+"";
	}
	public static String ReplaceString(String str) {
		String output="";
		output=str.replace(" ", "");
		output=output.replace("/", "");
		output=output.replace("\"", "");
		output=output.replace("(", "");
		output=output.replace(")", "");
		output=output.replace("-", "");
		output=output.replace(":", "");
		output=output.replace("=", "");
		output=output.replace("*", "");
		output=output.replace(".", "");
		return output;
	}
	public static String getCurrentDateTimeToStringID() {
		Date date = new Date();  
        Timestamp sq=new Timestamp(date.getTime());  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss:SSS"); 
        formatter.format(sq);
        String s = ReplaceString(sq.toString());
        System.out.println(s);
        return s;
	}
	
	public static String PolicyNumber(String inputPolicyNumber) {
		int l=inputPolicyNumber.length();
		int l_7=l-7;
		String tail=inputPolicyNumber.substring(l_7,24);
		System.out.println("Tail:"+tail);
		//System.out.println("hi "+l_7);
		String n_a = inputPolicyNumber.substring(0, l_7);
		//System.out.println("new string "+n_a);
		String replace_n_a = n_a.replace("/", "");
		//System.out.println("replace_n_a:"+replace_n_a);
		String originalPolicyNumber = replace_n_a+tail;
		System.out.println("originalPolicyNumber:"+originalPolicyNumber);
		
		return originalPolicyNumber;
	}

	public static int checkOS() {
		int i=0;
		if (OS.indexOf("win") >= 0){
			return 0;			
		}else if(OS.indexOf("mac") >= 0||OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ||OS.indexOf("sunos")>=0) {
			return -1;
		}
	
		return i;
	}
	
	public static String getProductCode(String userInputPolicy) {
		//String p="P/01/HC00/20/0030/000/00";
		String s[]=userInputPolicy.split("/");
		//System.out.println(s[2]);
		return s[2];
	}
	
	
}
