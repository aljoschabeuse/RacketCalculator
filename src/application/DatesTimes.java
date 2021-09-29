package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatesTimes {
	public String weekday() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String asString = ((String) dtf.format(LocalDateTime.now())).split(" ")[0];
		Integer year = Integer.parseInt(asString.split("/")[0]);
		Integer month = Integer.parseInt(asString.split("/")[1]);
		Integer day = Integer.parseInt(asString.split("/")[2]);
		int asNumber = (year - 2000) * 365;
		asNumber += (int) Math.ceil((year - 2000) / 4f);
		if (month > 2) {
			asNumber++;
		}
		switch (month) {
		case 2: 
			asNumber += 31;
			break;
		case 3:
			asNumber += 59;
			break;
		case 4:
			asNumber += 90;
			break;
		case 5:
			asNumber += 120;
			break;
		case 6: 
			asNumber += 151;
			break;
		case 7:
			asNumber += 181;
			break;
		case 8:
			asNumber += 212;
			break;
		case 9:
			asNumber += 243;
			break;
		case 10:
			asNumber += 273;
			break;
		case 11:
			asNumber += 304;
			break;
		case 12:
			asNumber += 334;
			break;
		default:
			asNumber += 0;
		}
		asNumber += day;
		
		switch (asNumber % 7) {
		case 1: 
			return "Samstag";
		case 2: 
			return "Sonntag";
		case 3: 
			return "Montag";
		case 4:
			return "Dienstag";
		case 5:
			return "Mittwoch";
		case 6: 
			return "Donnerstag";
		default:
			return "Freitag";
		}
	}
	
	public String date_yyyyMMdd_HHmmss() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	    return((String) dtf.format(LocalDateTime.now()));
	}
	
	public String date_yyyyMMdd_HHmm() {
		DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	    return (String) dtf4.format(LocalDateTime.now());
	}
	
	public String date_HHmm() {
		DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	    return ((String) dtf4.format(LocalDateTime.now())).substring(11,16);
	}
	
	public String date_ddMMyyyy() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String[] pieces = dtf.format(LocalDateTime.now()).split("/");
	    return pieces[2].substring(0, 2)+"."+pieces[1]+"."+pieces[0];
	}
	
	/*
    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
    System.out.println("yy/MM/dd HH:mm:ss-> "+dtf2.format(LocalDateTime.now()));

    DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy/MMMM/dd HH:mm:ss");
    System.out.println("yyyy/MMMM/dd HH:mm:ss-> "+dtf3.format(LocalDateTime.now()));
    
    DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm");
    System.out.println("yyyy/MM/dd hh:mm:ss-> "+dtf5.format(LocalDateTime.now()));
    */
	
	public boolean checkTimeFormat_HHmm(String time) {
		time = correctUserInput_TimeFormat_HHmm(time);
		int hour = 0;
		int minute = 0;
		String[] subTime = time.split(":");
		try {
			 hour = Integer.parseInt(subTime[0]);
		} catch(NumberFormatException e) {
			return false;
		}
		try {
			minute = Integer.parseInt(subTime[1]);
		} catch(Exception e) {
			return false;
		}
		
		if (hour < 0 || hour > 23) {
			return false;
		}
		
		if (minute < 0 || minute > 59) {
			return false;
		}
		
		return true;
	}
	
	public String correctUserInput_TimeFormat_HHmm(String toCorrect) {
		String correction = toCorrect.replace(".", ":").replace(",", ":");
		if (correction.length() != 5) {
			if (correction.length() == 4) {
				if (correction.split(":").length == 2) {
					correction = "0"+correction;
				} else {
					correction = correction.substring(0,2)+":"+correction.substring(2,4);
				}
			}
		}
		if (correction.startsWith("24")) {
			correction = "00"+correction.split("4")[1];
		}
		return correction;
	}
}