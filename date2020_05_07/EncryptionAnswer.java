package date2020_05_07;

import java.util.LinkedList;

public class EncryptionAnswer {
	private LinkedList<StringBuilder> codes;
	private LinkedList<StringBuilder> strings;

	/**
	 * @brief Compare this class to a hashmap which holds the code that was used to decrypt the string
	 */
	public EncryptionAnswer(){
		this.codes = new LinkedList<StringBuilder>();
		this.strings = new LinkedList<StringBuilder>();
	}

	/**
	 * @brief Returns the decrypted string and code separated by a semicolon
	 * @param index Which element should be returned
	 * @return The decrypted string with corresponding code. Looks like 'string;code'
	 */
	public String getElement(int index){
		return strings.get(index) + ";" + codes.get(index);
	}

	/**
	 * @brief Adds a decrypted string and corresponding code
	 * @param str The decrypted string
	 * @param code The code that was used for this string
	 */
	public void addElement(String str, String code){
		codes.add(new StringBuilder(str));
		strings.add(new StringBuilder(code));
	}

	/**
	 * @brief Adds a decrypted string and corresponding code, separated by a semicolon
	 * @param strCode The decrypted string and code. Looks like 'string;code'
	 */
	public void addElement(String strCode){
		strings.add(new StringBuilder(strCode.substring(0, strCode.indexOf(';'))));
		codes.add(new StringBuilder(strCode.substring(strCode.indexOf(';'))));
	}
}
