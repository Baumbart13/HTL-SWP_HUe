package date2020_05_28_ASCII_Art.res;

import java.awt.*;

/**
 * To go through all ASCIIs with values, iterate through "ALL_ASCIIS"
 */
public interface ASCIIGreyscaleValue {

	ASCIIColor HASHTAG		= new ASCIIColor(new Color(30,30,30), '#');
	ASCIIColor DOT			= new ASCIIColor(new Color(200,200,200), '.');
	ASCIIColor SPACE		= new ASCIIColor(new Color(255,255,255), ' ');
	ASCIIColor LOWERCASE_I	= new ASCIIColor(new Color(128,128,128), 'i');


	ASCIIColor[] ALL_ASCIIS = {HASHTAG, DOT, SPACE, LOWERCASE_I};
}
