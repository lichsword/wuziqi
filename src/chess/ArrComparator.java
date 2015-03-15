package chess;

import java.util.Comparator;

public class ArrComparator implements Comparator<Object>{
    int column = 2;
    
    //µÝ¼õ
    int sortOrder = -1;
    
    public ArrComparator(){
    	
    }

	
	public int compare(Object o1, Object o2) {
		if(o1 instanceof int[] && o2 instanceof int[]){
			return sortOrder*(((int[]) o1)[column] - ((int[]) o2)[column]);
		}
		throw new IllegalArgumentException("param o1, o2 must int[].");
	}	
}
