package main.resources;

import java.io.IOException;

import unibo.basicomm23.utils.ConnectionFactory;

public class LEDFisico implements ILED{
	private  Process p;
	
	public LEDFisico() {}


	@Override
	public boolean turnOn() {
		try {
			this.p       = Runtime.getRuntime().exec("python3 src/main/resources/sonar.py");
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean turnOff() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean turnBlink() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
