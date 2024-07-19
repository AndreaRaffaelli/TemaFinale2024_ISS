package main.java.test;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;

//import it.unibo.ctxprodcons.MainCtxprodconsKt; //Solo in IntelliJ
//import it.unibo.ctxping.MainCtxpingKt;
//import it.unibo.ctxpong.MainCtxpongKt;
//import it.unibo.ctxreferee.MainCtxrefereeKt;
import unibo.basicomm23.utils.ConnectionFactory;



public class TestWis {
private static Interaction connSupport;

public static void showOutput(Process proc, String color){
	new Thread(){
		public void run(){
			try {
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			ColorsOut.outappl("Here is the standard output of the command:\n", color);
			while (true){
				String s = stdInput.readLine();
				if ( s != null ) ColorsOut.outappl( s, color );
			} 
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}.start();
}


public static void activateSystemUsingDeploy() { 
	Thread th = new Thread(){
		public void run(){
			try {
                Process p;
                if(System.getProperty("os.name")== "Linux") {
                    Runtime.getRuntime().exec("tar -xvf ./build/distribution/sprintuno-1.0.tar");
                    CommUtils.outmagenta("TestWis activateSystemUsingDeploy ");
                    p = Runtime.getRuntime().exec("./build/distribution/sprintuno-1.0/bin/sprintuno");
                    showOutput(p,ColorsOut.BLACK);
                } else {
                    Runtime.getRuntime().exec("tar -xvf ./build/distribution/sprintuno-1.0.tar");
                    CommUtils.outmagenta("TestWis activateSystemUsingDeploy ");
                    p = Runtime.getRuntime().exec("./build/distribution/sprintuno-1.0/bin/sprintuno.bat");
                    showOutput(p,ColorsOut.BLACK);
                }

				showOutput(p,ColorsOut.BLACK);
			} catch ( Exception e) {
				CommUtils.outred("TestWis activate ERROR " + e.getMessage());
			}
		}
	};
	th.start();
}  



public static void main(){
    activateSystemUsingDeploy();
}


}