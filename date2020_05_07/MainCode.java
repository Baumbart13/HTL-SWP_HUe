package date2020_05_07;

import miscForEverything.FileHandler;

import java.nio.file.*;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class MainCode {

	private static Encryption Crypt = new Encryption();
	private static final String Error = "Not correct Arguments given. Exiting now";


	public static void main(String[] args) {
		// If Arguments are okay
		if(!(args.length < 3)){
			foo(args[0], args[1], args[2]);
		// Otherwise cancel execution of program
		}else{
			try {
				System.err.println(Error);
				java.util.concurrent.TimeUnit.SECONDS.sleep(5);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		System.gc();
		return;
	}

	private static void foo(final String in, final String code, final String encrypt){
		if(in.endsWith(".txt")){
			try {
				if(encrypt.equalsIgnoreCase("j") || encrypt.equalsIgnoreCase("y")){
					LinkedList<String> input = new LinkedList<String>(Arrays.asList(FileHandler.readFile(in)));

					LinkedList<String> fixedInput = new LinkedList<String>();
					for(String s : input){
						fixedInput.add(Crypt.fixString(s));
					}

					miscForEverything.FileHandler.writeToFile(in, Crypt.encrypt(Paths.get(in).toAbsolutePath(), code));

				}else if(encrypt.equalsIgnoreCase("n")){

				}
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				System.gc();
				return;
			}
		}else{
			if(encrypt.toLowerCase().contains("encrypt")){

				System.out.println("Input:" + System.lineSeparator() + in);
				System.out.println("Code:" + System.lineSeparator() + code + System.lineSeparator());
				System.out.println("Fixed Input:" + System.lineSeparator() + Crypt.fixString(in));
				System.out.println("Output:" + System.lineSeparator() + Crypt.encrypt(in, code));




			}else if(encrypt.toLowerCase().contains("decrypt")){

				System.out.println("Input:" + System.lineSeparator() + in);
				System.out.println("Code:" + System.lineSeparator() + code + System.lineSeparator());
				System.out.println("Fixed Input:" + System.lineSeparator() + Crypt.fixString(in));
				System.out.println("Output:" + System.lineSeparator() + Crypt.decrypt(in, code));

				LinkedList<String> possibleCode = new LinkedList<String>();
				LinkedList<String> possibleSolutions = new LinkedList<String>();
				String crypted = "";
				for(int i = 0; i < 9999; ++i){
					crypted = Crypt.encrypt(in, Integer.toString(i));
					if(crypted.contains("AAA")){
						possibleCode.add(Crypt.fixCode(String.valueOf(i)));
						possibleSolutions.add(crypted);
					}
				}

				try{
					FileHandler.writeToFile("solutions.txt", possibleSolutions.toArray(new String[possibleSolutions.size()]));
				}catch(IOException e){
					e.printStackTrace();
				}

				System.out.println("Original from decrypted:" + System.lineSeparator() + Crypt.encrypt(Crypt.decrypt(in, code), code));
				System.out.println(System.lineSeparator() + "Possible Codes:");
				for(String s : possibleCode){
					System.out.println(s);
				}
				System.out.println(System.lineSeparator() + "Possible Solutions:");
				for(String s : possibleSolutions){
					System.out.println(s);
				}
			}
		}
	}
}
