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
    private static final String DOCKER_NAME = "container_docker";
    private static String pidContext = "";
    private static String pidDocker = "";
    private static String pidBr = "";

    @BeforeClass
    public static void activateSystemUsingDeploy() {
        Thread th = new Thread(() -> {
            Process docker = null;
            Process br = null;
            Process p = null;
            String processPath = "";
            try {
                String osName = System.getProperty("os.name");
                if (osName.startsWith("Linux")) {
                    cleanOldDeployment();
                    processPath = "./build/distributions/testfunzionale-1.0/bin/testfunzionale";
                } else if (osName.startsWith("Windows")) {
                    processPath = "./build/distributions/testfunzionale-1.0/bin/testfunzionale.bat";
                } else {
                    CommUtils.outred("Unsupported operating system: " + osName);
                    return;
                }

                extractTarball();
                docker = startProcess("docker run --name " + DOCKER_NAME + " -ti -p 8090:8090 -p 8091:8091 --rm docker.io/natbodocker/virtualrobotdisi23:1.0");
                br = startProcess("../basicrobot24-1.0/bin/basicrobot24");
                pidBr = Long.toString(br.pid());

                Thread.sleep(2000);
                p = startProcess(processPath);
                pidContext = Long.toString(p.pid());

                showOutput(p, ColorsOut.BLACK);
                int exitCode = p.waitFor();
                CommUtils.outmagenta("Process exited with code: " + exitCode);
            } catch (Exception e) {
                CommUtils.outred("Error during deployment: " + e.getMessage());
            } finally {
                if (p != null) {
                    p.destroy();
                }
                if (br != null) {
                    br.destroy();
                }
                if (docker != null) {
                    docker.destroy();
                }
            }
        });
        th.start();
    }

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

            IApplMessage reply = connSupport.request(testRequest);
            CommUtils.outcyan("test_observer reply=" + reply);
            String answer = reply.msgContent();
            String s = answer.substring(answer.indexOf('(') + 1, answer.lastIndexOf(')'));

            assertTrue(Boolean.parseBoolean(s)); // Modificato per convertire la stringa in booleano
        } catch (Exception e) {
            CommUtils.outred("test_observer ERROR " + e.getMessage());
            fail("testRequest " + e.getMessage());
        }
    }

    @AfterClass
    public static void terminateSystemUsingDeploy() throws IOException {
        String osName = System.getProperty("os.name");
        ProcessBuilder pbContext, pbBr;

        if (osName.startsWith("Linux")) {
            pbContext = new ProcessBuilder("kill", "-15", pidContext);
            pbBr = new ProcessBuilder("kill", "-15", pidBr);
        } else if (osName.startsWith("Windows")) {
            pbContext = new ProcessBuilder("taskkill", "/F", "/PID", pidContext);
            pbBr = new ProcessBuilder("taskkill", "/F", "/PID", pidBr);
        } else {
            CommUtils.outred("Unsupported operating system: " + osName);
            return;
        }

        try {
            pbContext.start().waitFor();
            pbBr.start().waitFor();
            startProcess("docker stop " + DOCKER_NAME).waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            CommUtils.outred("Context stopped");
        }
    }

    private static void showOutput(Process proc, String color) {
        new Thread(() -> {
            try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                ColorsOut.outappl("Here is the standard output of the command:\n", color);
                String s;
                while ((s = stdInput.readLine()) != null) {
                    ColorsOut.outappl(s, color);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

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
        ProcessBuilder pb = new ProcessBuilder("tar", "-xvf", "build/distributions/testfunzionale-1.0.tar", "-C", "build/distributions/");
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
