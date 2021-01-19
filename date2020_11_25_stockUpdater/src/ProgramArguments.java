package date2020_11_25_stockUpdater.src;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public interface ProgramArguments {
	public interface Long{
		String install		= "--install";
		String uninstall	= "--uninstall";
		String help			= "--help";
		String autoupdate	= "--autoupdate";
		String update		= "--update";
		String windowed		= "--windowed";
		String updateDebug	= "--update-debug";

		static String[] getAll(){
			return new String[]{install, uninstall, help, autoupdate, update, windowed};
		}
	}

	@NotNull
	static boolean isAnyArgumentUsed(String... args){
		List<String> inspectMorePrecisely = new LinkedList<String>();

		for(int i = 0; i < args.length; ++i){
			if(args[i].startsWith("-")){
				inspectMorePrecisely.add(args[i]);
			}
		}
		for(int i = 0; i < inspectMorePrecisely.size(); ++i){

			for(int longForm = 0; longForm < Long.getAll().length; ++longForm){
				if(inspectMorePrecisely.get(i).equalsIgnoreCase(Long.getAll()[longForm])){
					return true;
				}
			}

			for(int shortForm = 0; shortForm < Short.getAll().length; ++shortForm){
				if(inspectMorePrecisely.get(i).equalsIgnoreCase(Long.getAll()[shortForm])){
					return true;
				}
			}

		}

		return false;
	}

	public interface Short{
		String help			= "-?";
		String windowed		= "-w";

		static String[] getAll(){
			return new String[]{help, windowed};
		}
	}

	String helpMessage();
}
