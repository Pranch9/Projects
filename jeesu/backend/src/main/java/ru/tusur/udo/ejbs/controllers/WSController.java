package ru.tusur.udo.ejbs.controllers;

import ru.tusur.udo.ejbs.servises.SensorsMonitoring;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/sensors")

@Stateless
public class WSController {

    private Session session;

    @Inject
    SensorsMonitoring sensorsMonitoring;

    @OnOpen
    public void connect(Session session) {
        this.session = session;
    }

    @OnClose
    public void close() {
        this.session = null;
    }

    private void handleMessage(String sensorsSnapshot) {
        if (session != null) {
            session.getAsyncRemote().sendText(sensorsSnapshot);
        }
    }

    @Schedule(hour = "*", minute = "*", second = "0/1")
    public void handleWebsocket() {
        handleMessage(sensorsMonitoring.retrieveSnapshot());
    }
}
