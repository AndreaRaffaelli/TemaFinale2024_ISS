package main.resources;

import org.json.simple.JSONObject;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ApplAbstractObserver;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
import unibo.basicomm23.ws.WsConnection;


public class LedFactory  { //extends ApplAbstractObserver {

//    Factory method
    public static IVirtualLED create( String address, String port ) {
    	return new VirtualLED( address, port );
    }

}
