package main.resources;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class VirtualSonar implements IVirtualSonar{
	private String vitualRobotIp;
	private ActorBasic owner;
	
	private static final String ADDRESS = "127.0.0.1"; // Indirizzo del receiver
	private static final String PORT = "8021"; // Porta (modificare secondo necessità)
	private static final ProtocolType PROTOCOL = ProtocolType.tcp; // Protocollo da utilizzare
	private Interaction connSupport;
	
	public VirtualSonar(String vitualRobotIp, ActorBasic owner) {
		this.owner = owner;
		this.vitualRobotIp = vitualRobotIp;
		this.connSupport = ConnectionFactory.createClientSupport(PROTOCOL, ADDRESS, PORT);
	}
	
	public int read() {
		return 1;
	}

	
	

}
