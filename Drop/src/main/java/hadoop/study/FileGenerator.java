package hadoop.study;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import person.mark.PersonMarkUtil;
import person.name.NameRandomGenerator;

public class FileGenerator {

	public static void main(String[] args) throws IOException {
		
		File file = new File("e://foolish-bird.txt");
		file.createNewFile();
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		Random random = new Random();
		int i = 0;
		while(i<5000){
			String line = NameRandomGenerator.generateName()+"\t"+PersonMarkUtil.randomGeneratePersonMark()+"\t"+random.nextInt(100);
			out.write(line);
			out.newLine();
			i++;
		}
		out.flush();
		out.close();

	}

}
