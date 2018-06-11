package websocket;

import com.google.gson.Gson;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Martijn van der Pol on 11-06-18
 **/

@ServerEndpoint("/reload")
public class ReloadWebSocketServer {

    private static HashMap<String, Session> sessionSet = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        sessionSet.put(session.getId(), session);
        System.out.println("Session: " + session.getId() + " connected");
    }

    @OnMessage
    public void OnMessage(Session session, String message) {
        System.out.println(session.getId() + " said: " + message);
    }

    @OnClose
    public void OnClose(Session session) {
        sessionSet.remove(session.getId());
    }

    public static void broadcastMessage(String message) {
        Gson gson = new Gson();
        for (HashMap.Entry<String, Session> entry : sessionSet.entrySet()) {
            try {
                entry.getValue().getBasicRemote().sendText(gson.toJson(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
