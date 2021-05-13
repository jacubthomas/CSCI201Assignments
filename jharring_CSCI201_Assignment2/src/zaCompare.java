import java.text.Collator;
import java.util.Comparator;

public class zaCompare implements Comparator<Company> {

	public zaCompare() {}

	@Override
	public int compare(Company a, Company b) {
		Collator col = Collator.getInstance(); 
		int result = col.compare(a.get_Name(), b.get_Name());
		if(result < 0)
			return 1;
		else if(result > 0)
			return -1;
		else 
			return 0;
	}

}
