package main.resources;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class VirtualLED implements IVirtualLED {
	

	private static final ProtocolType PROTOCOL = ProtocolType.tcp; // Protocollo da utilizzare
	private Interaction connSupport;
	
	public VirtualLED(String address, String port) {
		this.connSupport = ConnectionFactory.createClientSupport(PROTOCOL, address, port);
	}

	@Override
	public boolean turnOn()  {
		CommUtils.outblue("LED | Acceso");
		var msg = CommUtils.buildDispatch("LED", "info", "info(ledAcceso)", "test_observer");
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
		CommUtils.outblue("LED | Spento");
		var msg = CommUtils.buildDispatch("LED", "info", "info(ledSpento)", "test_observer");
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
		CommUtils.outblue("LED | Blink");
		var msg = CommUtils.buildDispatch("LED", "info", "info(ledBlink)", "test_observer");
		try {
			connSupport.forward(msg);
		} catch (Exception e) {
			CommUtils.outred("Fatal error led");
			return false;
		}
		return true;
	}

}
