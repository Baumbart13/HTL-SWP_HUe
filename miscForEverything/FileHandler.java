package miscForEverything;

import java.io.*;
import java.util.LinkedList;

public class FileHandler {
	public static String[] readFile(String file)throws IOException {
		LinkedList<String> inputs = new LinkedList<String>();

		BufferedReader br = new BufferedReader(new FileReader(file));
		try{
			String line = br.readLine();

			while(line != null){
				inputs.add(line);
				line = br.readLine();
			}
		}finally{
			br.close();
		}

		return inputs.toArray(new String[inputs.size()]);
	}

	public static void writeToFile(String file, String[] strings) throws IOException{

		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		try{
			for(int i = 0; i < strings.length; ++i){
				bw.write(strings[i]);
				bw.newLine();
			}
		}finally{
			bw.close();
		}
	}
}
