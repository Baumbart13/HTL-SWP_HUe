package date2020_11_25_stockUpdater.src;

import java.util.LinkedList;
import java.util.List;

public class BaumbartStockInstaller {

	public final String BaumbartStockUpdater_lowestDirectory = "BaumbartStockWatcher";
	public final String BaumbartStockUpdater_filename = "BaumbartStockWatcher";

	public static class Windows {


		public interface SchTasks_Params {
			/**
			 * 1st argument.
			 * Every [interface_Param] should the task be done. Followed by a parameter of the inner interface.
			 */
			String SchTasks_Params_Schedule = "/SC ";

			public interface SchTasks_Params_Schedule {
				String MINUTE = "MINUTE";
				String HOURLY = "HOURLY";
				String DAILY = "DAILY";
				String WEEKLY = "WEEKLY";
				String MONTHLY = "MONTHLY";
				String ONCE = "ONCE";
				String ON_LOGON = "ONLOGON";
				String ON_IDLE = "ONIDLE";
				String ON_EVENT = "ONEVENT";
			}

			/**
			 * 2nd argument.
			 * Followed by y name for the Task.
			 */
			String SchTasks_Params_TaskName = "/TN ";

			/**
			 * 3rd argument.
			 * Followed by the program to be run.
			 */
			String SchTasks_Params_TaskRun = "/TR ";
		}

		private List<String> commands = new LinkedList<String>();

		public ProcessBuilder foo;
	}

	public static class Linux {
		public final String fileSeparator = System.getProperty("file.separator");
		public final String stockUpdaterPath = fileSeparator + "usr" + "src" + "BaumbartStockWatcher";
		public final String crontabPath = fileSeparator + "etc" + fileSeparator + "crontab";
		public final String crontabValue = "0 * * * *" + "root" + "path";
	}
}
