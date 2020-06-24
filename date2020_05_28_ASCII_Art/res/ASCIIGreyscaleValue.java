package date2020_05_28_ASCII_Art.res;

import java.awt.*;

/**
 * To go through all ASCIIs with values, iterate through "ALL_ASCIIS"
 */
public interface ASCIIGreyscaleValue {

	ASCIIColor HASHTAG		= new ASCIIColor(new Color(30,30,30), '#');
	ASCIIColor DOT			= new ASCIIColor(new Color(200,200,200), '.');
	ASCIIColor SPACE		= new ASCIIColor(new Color(255,255,255), ' ');

	// alphabet lowercase
	ASCIIColor LOWERCASE_A	= new ASCIIColor(new Color(199,199,199), 'a');
	ASCIIColor LOWERCASE_B	= new ASCIIColor(new Color(182,182,182), 'b');
	ASCIIColor LOWERCASE_C	= new ASCIIColor(new Color(208,208,208), 'c');
	//ASCIIColor LOWERCASE_D	= new ASCIIColor(new Color(182,182,182), 'd'); // same as LOWERCASE_B
	//ASCIIColor LOWERCASE_E	= new ASCIIColor(new Color(199,199,199), 'e'); // same as LOWERCASE_A
	ASCIIColor LOWERCASE_F	= new ASCIIColor(new Color(204,204,204), 'f');
	ASCIIColor LOWERCASE_G	= new ASCIIColor(new Color(172,172,172), 'g');
	ASCIIColor LOWERCASE_H	= new ASCIIColor(new Color(184,184,184), 'h');
	ASCIIColor LOWERCASE_I	= new ASCIIColor(new Color(128,128,128), 'i'); //TODO i needs to be analyzed
	//ASCIIColor LOWERCASE_J	= new ASCIIColor(new Color(204,204,204), 'j'); // same as LOWERCASE_F
	ASCIIColor LOWERCASE_K	= new ASCIIColor(new Color(187,187,187), 'k');
	//ASCIIColor LOWERCASE_L	= new ASCIIColor(new Color(204,204,204), 'l'); //same as LOWERCASE_F
	ASCIIColor LOWERCASE_M	= new ASCIIColor(new Color(180,180,180), 'm');
	ASCIIColor LOWERCASE_N	= new ASCIIColor(new Color(193,193,193), 'n');
	ASCIIColor LOWERCASE_O	= new ASCIIColor(new Color(195,195,195), 'o');
	ASCIIColor LOWERCASE_P	= new ASCIIColor(new Color(178,178,178), 'p');
	//ASCIIColor LOWERCASE_Q	= new ASCIIColor(new Color(178,178,178), 'q'); // same as LOWERCASE_P
	ASCIIColor LOWERCASE_R	= new ASCIIColor(new Color(212,212,212), 'r');
	ASCIIColor LOWERCASE_S	= new ASCIIColor(new Color(208,208,208), 's');
	//ASCIIColor LOWERCASE_T	= new ASCIIColor(new Color(204,204,204), 't'); // same as LOWERCASE_F
	//ASCIIColor LOWERCASE_U	= new ASCIIColor(new Color(193,193,193), 'u'); // same as LOWERCASE_N
	//ASCIIColor LOWERCASE_V	= new ASCIIColor(new Color(199,199,199), 'v'); // same as LOWERCASE_A
	ASCIIColor LOWERCASE_W	= new ASCIIColor(new Color(187,187,187), 'w');
	//ASCIIColor LOWERCASE_X	= new ASCIIColor(new Color(199,199,199), 'x'); // same as LOWERCASE_A
	ASCIIColor LOWERCASE_Y	= new ASCIIColor(new Color(178,178,178), 'y');
	//ASCIIColor LOWERCASE_Z	= new ASCIIColor(new Color(208,208,208), 'z'); // same as LOWERCASE_C

	// alphabet uppercase
	ASCIIColor UPPERCASE_A	= new ASCIIColor(new Color(128,128,128), 'A');
	ASCIIColor UPPERCASE_B	= new ASCIIColor(new Color(128,128,128), 'B');
	ASCIIColor UPPERCASE_C	= new ASCIIColor(new Color(128,128,128), 'C');
	ASCIIColor UPPERCASE_D	= new ASCIIColor(new Color(128,128,128), 'D');
	ASCIIColor UPPERCASE_E	= new ASCIIColor(new Color(128,128,128), 'E');
	ASCIIColor UPPERCASE_F	= new ASCIIColor(new Color(128,128,128), 'F');
	ASCIIColor UPPERCASE_G	= new ASCIIColor(new Color(128,128,128), 'G');
	ASCIIColor UPPERCASE_H	= new ASCIIColor(new Color(128,128,128), 'H');
	ASCIIColor UPPERCASE_I	= new ASCIIColor(new Color(128,128,128), 'I');
	ASCIIColor UPPERCASE_J	= new ASCIIColor(new Color(128,128,128), 'J');
	ASCIIColor UPPERCASE_K	= new ASCIIColor(new Color(128,128,128), 'K');
	ASCIIColor UPPERCASE_L	= new ASCIIColor(new Color(128,128,128), 'L');
	ASCIIColor UPPERCASE_M	= new ASCIIColor(new Color(128,128,128), 'M');
	ASCIIColor UPPERCASE_N	= new ASCIIColor(new Color(128,128,128), 'N');
	ASCIIColor UPPERCASE_O	= new ASCIIColor(new Color(128,128,128), 'O');
	ASCIIColor UPPERCASE_P	= new ASCIIColor(new Color(128,128,128), 'P');
	ASCIIColor UPPERCASE_Q	= new ASCIIColor(new Color(128,128,128), 'Q');
	ASCIIColor UPPERCASE_R	= new ASCIIColor(new Color(128,128,128), 'R');
	ASCIIColor UPPERCASE_S	= new ASCIIColor(new Color(128,128,128), 'S');
	ASCIIColor UPPERCASE_T	= new ASCIIColor(new Color(128,128,128), 'T');
	ASCIIColor UPPERCASE_U	= new ASCIIColor(new Color(128,128,128), 'U');
	ASCIIColor UPPERCASE_V	= new ASCIIColor(new Color(128,128,128), 'V');
	ASCIIColor UPPERCASE_W	= new ASCIIColor(new Color(128,128,128), 'W');
	ASCIIColor UPPERCASE_X	= new ASCIIColor(new Color(128,128,128), 'X');
	ASCIIColor UPPERCASE_Y	= new ASCIIColor(new Color(128,128,128), 'Y');
	ASCIIColor UPPERCASE_Z	= new ASCIIColor(new Color(128,128,128), 'Z');


	ASCIIColor[] ALL_ASCIIS = {HASHTAG, DOT, SPACE, LOWERCASE_I, LOWERCASE_A, LOWERCASE_B, LOWERCASE_C, LOWERCASE_F, LOWERCASE_G, LOWERCASE_H, LOWERCASE_I,
								LOWERCASE_K, LOWERCASE_M, LOWERCASE_N, LOWERCASE_O, LOWERCASE_P, LOWERCASE_R, LOWERCASE_S, LOWERCASE_W, LOWERCASE_Y
								};
}
