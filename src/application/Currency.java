package application;
import java.text.DecimalFormat;

public class Currency {
	public String currencyAsString(float f) {
		DecimalFormat d = new DecimalFormat("##.00 EUR");
		String asString = d.format(f);
		if (asString.charAt(0) == ',') {
			asString = "0"+asString;
		}
		return asString; 
	}
	
	public String currencyAsString(double f) {
		DecimalFormat d = new DecimalFormat("##.00 EUR");
		String asString = d.format(f);
		if (asString.charAt(0) == ',') {
			asString = "0"+asString;
		}
		return asString; 
	}
	
	public float currencyAsFloat (String s) {
		String[] arr = s.replace('.', ',').split(",");
		try {
			return Float.parseFloat(arr[0]) + Float.parseFloat(arr[1].substring(0, 1)) / 10;
		} catch(Exception e) {
			System.out.println("Das Umwandeln eines Waehrungs-Strings in eine float-Zahl ist fehlgeschlagen: "+e.getMessage());
			return -1.0f;
		}
	}
	
	public double currencyAsDouble(String s) {
		double d = 0;
		String[] arr = s.replace('.', ',').split(",");
		try {
			d += Double.parseDouble(arr[0]);
			String[] arr2 = arr[1].split(" ");
			d += Double.parseDouble(arr2[0]) / 100;
			return d;
		} catch(NumberFormatException e) {
			System.out.println("Das Umwandeln des Strings in eine double-Variable ist fehlgeschlagen: "+e.getMessage());
			return -1.0;
		}
	}
}
