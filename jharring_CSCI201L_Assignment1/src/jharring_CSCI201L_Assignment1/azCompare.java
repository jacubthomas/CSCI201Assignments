package jharring_CSCI201L_Assignment1;

import java.text.Collator;
import java.util.Comparator;

public class azCompare implements Comparator<Company> {

	public azCompare() {}

	@Override
	public int compare(Company a, Company b) {
		
		Collator col = Collator.getInstance(); 
		return col.compare(a.getName(), b.getName());
	}

}
