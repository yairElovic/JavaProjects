package App;

import java.util.*;

public class SalableProductsComparator implements Comparator<SalableProduct>{
	private boolean sortByName;
	private boolean reverseOrder;
	
	public SalableProductsComparator(boolean sortByName, boolean reverseOrder) {
		this.sortByName = sortByName;
		this.reverseOrder = reverseOrder;
	}
	
	/**
	 * overrides the comare method in comparator to sort based on user entered criteria 
	 */
	  @Override
	    public int compare(SalableProduct p1, SalableProduct p2) {
		  int compareNum;
		  
		  if(sortByName)
			 compareNum = p1.getName().compareToIgnoreCase(p2.getName());
		  else
			  compareNum = Double.compare(p1.getPrice(), p2.getPrice());
		  
		  if(reverseOrder)
			  compareNum = -compareNum;
		  
		  return compareNum;
	  }
}
