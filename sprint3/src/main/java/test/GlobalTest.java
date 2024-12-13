package main.java.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GlobalTest {

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
}
