package date2020_05_28_ASCII_Art.src;

import java.awt.image.*;
import java.io.*;

import miscForEverything.UI;
import static date2020_05_28_ASCII_Art.src.ASCIIConverter.*;

public class ASCIIArt{
	private final static String desktopPath = System.getProperty("user.home") + "\\Desktop\\";
	private static String fileName = "app.bmp";
	private final static String outputFileName = "app2.bmp";
	private final static String outputASCIIFileName = "app2.txt";

	private static String askForLoadedFile(){

		File path = new File("");

		// as long as no guilty file has been chosen
		do{
			System.out.println("Please enter the Path of your file, you want to convert: ");
			path = new File(UI.readString());
		}while(!(path.exists()) && path.getName().toLowerCase().endsWith(".bmp"));

		return path.getAbsolutePath();
	}

	private static String askForSavedFile(){
		System.out.println("Please enter, where the converted image should be saved: ");

		return UI.readString();
	}

	public static void main(String[] args) {

		String loadingPath = "";
		String savingPath = "";

		if(args.length != 0){
			for(String s : args) {

				if (s.equalsIgnoreCase("-default")) {
					loadingPath = desktopPath + fileName;
					savingPath = desktopPath + outputFileName;
				}

			}
		} else {
			loadingPath = askForLoadedFile();
			savingPath = askForSavedFile();
		}

		BufferedImage img = rgbToGreyscale(FileHandler.loadImage(loadingPath));

		StringBuilder asciiImage = greyscaleToASCII(img);

		FileHandler.writeASCIIImage(asciiImage, savingPath);
	}

	/* Different project
	private static void addExtraInformation(File file, String info) throws IOException{

		LinkedList<Integer> list = new LinkedList<Integer>();
		readFileToList(file, list);

		// convert info to Integer
		Integer[] infoAsInteger = new Integer[info.length()];
		for(int i = 0; i < infoAsInteger.length; ++i){

		}

		// insert the info
		for(int i = list.size(); i > (list.size() - infoAsInteger.length); --i){

		}

	}

	private static void readFileToList(File file, java.util.List<Integer> list) throws IOException{
		Integer value = 0;
		BufferedReader br = new BufferedReader(new FileReader(file));
		while((value = br.read()) != null){
			list.add(value);
		}
	}
	*/
}