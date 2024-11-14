package main.java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class Sprint12Test {
    private static Interaction connSupport;
    
    // Paths and settings
    private static final String TAR_DIR = "build/distributions/sprintuno_due-1.0.tar";
    private static final String DIST_DIR = "build/distributions/sprintuno_due-1.0";
    private static final String ADDRESS = "localhost";
    private static final String PORT = "8022";
    private static final ProtocolType PROTOCOL = ProtocolType.tcp;
    private static final String DOCKER_IMAGE = "docker.io/natbodocker/virtualrobotdisi23:1.0";
    private static final int MAX_TIMEOUT_SEC = 60;

    private static String pidContext = "";
    private static String pidBr = "";
    private static String pidSP2 = "";
    private static Process mainProcess;
    private static Process dockerProcess;

    @BeforeClass
    public static void activateSystemUsingDeploy() {
        new Thread(Sprint12Test::deploySystem).start();
    }

    private static void deploySystem() {
        try {
            startDockerContainer();
            extractTarball();
            startProcessComponents();
        } catch (Exception e) {
            CommUtils.outred("Error during deployment: " + e.getMessage());
            cleanup();
        }
    }

    private static void startDockerContainer() throws IOException {
        dockerProcess = Runtime.getRuntime().exec(
            "docker run -d -p 8090:8090 -p 8091:8091 --rm " + DOCKER_IMAGE
        );
        CommUtils.outcyan("Docker container started with image: " + DOCKER_IMAGE);

        // Open the default browser to localhost:8090
        String osName = System.getProperty("os.name").toLowerCase();
        ProcessBuilder browserProcess;

        if (osName.contains("win")) {
            browserProcess = new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", "http://localhost:8090");
        } else if (osName.contains("mac")) {
            browserProcess = new ProcessBuilder("open", "http://localhost:8090");
        } else if (osName.contains("nix") || osName.contains("nux")) {
            browserProcess = new ProcessBuilder("xdg-open", "http://localhost:8090");
        } else {
            throw new UnsupportedOperationException("Unsupported operating system for opening a browser.");
        }

        browserProcess.start();
        CommUtils.outcyan("Opened localhost:8090 in the default browser.");
    }


    private static void extractTarball() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("tar", "-xvf", TAR_DIR, "-C", "build/distributions/");
        Process tarProcess = pb.start();
        tarProcess.waitFor();
        tarProcess.destroy();
        CommUtils.outcyan("Tarball extracted to: " + DIST_DIR);
    }

    private static void startProcessComponents() throws IOException, InterruptedException {
        String osName = System.getProperty("os.name");
        String mainScript = osName.startsWith("Windows") ? "sprintuno_due.bat" : "./sprintuno_due";
        
        ProcessBuilder pb = new ProcessBuilder(mainScript);
        pb.directory(new java.io.File(DIST_DIR + "/bin"));
        mainProcess = pb.start();
        pidContext = Long.toString(mainProcess.pid());
        
        CommUtils.outcyan("Started main process with PID: " + pidContext);
        showOutput(mainProcess, ColorsOut.BLACK);

        String robotScript = osName.startsWith("Windows") ? "basicrobot24.bat" : "./basicrobot24";
        ProcessBuilder robotPB = new ProcessBuilder(robotScript);
        robotPB.directory(new java.io.File("../basicrobot24-1.0/bin"));
        Process robotProcess = robotPB.start();
        pidBr = Long.toString(robotProcess.pid());
        
        CommUtils.outcyan("Started basicrobot process with PID: " + pidBr);
//        showOutput(robotProcess, ColorsOut.BLACK);

        String sprintScript = osName.startsWith("Windows") ? "sprintdue.bat" : "./sprintdue";
        ProcessBuilder sprintPB = new ProcessBuilder(sprintScript);
        sprintPB.directory(new java.io.File("../sprintdue-1.0/bin"));
        Process sprintProcess = sprintPB.start();
        pidSP2 = Long.toString(sprintProcess.pid());
        
        CommUtils.outcyan("Started sprintdue process with PID: " + pidSP2);
        showOutput(sprintProcess, ColorsOut.GREEN);
    }

    @Test
    public void testSystemFunctionality() {
        try {
            while (connSupport == null) {
                connSupport = ConnectionFactory.createClientSupport(PROTOCOL, ADDRESS, PORT);
                CommUtils.outcyan("Attempting to connect...");
                Thread.sleep(1000);
            }
            assertTrue("Main process did not finish in time", mainProcess.waitFor(MAX_TIMEOUT_SEC, TimeUnit.SECONDS));
//            assertEquals("Unexpected exit value", 30, mainProcess.exitValue());
            assertEquals("Unexpected exit value", 31, mainProcess.exitValue());
        } catch (Exception e) {
            CommUtils.outred("Test error: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @AfterClass
    public static void terminateSystemUsingDeploy() {
        try {
            terminateProcesses();
            stopDockerContainer();
        } catch (Exception e) {
            CommUtils.outred("Error during termination: " + e.getMessage());
        }
    }

    private static void terminateProcesses() throws IOException, InterruptedException {
        String osName = System.getProperty("os.name");

        if (osName.startsWith("Linux")) {
            runCommand("kill", "-15", pidContext, pidBr, pidSP2);
        } else if (osName.startsWith("Windows")) {
            runCommand("taskkill", "/F", "/PID", pidContext, pidBr);
        } else {
            CommUtils.outred("Unsupported operating system: " + osName);
        }
    }

    private static void stopDockerContainer() throws IOException, InterruptedException {
        if (dockerProcess != null) {
            dockerProcess.destroy();
            dockerProcess.waitFor();
            dockerProcess = null;
            CommUtils.outcyan("Docker container stopped");
        }
    }

    private static void runCommand(String... command) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();
        process.waitFor();
        process.destroy();
    }

    private static void showOutput(Process proc, String color) {
        new Thread(() -> {
            try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                ColorsOut.outappl("Process output:\n" + proc.info(), color);
                String s;
                while ((s = stdInput.readLine()) != null) {
                    ColorsOut.outappl(s, color);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void cleanup() {
        if (mainProcess != null) mainProcess.destroy();
        if (dockerProcess != null) dockerProcess.destroy();
    }
}
