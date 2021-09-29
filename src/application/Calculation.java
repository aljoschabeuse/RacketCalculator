package application;
import java.util.ArrayList;
public class Calculation {
	ArrayList<Float> floatPrices = new ArrayList<Float>();
	ArrayList<Double> doublePrices = new ArrayList<Double>();
	public ArrayList<Float> getPrices (Booking b) {
		floatPrices = new ArrayList<Float>();
		if (b.getEcoHours() + b.getPrimeHours() == 0) {
			for (int i = 0; i < b.getFormation().getSize(); i++) {
				floatPrices.add(0.0f);
			}
		} else {
			for (int i = 0; i < b.getFormation().getSize(); i++) {
				b.getFormation().getList().get(i).setAmount(sum(b, b.getFormation().getList().get(i)));
			}
			if (b.getFormation().getSize() > 4) {
				correctur32(b);
			}
			for (int i = 0; i < b.getFormation().getSize(); i++) {
				if (b.getFormation().getList().get(i).getAmount() < 0) {
					floatPrices.add(0.0f);
				} else {
					floatPrices.add(b.getFormation().getList().get(i).getAmount());
				}
			}
		}
		return floatPrices;
	}
	
	public ArrayList<Double> getPricesAsDouble (Booking b) {
		doublePrices = new ArrayList<Double>();
		if (b.getFormation().getSize() > 4) {
			correctur32(b);
		} else {
			for (int i = 0; i < b.getFormation().getSize(); i++) {
				b.getFormation().getList().get(i).setAmount(sum(b, b.getFormation().getList().get(i)));
			}
		}
		
		for (int i = 0; i < b.getFormation().getSize(); i++) {
			if (b.getFormation().getList().get(i).getAmount() < 0.0) {
				doublePrices.add(0.0);
			} else {
				doublePrices.add((double) b.getFormation().getList().get(i).getAmount());
			}
		}
		return doublePrices;
	}
	
	public void correctur32(Booking b) {
		int hours = b.getPrimeHours() + b.getEcoHours();
		double dividedSum = hours * 32.0 / b.getFormation().getSize();
		
		for (int i = 0; i < b.getFormation().getSize(); i++) {
			double individualSum = dividedSum;
			if (b.getFormation().getList().get(i).getMember() && !b.getFormation().getList().get(i).getChild()) {
				if (hours > 1) {
					individualSum -= 24;
				} else {
					individualSum -= 12;
				}
			} else if (b.getFormation().getList().get(i).getHansefit()) {
				individualSum -= 12;
			} else if (b.getFormation().getList().get(i).getStudent() && dividedSum / (double) hours > 6) {
				//individualSum -= (dividedSum / (double) hours - 6) * b.getEcoHours();
			} else if (b.getFormation().getList().get(i).getChild() && dividedSum / (double) hours > 5) {
				//individualSum -= (dividedSum / (double) hours - 5) * hours;
			}
			if (individualSum < 0) {
				individualSum = 0;
			}
			b.getFormation().getList().get(i).setAmount((float) individualSum);
		}
		/*
		float amount = 0;
		for (int i = 0; i < booking.getFormation().getSize(); i++) {
			amount += booking.getFormation().getList().get(i).getAmount();
		}
		if (amount > (booking.getEcoHours() + booking.getPrimeHours()) * 32 && booking.getSportart() != 2) {
			for (int i = 0; i < booking.getFormation().getSize(); i++) {
				booking.getFormation().getList().get(i).setAmount((
						booking.getEcoHours() + booking.getPrimeHours()) * 32.0f / booking.getFormation().getSize());
				if (booking.getFormation().getList().get(i).getHansefit()) {
					booking.getFormation().getList().get(i).setAmount(
							booking.getFormation().getList().get(i).getAmount() - 12);
				}
				if (booking.getFormation().getList().get(i).getMember()) {
					booking.getFormation().getList().get(i).setAmount(0.0f);
				}
			}
		}
		*/
	}
	
