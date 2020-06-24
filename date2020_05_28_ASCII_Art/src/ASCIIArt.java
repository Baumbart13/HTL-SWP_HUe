package date2020_05_28_ASCII_Art.src;

import date2020_05_28_ASCII_Art.res.ASCIIColor;
import date2020_05_28_ASCII_Art.res.ASCIIGreyscaleValue;
import miscForEverything.UI;
import net.sf.image4j.codec.bmp.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.*;
import java.util.LinkedList;

public class ASCIIArt{
	private final static String desktopPath = System.getProperty("user.home") + "/Desktop/";
	private static String fileName = "app.bmp";
	private final static String outputFileName = "app2.bmp";
	private final static String outputASCIIFileName = "app2.txt";

	private static StringBuilder greyscaleToASCII(BufferedImage img){
		StringBuilder asciiOutput = new StringBuilder(img.getHeight() + img.getWidth());
		//char[] asciiOutput = new char[img.getHeight() + img.getWidth()+1];
		int pixelCounter = 0;

		for(int y = 0; y < img.getHeight(); ++y){
			for(int x = 0; x < img.getWidth(); ++x){
				//asciiOutput[pixelCounter] = chooseClosestASCII(new Color(img.getRGB(x, y)));
				asciiOutput.append(chooseClosestASCII(new Color(img.getRGB(x, y))));
			}
			asciiOutput.append('\n');
			//asciiOutput[pixelCounter] = '\n';
		}

		asciiOutput.trimToSize();
		return asciiOutput;
	}

	private static Character chooseClosestASCII(Color grayscalePixel){
		int diff = Integer.MAX_VALUE;
		Character outputChar = 'ß';

		for(ASCIIColor col : ASCIIGreyscaleValue.ALL_ASCIIS){
			if(Math.abs(grayscalePixel.getRGB() - col.color().getRGB()) < diff){
				diff = Math.abs(grayscalePixel.getRGB() - col.color().getRGB());

				outputChar = col.character();
			}
		}
		return outputChar;
	}

	private static Color rgbToGrayscalePixel(Color rgbPixel){
		int red = rgbPixel.getRed();
		int green = rgbPixel.getGreen();
		int blue = rgbPixel.getBlue();

		int gray = (rgbPixel.getRed() + rgbPixel.getGreen() + rgbPixel.getBlue()) / 3;

		return new Color(gray, gray, gray);
	}

	private static BufferedImage rgbToGreyscale(BufferedImage img){

		for(int y = 0; y < img.getHeight(); ++y){
			for(int x = 0; x < img.getWidth(); ++x){
				img.setRGB(x, y, rgbToGrayscalePixel(new Color(img.getRGB(x, y))).getRGB());
			}
		}

		return img;
	}

	private static void writeASCIIImage(StringBuilder sequence){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(desktopPath + outputASCIIFileName));
			bw.write(sequence.toString(), 0, sequence.length());
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private static void writeImage(BufferedImage img){
		try{
			BMPEncoder.write(img, new File(desktopPath + outputFileName));
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			System.out.println(UI.GREEN("Done saving Image") + UI.RESET());
			UI.waitForKeypress();
		}
	}

	private static BufferedImage loadGreyscaleImage(){
		BufferedImage img = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);

		try{
			img = BMPDecoder.read(new File(desktopPath + fileName));
		}catch(IOException e){
			e.printStackTrace();
		}

		return rgbToGreyscale(img);
	}

	public static void main(String[] args) {

		if(args.length != 0){
			fileName = args[0];
		}

		BufferedImage img = loadGreyscaleImage();

		StringBuilder asciiImage = greyscaleToASCII(img);

		writeASCIIImage(asciiImage);
		writeImage(img);
	}

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
}