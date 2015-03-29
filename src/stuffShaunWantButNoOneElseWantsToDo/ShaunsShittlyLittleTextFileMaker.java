package stuffShaunWantButNoOneElseWantsToDo;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import map.GenerateNodes;

public class ShaunsShittlyLittleTextFileMaker {
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		GenerateNodes nodes = new GenerateNodes("mit.edu", 110);
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Test"));
		out.writeObject(nodes);

	}
}
