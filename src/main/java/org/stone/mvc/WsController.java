package org.stone.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.stone.entity.WiselyMessage;
import org.stone.entity.WiselyResponse;

import java.security.Principal;

/**
 * Created by liulei on 12/13 0013.
 */
@Controller
public class WsController {

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public WiselyResponse say(WiselyMessage message) throws Exception{
        Thread.sleep(100);
        return new WiselyResponse("welcome, "+message.getName()+"!");
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void handleChat(Principal principal, String msg){
        if(principal.getName().equals("peter")){
            messagingTemplate.convertAndSendToUser("lily","/queue/notifications",principal.getName()+"-send:"+msg);
        }else {
            messagingTemplate.convertAndSendToUser("peter","/queue/notifications",principal.getName()+"-send:"+msg);
        }
    }
}
