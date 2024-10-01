package main.resources;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.utils.CommUtils;

public class VirtualLED implements IVirtualLED {
	private String vitualRobotIp;
	private ActorBasic owner;
	
	public VirtualLED(String vitualRobotIp, ActorBasic owner) {
		this.owner = owner;
		this.vitualRobotIp = vitualRobotIp;
	}

	@Override
	public boolean turnOn() {
		// Codice python per accendere led
		CommUtils.outblue("LED | Acceso");
		return true;
	}

	@Override
	public boolean turnOff() {
		CommUtils.outblue("LED | Spento");
		return false;
	}

	@Override
	public boolean blink() {
		CommUtils.outblue("LED | Blink");
		return false;
	}

}
