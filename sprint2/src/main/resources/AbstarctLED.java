package main.resources;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import org.json.simple.JSONObject;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ApplAbstractObserver;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
import unibo.basicomm23.ws.WsConnection;


public abstract class AbstarctLED extends ApplAbstractObserver {

    //Factory method
    public static VirtualLED create( String vitualRobotIp, ActorBasic owner ) {
    	return new VirtualLED( vitualRobotIp, owner );
    }
	@Override
	public void turnOn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void blink() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String value) {
		// TODO Auto-generated method stub
		
	}
	

}
