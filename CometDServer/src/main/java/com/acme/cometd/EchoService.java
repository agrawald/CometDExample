package com.acme.cometd;

import org.cometd.annotation.Listener;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by Dheeraj Agrawal on 15/05/15.
 */
@javax.inject.Named // Tells Spring that this is a bean
@javax.inject.Singleton // Tells Spring that this is a singleton
@Service("echoService")
public class EchoService
{
    @Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;

    @PostConstruct
    public void init()
    {
        System.out.println("Echo Service Initialized");
    }

    @Listener("/echo")
    public void echo(ServerSession remote, ServerMessage.Mutable message)
    {
        String channel = message.getChannel();
        Object data = message.getData();
        remote.deliver(serverSession, channel, data);
    }
}

