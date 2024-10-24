package main.resources;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class VirtualLED implements ILED {
	

	private static final ProtocolType PROTOCOL = ProtocolType.tcp; // Protocollo da utilizzare
	private Interaction connSupport;
	
	public VirtualLED(String address, String port) {
		this.connSupport = ConnectionFactory.createClientSupport(PROTOCOL, address, port);
	}

	@Override
	public boolean turnOn()  {
		CommUtils.outcyan("LED | Acceso");
		var msg = CommUtils.buildDispatch("LED", "info", "info(virtualLED,led,on)", "test_observer");

		try {
			connSupport.forward(msg);
		} catch (Exception e) {
			CommUtils.outred("Fatal error led");
			return false;
		}
		return true;
	}

	@Override
	public boolean turnOff() {
		CommUtils.outcyan("LED | Spento");
		var msg = CommUtils.buildDispatch("LED", "info", "info(virtualLED,led,off)", "test_observer");

		try {
			connSupport.forward(msg);
		} catch (Exception e) {
			CommUtils.outred("Fatal error led");
			return false;
		}
		return true;
	}

	@Override
	public boolean turnBlink() {
		CommUtils.outcyan("LED | Blink");
		var msg = CommUtils.buildDispatch("LED", "info", "info(virtualLED,led,blink)", "test_observer");
		try {
			connSupport.forward(msg);
		} catch (Exception e) {
			CommUtils.outred("Fatal error led");
			return false;
		}
		return true;
	}

}
