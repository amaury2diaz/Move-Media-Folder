package moveIt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;

public class Commands {
	private Commands() {
	}

	public static boolean isRunning(String process) {
		boolean found = false;
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
					+ "Set locator = CreateObject(\"WbemScripting.SWbemLocator\")\n"
					+ "Set service = locator.ConnectServer()\n"
					+ "Set processes = service.ExecQuery _\n"
					+ " (\"select * from Win32_Process where name='"
					+ process
					+ "'\")\n"
					+ "For Each process in processes\n"
					+ "wscript.echo process.Name \n"
					+ "Next\n"
					+ "Set WSHShell = Nothing\n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			line = input.readLine();
			if (line != null) {
				if (line.equals(process)) {
					found = true;
				}
			}
			input.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return found;
		
	}

	public static String getOldMediaFolderPath() {
		String fileContents = "Rupelstinkyi";
		String mediaFolderPath = "Dummy";
		String[] fileArray = new String[10];
		Path log_path = Paths.get("C:/log.txt");
		int counter = 0;

		try {
			File file = File.createTempFile("username", ".bat");

			FileWriter fw = new FileWriter(file);
			String bat = "reg query \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders\" /v \"My Music\" >C:\\log.txt \n exit 0";
			fw.write(bat);
			fw.close();
			Runtime.getRuntime().exec("cmd /c start " + file.getPath());

			try {
				Thread.sleep(1000); // 1000 milliseconds is one second.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			BufferedReader input = Files.newBufferedReader(log_path);

			fileContents = input.readLine();

			while ((fileContents = input.readLine()) != null) {

				fileArray[counter] = fileContents;
				counter++;

			}
			String[] parse = fileArray[1].split("    ");
			mediaFolderPath = parse[3];
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Files.delete(log_path);
		} catch (NoSuchFileException x) {
			System.err.format("%s: no such" + " file or directory%n", log_path);
		} catch (DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", log_path);
		} catch (IOException x) {
			// File permission problems are caught here.
			System.err.println(x);
		}

		return mediaFolderPath;
	}// end of getOldMediaFolderPath

	public static void moveIt(String oldMediaFolderPath) {
		try {
			
			File file = File.createTempFile("moveIt", ".bat");

			FileWriter fw = new FileWriter(file);
			String bat = " @echo off \nxcopy \"" + oldMediaFolderPath.trim()
					+ "\" \"C:\\PCShared\" /e /s /y \n del /S /F /Q \""
					+ oldMediaFolderPath.trim() + "\" \nexit 0";
			fw.write(bat);
			fw.close();
			Runtime.getRuntime().exec("cmd /c start " + file.getPath());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void changeRegistry() {
		try {
			File file = File.createTempFile("regChange", ".bat");

			FileWriter fw = new FileWriter(file);
			String bat = "@echo off \nreg add \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders\" /v \"My Music\" /t REG_EXPAND_SZ /d C:\\PCShared /f\n exit 0";
			fw.write(bat);
			fw.close();
			Runtime.getRuntime().exec("cmd /c start " + file.getPath());

		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
    public static void closeApp(String appName){
    	try {
			File file = File.createTempFile("killProgram", ".bat");

			FileWriter fw = new FileWriter(file);
			String bat = "@echo off \n taskkill /IM "+appName.trim()+"\n exit 0";
			fw.write(bat);
			fw.close();
			Runtime.getRuntime().exec("cmd /c start " + file.getPath());

		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
}