	public float sum(Booking booking, Customer customer) {
		ArrayList<Float> amounts = new ArrayList<Float>();
		
		float amount = booking.getBasicAmount();
		
		int ecoHours = booking.getEcoHours();
		int primeHours = booking.getPrimeHours();
		int hours = ecoHours + primeHours;
		
		// 1. Stunde
		amounts.add((float) booking.getBasicAmount());
		if (customer.getMember() || customer.getHansefit()) {
			amounts.set(0, 0.0f);
		}
		if (customer.getStudent() && ecoHours > 0) {
			if (amounts.get(0) == 12) {
				amounts.set(0, 8f);
			} else if (amounts.get(0) == 9) {
				amounts.set(0, 7f);
			} else if (amounts.get(0) == 8) {
				amounts.set(0, 6f);
			}
		}
		if (customer.getChild()) {
			amounts.set(0, 5f);
		}
		if (customer.getTennisWA() && booking.getSportart() == 2) {
			amounts.set(0, 20.0f / booking.getFormation().getSize());
		}
		if (customer.getTennisJS() && booking.getSportart() == 2) {
			amounts.set(0, 16.0f / booking.getFormation().getSize());
		}
		if (customer.getFamilie()) {
			amounts.set(0,  29.5f / booking.getFormation().getSize());
		}
		if (booking.getSportart() == 2 && !(customer.getTennisWA() || customer.getTennisJS())) {
			amounts.set(0, amounts.get(0) + 3.0f / booking.getFormation().getSize());
		}
		// 2. Stunde
		if (hours > 1) {
			amounts.add((float) booking.getBasicAmount());
			
			if (customer.getMember()) {
				if (booking.getFormation().getSize() >= 4) {
					amounts.set(1, 0.0f);
				} else {
					if (booking.getSportart() == 2) {
						amounts.set(1, (float) booking.getBasicAmount());
					} else {	
						amounts.set(1, 5.0f);
					}
				}
			}
			if (customer.getStudent() && ecoHours > 1) {
				if (amounts.get(1) == 12) {
					amounts.set(1, 8f);
				} else if (amounts.get(1) == 9) {
					amounts.set(1, 7f);
				} else if (amounts.get(1) == 8) {
					amounts.set(1, 6f);
				}
			}
			if (customer.getChild()) {
				amounts.set(1, 5f);
			}
			if (customer.getTennisWA() && booking.getSportart() == 2) {
				amounts.set(1, 20.0f / booking.getFormation().getSize());
			}
			if (customer.getTennisJS() && booking.getSportart() == 2) {
				amounts.set(1, 16.0f / booking.getFormation().getSize());
			}
			if (customer.getFamilie()) {
				amounts.set(1,  20.0f / booking.getFormation().getSize());
			}
			
			if (booking.getSportart() == 2 && !(customer.getTennisWA() || customer.getTennisJS())) {
				amounts.set(1, amounts.get(1) + 3.0f / booking.getFormation().getSize());
			}
		}
		
		// Jede weitere Stunde
		for (int i = 2; i < hours; i++) {
			amounts.add((float) booking.getBasicAmount());
			if (customer.getMember() && booking.getSportart() != 2) {
				amounts.set(i, 5.0f);
			}
			if (customer.getStudent() && ecoHours > i) {
				if (amounts.get(i) == 12) {
					amounts.set(i, 8f);
				} else if (amounts.get(i) == 9) {
					amounts.set(i, 7f);
				} else if (amounts.get(i) == 8) {
					amounts.set(i, 6f);
				}
			}
			if (customer.getChild()) {
				amounts.set(i, 5f);
			}
			if (customer.getTennisWA() && booking.getSportart() == 2) {
				amounts.set(i, 20.0f / booking.getFormation().getSize());
			}
			if (customer.getTennisJS() && booking.getSportart() == 2) {
				amounts.set(i, 16.0f / booking.getFormation().getSize());
			}
			if (customer.getFamilie()) {
				if (i % 2 == 0) {
					amounts.set(i,  29.5f / booking.getFormation().getSize());
				} else {
					amounts.set(i,  20.0f / booking.getFormation().getSize());
				}
			}
			
			if (booking.getSportart() == 2 && !(customer.getTennisWA() || customer.getTennisJS())) {
				amounts.set(i, amounts.get(i) + 3.0f / booking.getFormation().getSize());
			}
		}
		
		amount = 0.0f;
		for (int i = 0; i < amounts.size(); i++) {
			amount += amounts.get(i);
		}
		customer.setAmount(amount);
		return amount;
	}
}
