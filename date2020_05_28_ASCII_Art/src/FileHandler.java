package date2020_05_28_ASCII_Art.src;

import miscForEverything.UI;
import net.sf.image4j.codec.bmp.BMPDecoder;
import net.sf.image4j.codec.bmp.BMPEncoder;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
	private FileHandler(){

	}

	public static void writeImage(BufferedImage img, String path){
		try{
			BMPEncoder.write(img, new File(path));
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			System.out.println(UI.GREEN("Done saving Image") + UI.RESET());
			UI.waitForKeypress();
		}
	}

	public static void writeASCIIImage(StringBuilder sequence, String path){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(sequence.toString(), 0, sequence.length());
		}catch(IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			System.out.println(UI.GREEN("Done saving ASCII-Image") + UI.RESET());
			UI.waitForKeypress();
		}
	}

	public static BufferedImage loadImage(String path){
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

		try{
			img = BMPDecoder.read(new File(path));
		}catch(IOException e){
			e.printStackTrace();
		}

		return img;
	}


}
