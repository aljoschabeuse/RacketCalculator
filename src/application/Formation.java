package application;
import java.util.ArrayList;
public class Formation {
	ArrayList<Customer> list = new ArrayList<Customer>();
	
	public Formation (ArrayList<Customer> list) {
		this.list = list;
	}
	
	public ArrayList<Customer> getList() {
		return list;
	}
	
	public void enlargeList(Customer c) {
		list.add(c);
	}
	
	public void shortenList() {
		list.remove(list.size() - 1);
	}
	
	public void shortenList(int index) {
		list.remove(index);
	}
	
	public void shortenList(String name) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(name)) {
				list.remove(i);
				break;
			}
		}
	}
	
	public int getSize() {
		return list.size();
	}
}
