package date2020_05_28_ASCII_Art.src;

import date2020_05_28_ASCII_Art.res.ASCIIColor;
import date2020_05_28_ASCII_Art.res.ASCIIGreyscaleValue;
import miscForEverything.CustomExceptions.CantCreateException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ASCIIConverter {

	private ASCIIConverter() throws CantCreateException {
		throw new CantCreateException();
	}

	/**
	 * Converts an grey BufferedImage to a ASCII-Image in a StringBuilder.
	 * @param img The gray BufferedImage, which should be converted.
	 * @return Return the ASCII-Image as a StringBuilder
	 */
	public static StringBuilder greyscaleToASCII(BufferedImage img){

		StringBuilder asciiOutput = new StringBuilder(img.getHeight() + img.getWidth()*2);
		int pixelCounter = 0;

		for(int y = 0; y < img.getHeight(); ++y) {

			for (int x = 0; x < img.getWidth(); ++x) {

				Character character = chooseClosestASCII(new Color(img.getRGB(x, y)));
				asciiOutput.append(character);
				asciiOutput.append(character);
			}

			asciiOutput.append('\n');
		}

		asciiOutput.trimToSize();
		return asciiOutput;
	}

	/**
	 * Searches the ASCII-symbol which corresponds the most to the given pixel
	 * @param grayscalePixel The Pixel, which should be analyzed
	 * @return The most corresponding ASCII-symbol
	 */
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

	public static Color rgbToGrayscalePixel(Color rgbPixel){

		int gray = (rgbPixel.getRed() + rgbPixel.getGreen() + rgbPixel.getBlue()) / 3;
		return new Color(gray, gray, gray);
	}

	public static BufferedImage rgbToGreyscale(BufferedImage img){

		for(int y = 0; y < img.getHeight(); ++y){
			for(int x = 0; x < img.getWidth(); ++x){
				img.setRGB(x, y, rgbToGrayscalePixel(new Color(img.getRGB(x, y))).getRGB());
			}
		}

		return img;
	}
}
