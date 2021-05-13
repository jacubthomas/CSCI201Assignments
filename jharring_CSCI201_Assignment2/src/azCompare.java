import java.text.Collator;
import java.util.Comparator;

public class azCompare implements Comparator<Company> {

	public azCompare() {}

	@Override
	public int compare(Company a, Company b) {
		
		Collator col = Collator.getInstance(); 
		return col.compare(a.get_Name(), b.get_Name());
	}

}
