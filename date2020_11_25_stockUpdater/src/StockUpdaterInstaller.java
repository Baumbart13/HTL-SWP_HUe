package date2020_11_25_stockUpdater.src;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.Locale;

public final class StockUpdaterInstaller {

	private static final String programPath = new JFileChooser().getFileSystemView().getHomeDirectory().getParent().concat(String.format("%sDocuments%sStockUpdaterBaumi%sbin",File.separator, File.separator, File.separator));
	private static final String fileName = "StockUpdaterBaumbart.jar";

	//----------------------------
	// Task-independent
	public static enum OS{
		Windows,
		Linux,
		NotSupported;
	}

	/**
	 * Determine the current OS.
	 * @return the current OS.
	 */
	private static OS selectOS(){
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			return OS.Windows;
		}
		if(System.getProperty("os.name").toLowerCase().contains("linux")){
			return OS.Linux;
		}
		return OS.NotSupported;
	}

	private static void exit(String msg){
		System.gc();
		System.out.println(msg);
		System.exit(0);
	}

	/**
	 * Used for - at least windows - locale-issues
	 * @return the correct translation of "daily".
	 */
	private static String setDailyToSystemLanguage(){
		var language = Locale.getDefault().getLanguage();

		switch(language.toLowerCase()){
			case "en": return "daily";
			case "de":
			default: return "täglich";
		}
	}

	/**
	 * NO!
	 */
	private StockUpdaterInstaller(){
		super();
	}

	/**
	 * Is this file at the correct spot yet?
	 * @return The answer to this question.
	 */
	private static boolean isRightPath(){
		return System.getProperty("user.dir").equals(programPath);
	}

	/**
	 * If the path is incorrect.. fix it
	 */
	private static void fixPath(){
		var currentFile = String.format("%s%s%s",
				System.getProperty("user.dir"), File.separator, fileName);

		try {
			new File(programPath).mkdirs();
			Files.copy(Paths.get(currentFile), Paths.get(programPath.concat(File.separator).concat(fileName)),
					/*StandardCopyOption.ATOMIC_MOVE,*/ StandardCopyOption.COPY_ATTRIBUTES);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return;
	}

	// Task-independent
	//----------------------------
	// Installation

	/**
	 * Let the OS determine, which method shall be used to create the automation
	 * @param os The current OS
	 */
	private static void createAutomatition(OS os){
		switch (os){
			case Linux:
				createCrontab();
				break;
			case Windows:
				createScheduledTask();
				break;
			default:
				System.out.println("What is this OS? Not implemented in any way");
		}
	}

	/**
	 * The windows style of crontasks
	 */
	private static void createScheduledTask(){
		var system = "localhost";
		var daily = setDailyToSystemLanguage();
		var taskName = "UpdateStock";
		var path = String.format("%s%s%s", programPath, File.separator, fileName);
		var startTime = LocalTime.of(0, 0);

		// Only reason for this amount of variables is readability
		var command = String.format("SCHTASKS /CREATE /S %s /SC %s /TN %s /TR \"%s\" /ST %s",
				system, daily, taskName, path, startTime);

		try{
			Runtime.getRuntime().exec(command);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Creates the according crontab for Linux
	 */
	private static void createCrontab(){
		System.err.println("StockUpdaterInstaller.createCrontab() not implemented yet.");
	}

	/**
	 * This method is called, when the user gives the parameter "--install" to the executable. It will set a cronjob/scheduled task/etc to automatically update the local database of the stocks.
	 */
	public static void install(){
		if(!isRightPath()){
			fixPath();
		}

		createAutomatition(selectOS());

		exit("Set up automatic task successfully");
	}

	// Installation
	//----------------------------
	// Uninstallation

	/**
	 * Let the OS determine, which method shall be used to delete the automation
	 * @param os The current OS
	 */
	private static void deleteAutomation(OS os){
		switch(os){
			case Linux:
				deleteCrontab();
				break;
			case Windows:
				deleteScheduledTask();
				break;
			default:
				System.out.println("What is this OS? Not implemented in any way");
		}
	}

	private static void deleteCrontab(){
		System.err.println("StockUpdaterInstaller.deleteCrontab() not implemented yet.");
	}

	private static void deleteScheduledTask(){
		var taskName = "UpdateStock";
		var command = String.format("SCHTASKS /DELETE /TN %s", taskName);

		try{
			Runtime.getRuntime().exec(command);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * This method is called, when the user gives the parameter "--uninstall" to the executable. It will remove the cronjob/scheduled task/etc of automatically updating the local database of the stocks.
	 */
	public static void uninstall(){

		if(isRightPath()){
			deleteAutomation(selectOS());
		}

		exit("Deleted automatic task successfully");
	}

	// Uninstallation
	//----------------------------
}
