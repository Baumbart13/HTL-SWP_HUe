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

				if(s.equalsIgnoreCase("-insert")){
					addInfo(desktopPath + fileName);
					return;
				}else if(s.equalsIgnoreCase("-default")){
					loadingPath = desktopPath + fileName;
					savingPath = desktopPath + outputFileName;
					break;
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

	private static void addInfo(String path){
		BufferedImage img = FileHandler.loadImage(path);

		char[] info = "Transformed by Baumbart13".toCharArray();

		System.out.println("Start adding info...");
		/*int infoIterator = info.length-1;
		for(int i = img.getWidth()-1; i > 0 && infoIterator > 0; --i, --infoIterator){
			img.setRGB(i, img.getHeight()-1, info[infoIterator]);
		}*/

		FileHandler.writeImage(img, path);

		try{
			// read file
			BufferedReader br = new BufferedReader(new FileReader(path));
			String s = "";
			StringBuilder sBuilder = new StringBuilder();
			while((s = br.readLine()) != null){
				sBuilder.append(s);
			}
			br.close();

			// write info to file
			sBuilder.replace(sBuilder.length()-info.length-1, sBuilder.length()-info.length-1, info.toString());

			// write file
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(sBuilder.toString());
			bw.close();

		}catch(IOException e){
			e.printStackTrace();
		}

		StringBuilder debug = new StringBuilder();
		int i = 0;
		for(i = 600; i < img.getWidth(); ++i){
			debug.append(img.getRGB(i, img.getHeight()-1));
		}

		System.out.println("Added info");
		UI.waitForKeypress();
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