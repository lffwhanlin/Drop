package drop.everything.hl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;

import person.mark.PersonMarkUtil;
import person.name.NameRandomGenerator;

public class DataUtil {
	
	public static void main(String[] args){
		
	}
	
	public static List<String> productPerson(){
		List<String> persons = new ArrayList<String>();
    	for(int i=0;i<1000;i++){
    		JSONObject object = new JSONObject();
    		object.put("id", UUID.randomUUID().toString());
    		object.put("name",NameRandomGenerator.generateName());
    		object.put("age", new Random().nextInt(80));
    		object.put("mark", PersonMarkUtil.randomGeneratePersonMark());
    		persons.add(object.toJSONString());
    	}
    	return persons;
	}


}
