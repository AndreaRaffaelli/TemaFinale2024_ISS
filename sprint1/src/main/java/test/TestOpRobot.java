package main.java.test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;

public class TestOpRobot {

	@Test
	public void test() {
//		fail("Not yet implemented");
	}
	
	@BeforeClass
	public static void activateSystemUsingDeploy() {
		Thread th = new Thread(() -> {
			Process p = null;
			try {
				String osName = System.getProperty("os.name");
				if (osName.startsWith("Linux")) {
					cleanOldDeployment();
					extractTarball();
					p = startProcess("./build/distributions/testwis-1.0/bin/testwis");
				} else if (osName.startsWith("Windows")) {
					extractTarball();
					p = startProcess("./build/distributions/testwis-1.0/bin/testwis.bat");
				} else {
					CommUtils.outred("Unsupported operating system: " + osName);
					return;
				}

				showOutput(p, ColorsOut.BLACK);
				int exitCode = p.waitFor();
				CommUtils.outmagenta("Process exited with code: " + exitCode);

			} catch (Exception e) {
				CommUtils.outred("Error during deployment: " + e.getMessage());
			} finally {
				if (p != null) {
					p.destroy();
				}
			}
		});
		th.start();
	}
	
	private static void cleanOldDeployment() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("rm", "-rf", "build/distributions/testwis-1.0");
		Process p = pb.start();
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			CommUtils.outred("Cleanup interrupted");
		} finally {
			p.destroy();
		}
	}

	private static void extractTarball() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("tar", "-xvf", "build/distributions/testwis-1.0.tar", "-C",
				"build/distributions/");
		Process p = pb.start();
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			CommUtils.outred("Extraction interrupted");
		} finally {
			p.destroy();
		}
	}

	private static Process startProcess(String command) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(command.split(" "));
		pb.redirectErrorStream(true); // Redirects error stream to output stream
		return pb.start();
	}
	
	public static void showOutput(Process proc, String color) {
		new Thread() {
			public void run() {
				try {
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
					BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
					ColorsOut.outappl("Here is the standard output of the command:\n", color);
					while (true) {
						String s = stdInput.readLine();
						if (s != null)
							ColorsOut.outappl(s, color);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
