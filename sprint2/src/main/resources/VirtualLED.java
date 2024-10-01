package main.resources;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class VirtualLED implements IVirtualLED {
	private String vitualRobotIp;
	private ActorBasic owner;
	
	private static final String ADDRESS = "127.0.0.1"; // Indirizzo del receiver
	private static final String PORT = "8021"; // Porta (modificare secondo necessità)
	private static final ProtocolType PROTOCOL = ProtocolType.tcp; // Protocollo da utilizzare
	private Interaction connSupport;
	
	public VirtualLED(String vitualRobotIp, ActorBasic owner) {
		this.owner = owner;
		this.vitualRobotIp = vitualRobotIp;
		this.connSupport = ConnectionFactory.createClientSupport(PROTOCOL, ADDRESS, PORT);
	}

	@Override
	public boolean turnOn()  {
		// Codice python per accendere led
		CommUtils.outblue("LED | Acceso");
		var msg = CommUtils.buildDispatch("LED", "info", "info(ledAcceso)", "test_observer");
		try {
			connSupport.forward(msg);
		} catch (Exception e) {
			CommUtils.outred("Fatal error led");
		}
		return true;
	}

	@Override
	public boolean turnOff() {
		CommUtils.outblue("LED | Spento");
		var msg = CommUtils.buildDispatch("LED", "info", "info(ledSpento)", "test_observer");
		try {
			connSupport.forward(msg);
		} catch (Exception e) {
			CommUtils.outred("Fatal error led");
		}
		return false;
	}

	@Override
	public boolean blink() {
		CommUtils.outblue("LED | Spento");
		var msg = CommUtils.buildDispatch("LED", "info", "info(ledBlink)", "test_observer");
		try {
			connSupport.forward(msg);
		} catch (Exception e) {
			CommUtils.outred("Fatal error led");
		}
		return false;
	}

}
