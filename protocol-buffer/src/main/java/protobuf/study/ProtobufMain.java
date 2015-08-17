package protobuf.study;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import protobuf.study.BookInfo.Book;
import protobuf.study.BookInfo.Book.Builder;

public class ProtobufMain {

	public static void main(String[] args) throws Exception {
		
		
		write();
		
		read();

	}
	
	public static void write() throws Exception{
		
		Builder builder = BookInfo.Book.newBuilder();
		
		builder.setId(10001);
		
		builder.setName("Mysql¸ßÐÔÄÜ");
		
		builder.setPrice(58.68);
		
		Book book = builder.build();
		
		File file = new File("E://book.dat");
		
		file.createNewFile();
		
		FileOutputStream out = new FileOutputStream(file);
		
		book.writeTo(out);
	}
	
	public static void read() throws Exception{
		
		File file = new File("E://book.dat");
		
		FileInputStream in = new FileInputStream(file);
		
		Book book = Book.parseFrom(in);
		
		System.out.println(book.getId()+"\t"+book.getName()+"\t"+book.getPrice());
		
	}

}
