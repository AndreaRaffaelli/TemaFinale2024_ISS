package main.java.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class GlobalTest {
	private static Interaction connSupport;

	// Indirizzo e porta della GUI:
    private static final String ADDRESS = "localhost"; // 
    private static final String PORT = "8022"; //
    private static final ProtocolType PROTOCOL = ProtocolType.tcp; // Protocollo da utilizzare
	private static final int MAX_T = 15000;


    private static final String DOCKER_COMPOSE_FILE = "/home/andrea/unibo/iss/TemaFinale2024_ISS/docker-compose.yml";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        executeCommand("docker-compose -f " + DOCKER_COMPOSE_FILE + " up -d");
    }

    @Test
    public void testContainersRunning() throws Exception {
        List<String> containerIds = getRunningContainerIds();
        for (String containerId : containerIds) {
            System.out.println("testing " + containerId);
            assertTrue("Container is not running: " + containerId, containerId != null);
        }
    }

    private static List<String> getRunningContainerIds() throws Exception {
        List<String> containerIds = new ArrayList<>();
        String command = "docker ps -q";
        String result = executeCommand(command);
        for (String line : result.split("\n")) {
            containerIds.add(line.trim());
        }
        return containerIds;
    }

    private static String executeCommand(String command) throws Exception {
        Process process = new ProcessBuilder().command(command.split(" ")).redirectErrorStream(true).start();

        StringBuilder output = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed: " + command + "\nOutput: " + output);
        }

        return output.toString().trim();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        executeCommand("docker-compose -f " + DOCKER_COMPOSE_FILE + " down");
    }
	@Test
	public void test() throws Exception {
		// Modifica da qui:
		IApplMessage testRequest = CommUtils.buildRequest("tester", "testRequest", "testRequest(A)", "test_observer");
	    IApplMessage testAddrp = CommUtils.buildRequest("tester", "addrp", "addrp(1)", "wis");
		try {
            CommUtils.outmagenta("test_observer ======================================= ");
            while (connSupport == null) {
                connSupport = ConnectionFactory.createClientSupport(PROTOCOL, ADDRESS, PORT);
                CommUtils.outcyan("testfunzionale another connect attempt ");
                Thread.sleep(1000);
            }
            CommUtils.outcyan("CONNECTED to test_observer " + connSupport);
			IApplMessage replyAddrp = connSupport.request(testAddrp);
			CommUtils.outcyan("Iniviato: "+ testAddrp.toString());
            Thread.sleep(this.MAX_T);

			//

            IApplMessage reply = connSupport.request(testRequest);
            CommUtils.outcyan("test_observer reply=" + reply);
            String answer = reply.msgContent();
            String s = answer.substring(answer.indexOf('(') + 1, answer.lastIndexOf(')'));

            assertTrue(Boolean.parseBoolean(s)); 
        } catch (Exception e) {
            CommUtils.outred("test_observer ERROR " + e.getMessage());
            fail("testRequest " + e.getMessage());
        }
	}

}
