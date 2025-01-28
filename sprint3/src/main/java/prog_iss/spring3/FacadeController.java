package prog_iss.spring3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import unibo.basicomm23.utils.CommUtils;
 

 

@Controller
public class FacadeController {
    String protocol="tcp";

    @Value("${wis.ip}")
    String wisip = "localhost";
    @Value("${wis.port}")
    String wisport;
    @Value("${spring.application.name}")
    String appNameOld;  //vedi application.properties 
    protected String mainPage = "Fcd24SGui"; //TODO: "WebRobot24Gui";  

    @Autowired
    public FacadeController(){
        CommUtils.outgreen (" --- FacadeController | STARTS " );
        System.out.println(wisip);
        System.out.println(wisport);
        new FacadeBuilder();
    }
 

    protected String buildThePage(Model viewmodel) {
        setConfigParams(viewmodel);
        return mainPage;
    }
    protected void setConfigParams(Model viewmodel){
 
    }
    @GetMapping("/")
    public String homePage(Model viewmodel) {
        //CommUtils.outcyan("FacadeController homePage appNameOld=" + appNameOld);
        viewmodel.addAttribute("appname", ApplSystemInfo.appName);
        viewmodel.addAttribute("wisip", wisip);  
        String dir = System.getProperty("user.dir");
        CommUtils.outgreen (" --- FacadeController | entry dir= "+dir  );
        return buildThePage(viewmodel); //"qakFacadeGUI";
    }
    

    @GetMapping("/test")
    public String testPage(Model viewmodel) {
        //CommUtils.outcyan("FacadeController homePage appNameOld=" + appNameOld);
        viewmodel.addAttribute("appname", ApplSystemInfo.appName);
        viewmodel.addAttribute("wisip", wisip);  
        String dir = System.getProperty("user.dir");
        CommUtils.outgreen (" --- FacadeController | entry dir= "+dir  );
        return "Fcd24SGuiTest"; //"qakFacadeGUI";
    }

    @PostMapping("/addrp")
    public String addrp(Model viewmodel, @RequestParam String ipaddr  ){
        wisip = ipaddr;
        System.out.println("ServiceFacadeController | setrobotip:" + ipaddr );
        viewmodel.addAttribute("wisip", wisip);
        Utils.connectWithRobotUsingTcp(ipaddr);
        return buildThePage(viewmodel);
    }
     
}
