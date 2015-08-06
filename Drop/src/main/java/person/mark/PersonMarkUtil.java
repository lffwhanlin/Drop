package person.mark;

import java.util.Random;

public class PersonMarkUtil {
	
	private static String[] marks = new String[]{"运动","美食","电影","汽车","看书","旅游","美女","IT"};
	private static Random random = new Random();
	
	public static String randomGeneratePersonMark(){
		String mark = "";
		int count = random.nextInt(marks.length);
		for(int i=0;i<count;i++){
			String ele = marks[random.nextInt(marks.length-1)];
			if(mark.indexOf(ele)!=-1){
				continue;
			}else{
				mark+=ele;
				mark+=",";
			}
		}
		if(mark.length()>1){
			mark=mark.substring(0, mark.length()-1);
		}
		return mark;
	}
	
	public static void main(String[] args){
		System.out.println(randomGeneratePersonMark());
	}

}
