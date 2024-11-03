package main.resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

import unibo.basicomm23.utils.CommUtils;

public class LEDFisico implements ILED {
    private Process p;
    private String ledOnPath = "../../LedOn.py";
    private String ledOffPath = "../../LedOff.py";
    private String ledBlinkPath = "../../LedBlink.py";
	/*
	 * private String ledBlinkPath = "src/main/resources/LedBlink.py";
	 */

    public LEDFisico() throws IOException {

        // Creazione di un oggetto File
        File file1 = new File(ledOnPath);
        File file2 = new File(ledOffPath);
        File file3 = new File(ledBlinkPath);
 
		if (!file1.exists() || !file2.exists() || !file3.exists()) {
			throw new IOException();
		}
    }

    @Override
    public boolean turnOn() {
    	if(p!= null) {
    		p.destroy();
    	}
    	try {
			this.p = Runtime.getRuntime().exec("python3 ../../LedOn.py");
			CommUtils.outcyan("LED | Acceso");
		} catch (IOException e) {
			return false;
		}
        return true;
    }

    @Override
    public boolean turnOff() {
    	if(p!= null) {
    		p.destroy();
    	}
    	try {
			this.p = Runtime.getRuntime().exec("python3 ../../LedOff.py");
			CommUtils.outcyan("LED | Spento");
		} catch (IOException e) {
			return false;
		}
        return true;    
        
    }

    @Override
    public boolean turnBlink() {
    	if(p!= null) {
    		p.destroy();
    	}
    	try {
			this.p = Runtime.getRuntime().exec("python3 ../../LedBlink.py");
			CommUtils.outcyan("LED | Blink");
		} catch (IOException e) {
			return false;
		}
        return true;       
        
    }


}
