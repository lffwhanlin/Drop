package person.name;

import java.io.InputStream;

public class NameRandomGenerator {
	
	public static void main(String[] args){
		
		InputStream in = NameRandomGenerator.class.getResourceAsStream("baijiaxing.txt");
		
		System.out.println(in);
		
		
	}
	
	

}
