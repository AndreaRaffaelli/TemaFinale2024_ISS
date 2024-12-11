package main.java.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GlobalTest {

	private static final List<String> containerIds = new ArrayList<>();
	private static final String[] containerConfigs = { 
			"sprint1:latest:8022:8022", // Image, with port mapping
			"sprint2:latest:8021:8021", // Image, with port mapping
			"docker.io/natbodocker/basicrobot24:1.0:8020:8020", // Image, with port mapping
			"docker.io/natbodocker/virtualrobotdisi23:1.0:8090:8091" // Image, with multiple port mappings
	};

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		for (String config : containerConfigs) {
			String containerId = startContainer(config);
			System.out.println("launched config "+ config + " " + containerId);
			if (containerId != null) {
				containerIds.add(containerId.trim());
			}
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		for (String containerId : containerIds) {
			stopAndRemoveContainer(containerId);
		}
	}

	private static String startContainer(String config) throws Exception {
		String[] parts = config.split(":");
		String image = parts[0];
		String version=parts[1];
		String hostPort = parts[2];
		String containerPort = parts[3];

		// Costruisci il comando docker run per un solo port mapping
		String command = String.format("docker run -d --rm -p %s:%s %s:%s", hostPort, containerPort, image, version);
		System.out.println(command);
		// Esegui il comando
		return executeCommand(command);
	}

	private static void stopAndRemoveContainer(String containerId) throws Exception {
		executeCommand("docker stop " + containerId);
		executeCommand("docker rm " + containerId);
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

	@Test
	public void testContainersRunning() throws Exception {
	    for (String containerId : containerIds) {
	        String command = "docker ps -q --filter id=" + containerId; // Corrected line
	        String result = executeCommand(command);
	        assertTrue("Container is not running: " + containerId, result.contains(containerId));
	    }
	}
}
