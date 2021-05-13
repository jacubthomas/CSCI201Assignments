package jharring_CSCI201L_Assignment1;

import java.text.Collator;
import java.util.Comparator;

public class zaCompare implements Comparator<Company> {

	public zaCompare() {}

	@Override
	public int compare(Company a, Company b) {
		Collator col = Collator.getInstance(); 
		int result = col.compare(a.getName(), b.getName());
		if(result < 0)
			return 1;
		else if(result > 0)
			return -1;
		else 
			return 0;
	}

}
