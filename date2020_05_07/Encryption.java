package date2020_05_07;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import static miscForEverything.FileHandler.*;

public class Encryption {
	private char lowerBound;
	private char upperBound;
	private int codeLength;

	/**
	 * @brief Codelength is by default 4
	 * @param lowerBound Sets the lower bound, including
	 * @param upperBound Sets the upper bound, including
	 */
	public Encryption(char lowerBound, char upperBound){
		this(lowerBound, upperBound, 4);
	}

	/**
	 * @brief Codelength is by default 4, Lowerbound is A, Upperbound is Z
	 */
	public Encryption(){
		this('A', 'Z', 4);
	}

	/**
	 * @param lowerBound Sets the lower bound, including
	 * @param upperBound Sets the upper bound, including
	 * @param codeLength Sets the length of the code. Can be changed later with 'setCodeLength'
	 */
	public Encryption(char lowerBound, char upperBound, int codeLength){
		this.setCodeLength(codeLength);
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
	}

	/**
	 * @param codeLength Sets the length of the code
	 * @throws IllegalArgumentException If the argument codeLength is less than or equals 1
	 */
	public void setCodeLength(int codeLength) throws IllegalArgumentException{
		if(codeLength <= 0){
			throw new IllegalArgumentException("Length of code cannot be less than 1.");
		}else {
			this.codeLength = codeLength;
		}
	}

	/**
	 * @brief Searches through a series of encrypted words for 'find'
	 * @param text The series of words, which should be decrypted
	 * @param find For which 'series' is being searched
	 * @return
	 */
	public HashMap<String, LinkedList<String>> searchFor(final String[] text, final String find){
		HashMap<String, LinkedList<String>> codeText = new HashMap<String, LinkedList<String>>();

		String decrypted = "";
		for(int i = 0; i < Math.pow(10, codeLength); ++i){
			for (String str : text) {
				decrypted = decrypt(str, Integer.toString(i));

				// If found
				if(decrypted.contains(find)){

					// If this is the first one to be found with this code
					if(!codeText.containsKey(fixCode(Integer.toString(i)))){

						// Insert the new Code
						codeText.put(fixCode(Integer.toString(i)), new LinkedList<String>());
						// and apply the input text decrypted to it
						for(String s : text){
							codeText.get(fixCode(Integer.toString(i))).add(decrypt(s, Integer.toString(i)));
						}
					}
				}
			}
		}

		// TODO replace the return with a method, that converts the hashmap to a 2D-String-Array
		return codeText;
	}

	private char[] fixCodeInput(final String str) throws IllegalArgumentException {
		StringBuilder s = new StringBuilder(str);
		// Fix length
		if (s.length() > this.codeLength) {
			s.delete(this.codeLength, s.length());
			s.trimToSize();

		} else if (s.length() < this.codeLength) {
			s = s.reverse();
			while (s.length() != this.codeLength) {
				s.append('0');
			}
			s.reverse();
		}

		// Throw Exception on wrong input
		for (int i = 0; i < s.length(); ++i) {
			if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
				throw new IllegalArgumentException("Argmuent \"code\" can only include digits and no letters." + System.lineSeparator() +
						s.charAt(i) + System.lineSeparator());
			}
		}

		// To Integers
		char[] out = s.toString().toCharArray();
		for (int i = 0; i < out.length; ++i) {
			out[i] -= '0';
		}

		return out;
	}

	private char[] fixStringInput(final String str) {
		StringBuilder s = new StringBuilder(str.toUpperCase());
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) < lowerBound || s.charAt(i) >= upperBound) {
				s.deleteCharAt(i);
				i = 0;
			}
		}
		return s.toString().toCharArray();
	}

	/**
	 * @brief Fixes the input to a format, which can be used to en-/decrypt messages by this algorithm. No need to call this method, if wanted to en-/decrypt
	 * @param str Code which should be fixed
	 * @return The fixed code as a string
	 */
	public String fixCode(String str){
		char[] out = fixCodeInput(str);
		for(int i = 0; i < out.length; ++i){
			out[i] += '0';
		}
		return new String(out);
	}

	/**
	 * @brief Fixes the input to a format, which can be en-/decrypted by this algorithm. No need to call this method, if wanted to en-/decrypt string
	 * @param str String which should be fixed
	 * @return The fixed string for algorithm
	 */
	public String fixString(String str){
		return new String(fixStringInput(str));
	}

	/**
	 * @param file The file, from which the lines should be decrypted
	 * @param code The code, with which the lines should be decrypted
	 * @return The decrypted lines of file
	 * @throws IOException File Output
	 */
	public String[] decrypt(Path file, final String code) throws IOException {
		return cryptStringsFromFile(file, code, false);
	}

	/**
	 * @param file The file, from which the lines should be encrypted
	 * @param code The code, with which the lines should be encrypted
	 * @return The encrypted lines of file
	 * @throws IOException File Output
	 */
	public String[] encrypt(Path file, final String code) throws IOException {
		return cryptStringsFromFile(file, code, true);
	}

	/**
	 * @brief Encrypts a string with a code. No need to call 'fixString'
	 * @param in The string which should be encrypted
	 * @param code The code which is used to encrypt the string. Only numbers are allowed
	 * @return The encrypted string
	 * @throws IllegalArgumentException If code contains other symbols than digits
	 */
	public String encrypt(String in, final String code) throws IllegalArgumentException {
		return this.cryptString(in, code, true);
	}

	/**
	 * @brief Decrypts a string with a code. No need to call 'fixString'
	 * @param in The string which should be decrypted
	 * @param code The code which is used to decrypt the string. Only numbers are allowed
	 * @return The decrypted string
	 * @throws IllegalArgumentException If code contains other symbols than digits
	 */
	public String decrypt(String in, final String code) throws IllegalArgumentException {
		return this.cryptString(in, code, false);
	}

	private String[] cryptStringsFromFile(Path file, final String code, final boolean encrypt) throws IOException {
		LinkedList<String> input = new LinkedList<String>(Arrays.asList(readFile(file.toString())));

		if (encrypt) {
			for (int i = 0; i < input.size(); ++i) {
				input.set(i, encrypt(input.get(i), code));
			}
		} else {
			for (int i = 0; i < input.size(); ++i) {
				input.set(i, encrypt(input.get(i), code));
			}
		}

		return input.toArray(new String[input.size()]);
	}

	private String cryptString(String in, final String code, final boolean encrypt) throws IllegalArgumentException {
		// Catch possible error - throws IllegalArgumentException
		// Fix length of code and prepare input
		final char[] fixedCode = fixCodeInput(code);
		char[] input = fixStringInput(in);

		// Actual En-/Decrypt
		if (encrypt) {
			// Change value of input
			int codeIndex = 0;
			for (int stringIndex = 0; stringIndex < input.length; ++stringIndex) {
				input[stringIndex] += fixedCode[codeIndex];

				while (input[stringIndex] >= upperBound) {
					input[stringIndex] -= (upperBound - lowerBound);
				}

				// Next index - change current codeIndex
				codeIndex = codeIndex < fixedCode.length - 1 ? codeIndex + 1 : 0;
			}
		} else {
			// Change value of input
			int codeIndex = 0;
			for (int stringIndex = 0; stringIndex < input.length; ++stringIndex) {
				input[stringIndex] -= fixedCode[codeIndex];

				while (input[stringIndex] < lowerBound) {
					input[stringIndex] += (upperBound - lowerBound);
				}

				// Next index - change current codeIndex
				codeIndex = codeIndex < fixedCode.length - 1 ? codeIndex + 1 : 0;
			}
		}

		return new String(input);
	}
}
