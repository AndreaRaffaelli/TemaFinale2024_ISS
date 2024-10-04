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


public class SonarFactory  { //extends ApplAbstractObserver {

//    Factory method
    public static IVirtualSonar create( String vitualRobotIp, ActorBasic owner ) {
    	return new VirtualSonar( vitualRobotIp, owner );
    }

}
