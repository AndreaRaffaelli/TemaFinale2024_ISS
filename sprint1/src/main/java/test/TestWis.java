package main.java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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

//import it.unibo.ctxprodcons.MainCtxprodconsKt; //Solo in IntelliJ
//import it.unibo.ctxreferee.MainCtxrefereeKt;
import unibo.basicomm23.utils.ConnectionFactory;

public class TestWis {
	private static Interaction connSupport;

	private static final String ADDRESS = "localhost"; // Indirizzo dell'host
	private static final String PORT = "6969"; // Porta (modificare secondo necessità)
	private static final ProtocolType PROTOCOL = ProtocolType.tcp; // Protocollo da utilizzare
	private static final int DLIMIT = 3;

	@Test
	public void test() {
		IApplMessage req = CommUtils.buildRequest("tester", "testRequest", "testRequest(A)", "test_observer");
		try {
			CommUtils.outmagenta("test_observer ======================================= ");
			while (connSupport == null) {
				connSupport = ConnectionFactory.createClientSupport(PROTOCOL, ADDRESS, PORT);
				CommUtils.outcyan("testwis another connect attempt ");
				Thread.sleep(1000);
			}
			CommUtils.outcyan("CONNECTED to test_observer " + connSupport);
			Thread.sleep(5000);

			IApplMessage reply = connSupport.request(req);
			CommUtils.outcyan("test_observer reply=" + reply);
			String answer = reply.msgContent();
			String parameters = answer.substring(answer.indexOf('(') + 1, answer.lastIndexOf(')'));
			String[] s = parameters.split(",");

			assertEquals(s[0], "false");
			assertTrue(Integer.valueOf(s[1]) < DLIMIT); // Minore del limite massimo
			assertTrue(Integer.valueOf(s[2]) > 0);
			CommUtils.outcyan("Test eseguiti con successo");

		} catch (Exception e) {
			CommUtils.outred("test_observer ERROR " + e.getMessage());
			fail("testRequest " + e.getMessage());
		}
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
//
//	public static void main(String[] args) {
////	System.out.println(System.getProperty("os.name"));
//		activateSystemUsingDeploy();
//		test();
//	}

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

}