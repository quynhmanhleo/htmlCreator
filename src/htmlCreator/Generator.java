package htmlCreator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;

public class Generator {
	private static final String TEAM = "NONAME";
	private ArrayList<Person> list = new ArrayList<Person>();
	private String DICTORY;
	private String DESTINATION = ".\\images\\";
	private String HOME = "index.html";
	
	private void convertData(String[] a) throws IOException{
		Person person = new Person();
		person.id = a[0];
		person.name = a[1];
		person.field = a[2];
		person.year = Integer.parseInt(a[3]);
		person.birth = a[4];
		person.friends = a[7].split(";");
		person.decription = a[8];
		person.favorites = new String[a.length - 9];
		for (int i = 0; i < person.favorites.length; ++i)
			person.favorites[i] = a[i + 9];
		
		copyPicture(DICTORY + a[5], DESTINATION + person.id + ".jpg");
		copyPicture(DICTORY + a[6], DESTINATION + person.id + "_t.jpg");
		
		list.add(person);
	}
	
	private void copyPicture(String string, String string2) throws IOException {
		System.out.println(string + " " + string2);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(string));
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(string2));
		byte[] buffer = new byte[8192];
		int numChars;
        while ( (numChars = bufferedInputStream.read(  buffer, 0, buffer.length ) ) != -1) 
            bufferedOutputStream.write( buffer, 0, numChars );
		bufferedInputStream.close();
		bufferedOutputStream.close();
	}

	private void readData(String csv) throws IOException{
		BufferedReader inputStream = new BufferedReader(new FileReader(csv));
		String line;
		while ((line = inputStream.readLine()) != null){
			String[] temp = line.split(",");
			convertData(temp);
		}
		inputStream.close();
		
		for (int i = 0; i < list.size(); ++i){
			Person person1 = list.get(i);
			for (int j = 0; j < person1.friends.length; ++j){
				for (int k = 0; k < list.size(); ++k){
					Person person2 = list.get(k);
					if (person1.friends[j].compareTo(person2.id) == 0){
						list.get(i).friends[j] = String.valueOf(k);
					}
				}
				
			}
		}
		
	}
	
	Generator(String csv, String directory) throws IOException{
		DICTORY = directory + "\\";
		readData(csv);
		writeHtml();
	}

	private void writeHtml() throws IOException {
		
		//index.html
		StringBuilder s = new StringBuilder();
		DataInputStream dataInputStream = new DataInputStream(new FileInputStream("1.txt"));
		DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("index.html"));
		String buffer;
		while ((buffer = dataInputStream.readLine()) != null)
			s.append(buffer + "\n");
		dataOutputStream.writeBytes(String.format(s.toString(), TEAM));
		
		s.setLength(0);
		dataInputStream.close();
		dataInputStream = new DataInputStream(new FileInputStream("2.txt"));
		while (((buffer = dataInputStream.readLine())) != null){
			s.append(buffer + "\n");
		}
		
		for (int i = 0; i < list.size(); ++i){
			Person person = list.get(i);
			dataOutputStream.writeBytes(String.format(s.toString(), person.id, person.id, person.id, person.name, person.field, person.year));
		}	
		
		s.setLength(0);
		dataInputStream.close();
		dataInputStream = new DataInputStream(new FileInputStream("3.txt"));
		while ((buffer = dataInputStream.readLine()) != null){
			s.append(buffer.toString());
		}
		dataOutputStream.writeBytes(String.format(s.toString(), TEAM));
		dataInputStream.close();
		dataOutputStream.close();
		//personal
		
		for (int i = 0; i < list.size(); ++i){
			Person person = list.get(i);
			dataInputStream = new DataInputStream(new FileInputStream("4.txt"));
			dataOutputStream = new DataOutputStream(new FileOutputStream(String.format("%s.html", person.id)));
			s.setLength(0);
			while ((buffer = dataInputStream.readLine()) != null)
				s.append(buffer + "\n");
			dataOutputStream.writeBytes(String.format(s.toString(), TEAM, person.name, person.id, person.name, person.id, person.id, person.id, person.birth, person.field, person.year, person.decription));
			
			for (int j = 0; j < person.favorites.length; ++j)
				dataOutputStream.writeBytes(String.format("<li>%s</li>\n", person.favorites[j]));
			
			s.setLength(0);
			dataInputStream.close();
			dataInputStream = new DataInputStream(new FileInputStream("6.txt"));
			while ((buffer = dataInputStream.readLine()) != null)
				s.append(buffer + "\n");
			dataOutputStream.writeBytes(s.toString());
			dataInputStream.close();
			dataInputStream = new DataInputStream(new FileInputStream("7.txt"));
			s.setLength(0);
			while ((buffer = dataInputStream.readLine()) != null){
				s.append(buffer);
			}
			for (int j = 0; j < person.friends.length; ++j){
				Person friend = list.get(Integer.parseInt(person.friends[j]));
				dataOutputStream.writeBytes(String.format(s.toString(), friend.id, friend.id, friend.id, friend.name, friend.field, friend.year));
			}
			
			s.setLength(0);
			dataInputStream.close();
			dataInputStream = new DataInputStream(new FileInputStream("8.txt"));
			while ((buffer = dataInputStream.readLine()) != null){
				s.append(buffer);
			}
			dataOutputStream.writeBytes(String.format(s.toString(), TEAM));
			dataInputStream.close();
			dataOutputStream.close();
		}
		dataOutputStream.close();
	}
}
