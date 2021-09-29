package application;
import java.util.HashMap;
public class Booking {
	private Formation formation;
	private int filiale;
	private int sportart;
	
	private int ecoHours;
	private int primeHours;
	
	private int basicAmount;
	
	private HashMap<Integer, String> filialeKeys = new HashMap<Integer, String>();
	private HashMap<Integer, String> sportartKeys = new HashMap<Integer, String>();
	
	public Booking(Formation formation, int filiale, int sportart, int ecoHours, int primeHours) {
		filialeKeys.put(0, "Lüstringen");
		filialeKeys.put(1, "Nahne");
		filialeKeys.put(2, "Oldenburg");
		
		sportartKeys.put(0, "Badminton");
		sportartKeys.put(1, "Squash");
		sportartKeys.put(2, "Tennis");
		sportartKeys.put(3, "Tischtennis");
		
		this.formation = formation;
		this.filiale = filiale;
		this.sportart = sportart;
		this.ecoHours = ecoHours;
		this.primeHours = primeHours;
	}
	
	//Null-Booking
	public Booking() {
		filialeKeys.put(0, "Lüstringen");
		filialeKeys.put(1, "Nahne");
		filialeKeys.put(2, "Oldenburg");
		
		sportartKeys.put(0, "Badminton");
		sportartKeys.put(1, "Squash");
		sportartKeys.put(2, "Tennis");
		sportartKeys.put(3, "Tischtennis");
	}
	
	public int getBasicAmount() {
		switch(formation.getSize()) {
		case 2: 
			return 12;
		case 3:
			return 9;
		case 4: 
			return 8;
		case 5: 
			return 8;
		case 6: 
			return 8;
		case 7: 
			return 8;
		case 8: 
			return 8;
		default:
			return -1;		
		}
	}
	
	//Getter
	public Formation getFormation() {
		return formation;
	}
	
	public int getFiliale() {
		return filiale;
	}
	
	public String getFilialeAsString() {
		return filialeKeys.get(filiale);
	}
	
	public int getSportart() {
		return sportart;
	}
	
	public String getSportartAsString() {
		return sportartKeys.get(sportart);
	}
	
	public int getEcoHours() {
		return ecoHours;
	}
	
	public int getPrimeHours() {
		return primeHours;
	}
	
	//Setter
	public void setFormation(Formation formation) {
		this.formation = formation;
	}
	
	public void setFiliale(int filiale) {
		this.filiale = filiale;
	}
	
	public void setSportart(int sportart) {
		this.sportart = sportart;
	}
	
	public void setEcoHours(int ecoHours) {
		this.ecoHours = ecoHours;
	}
	
	public void setPrimeHours(int primeHours) {
		this.primeHours = primeHours;
	}
}
