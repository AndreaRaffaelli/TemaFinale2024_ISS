package main.resources;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
import java.util.Random;

public class VirtualSonar implements IVirtualSonar{

	private static final ProtocolType PROTOCOL = ProtocolType.tcp; // Protocollo da utilizzare
	private Interaction connSupport;
	
	public VirtualSonar(String address, String port) {
		this.connSupport = ConnectionFactory.createClientSupport(PROTOCOL, address, port);

	}
	
	public boolean read() {
		Random random = new Random();
        int randomNumber = random.nextInt(10) + 1; // genera un numero tra 1 e 10
		var msg = CommUtils.buildDispatch("SONAR", "info", "info("+randomNumber+")", "test_observer");			
		try {
			connSupport.forward(msg);
		} catch (Exception e) {
			CommUtils.outred("Fatal error sonar");
			return false;
		}
		return true;
	}

	
	

}
