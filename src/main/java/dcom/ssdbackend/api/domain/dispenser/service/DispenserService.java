package dcom.ssdbackend.api.domain.dispenser.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.dto.DispenserResponseDto;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.domain.drinker.Drinker;
import dcom.ssdbackend.api.domain.drinker.dto.DrinkerResponseDto;
import dcom.ssdbackend.api.domain.drinker.service.DrinkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
@ServerEndpoint(value="/ws/socket")
public class DispenserService {

    private final DispenserRepository dispenserRepository;

    private final DrinkerService drinkerService;
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

    public DispenserResponseDto.DispenserInfo dispenserInfo(){
        String dispenserId = drinkerService.getAuthTokenInHeader();
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();
        return DispenserResponseDto.DispenserInfo.of(dispenser);
    }

    public void dispenserStart(){
        String dispenserId = drinkerService.getAuthTokenInHeader();
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();

            dispenser.setStarted(true);

            dispenser.builder()
                    .id(dispenser.getId())
                    .started(dispenser.isStarted())
                    .build();

            dispenserRepository.save(dispenser);
    }

    public void dispenserStop(){
        String dispenserId = drinkerService.getAuthTokenInHeader();
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();

        dispenser.setStarted(false);

        dispenser.builder()
                .id(dispenser.getId())
                .started(dispenser.isStarted())
                .build();

        dispenserRepository.save(dispenser);
    }
}
