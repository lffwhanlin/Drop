package person.name;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NameRandomGenerator {
	
	private static List<String> index = null;
	
	private static Random random = null;
	
	static {
		index = new ArrayList<String>();
		
		random = new Random();
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		System.out.println(generateName());
	}
	
	/**
	 * 2015年8月6日
	 * 下午3:06:13
	 * TODO 姓氏从文件中加载到内存中
	 * @throws IOException 
	 */
	public static void init() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(NameRandomGenerator.class.getClassLoader().getResourceAsStream("baijiaxing.txt"),"UTF-8"));
		String line = null;
		while((line=reader.readLine())!=null){
			String[] eles = line.split(" ");
			if(eles!=null&&eles.length>0){
				for(String ele : eles){
					index.add(ele);
				}
			}
		}
		reader.close();
	}
	
	
	public static String generateName(){
		//u4e00-u9fa5  UTF-8字符集中中文的编码范围  
		//编码范围最大的汉字和最小的汉字之间的差为20901,但编码靠后的子都是比较复杂的，常用的汉字选为编码靠前的1000字。
		int i = random.nextInt(index.size());
		String name = ""+index.get(i);
		name += (char)('\u4e00'+random.nextInt(500));
		name += (char)('\u4e00'+random.nextInt(500));
		return name;
	}
	

}
