package dcom.ssdbackend.api.domain.drinker.service;

import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@ServerEndpoint("/ws/socket/drinker")
public class DrinkerSocketService {

    private static Set<Session> clients =
            Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) throws Exception{
        if(!clients.contains(session)) {
            clients.add(session);
            System.out.println("Open Session : " + session.toString());
            session.getBasicRemote().sendText(session.toString());
        }
        else {
            System.out.println("Already Opened Session : " + session.toString());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws Exception{
        System.out.println("Sender : " + session.toString() + " / Receive Message : " + message);
        for(Session s : clients) {
            System.out.println("Receive : " + s.toString() + " / Receive Message : " + message);
            s.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session) throws Exception{
        System.out.println("Close Session : " + session.toString());
        clients.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable thr) {}
}
