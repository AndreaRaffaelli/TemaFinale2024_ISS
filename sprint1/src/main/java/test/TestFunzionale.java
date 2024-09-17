package main.java.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class TestFunzionale {
	private static Interaction connSupport;

	private static final String ADDRESS = "localhost"; // Indirizzo dell'host
	private static final String PORT = "6969"; // Porta (modificare secondo necessità)
	private static final ProtocolType PROTOCOL = ProtocolType.tcp; // Protocollo da utilizzare
	private static final int DLIMIT = 3;
	private static final long BTIME = 5000;
    private static final String docker_name = "container_docker";
	private static String pid_context = "";
    private static String pid_docker = "";
    private static String pid_br = "";

	@Test
	public void test() {
		IApplMessage testRequest = CommUtils.buildRequest("tester", "testRequest", "testRequest(A)", "test_observer");

		try {
			CommUtils.outmagenta("test_observer ======================================= ");
			while (connSupport == null) {
				connSupport = ConnectionFactory.createClientSupport(PROTOCOL, ADDRESS, PORT);
				CommUtils.outcyan("testfunzionale another connect attempt ");
				Thread.sleep(1000);
			}
			CommUtils.outcyan("CONNECTED to test_observer " + connSupport);
            Thread.sleep(15000);
			
			IApplMessage reply =connSupport.request(testRequest);
			CommUtils.outcyan("test_observer reply=" + reply);
			String answer = reply.msgContent();
			String s = answer.substring(answer.indexOf('(') + 1, answer.lastIndexOf(')'));

			assertTrue(s[0] == "true");
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
			Process docker = null;
            Process br = null;
            Process p = null;
            String process_path = "";
			try {
				String osName = System.getProperty("os.name");
				if (osName.startsWith("Linux")) {
					cleanOldDeployment();
					process_path = "./build/distributions/testfunzionale-1.0/bin/testfunzionale";
				} else if (osName.startsWith("Windows")) {
                    process_path = "./build/distributions/testfunzionale-1.0/bin/testfunzionale.bat";
				} else {
					CommUtils.outred("Unsupported operating system: " + osName);
					return;
				}

                extractTarball();
                docker = startProcess("docker run --name "+this.docker_name+" -ti -p 8090:8090 -p 8091:8091 --rm  docker.io/natbodocker/virtualrobotdisi23:1.0");
                br = startProcess("../basicrobot24-1.0/bin/basicrobot24");
                this.pid_br = Long.toString(br.pid());
                
                Thread.sleep(2000);
                p = startProcess(process_path);
                this.pid_context = Long.toString(p.pid());                

				showOutput(p, ColorsOut.BLACK);
				int exitCode = p.waitFor();
				CommUtils.outmagenta("Process exited with code: " + exitCode);
			} catch (Exception e) {
				CommUtils.outred("Error during deployment: " + e.getMessage());
			} finally {
				if (p != null) {
					p.destroy();
				}
                if (br != null){
                    br.destroy();   
                }
                if (docker != null){
                    docker.destroy();
                }
			}
		});
		th.start();
	}

	@AfterClass
	public static void terminateSystemUsingDeploy() throws IOException {
		String osName = System.getProperty("os.name");
        ProcessBuilder pb_context, pb_br;

		if (osName.startsWith("Linux")) {
			pb_context = new ProcessBuilder("kill", "-15", this.pid_context);			
			pb_br = new ProcessBuilder("kill", "-15", this.pid_br);
		} else if (osName.startsWith("Windows")) {
			pb_context = new ProcessBuilder("taskkill", "/F", "/PID", this.pid_context);
            pb_br = new ProcessBuilder("taskkill", "/F", "/PID", this.pid_br)
		}

        Process p = pb_context.start();
        Process p_br = pb_br.start();
		Process p_docker = startProcess("docker stop " + this.docker_name);			
        try {
            p.waitFor();
            p_br.waitFor();
            p_docker.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            CommUtils.outred("Context stopped");
        } finally {
            p.destroy();
            p_br.destroy();
            p_docker.destroy();
        }
	}

//
//	public static void main(String[] args) {
////	System.out.println(System.getProperty("os.name"));
//		activateSystemUsingDeploy();
//		test();
//	}

	private static void cleanOldDeployment() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("rm", "-rf", "build/distributions/testfunzionale-1.0");
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
		ProcessBuilder pb = new ProcessBuilder("tar", "-xvf", "build/distributions/testfunzionale-1.0.tar", "-C",
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