package demo.cometd;

import org.cometd.bayeux.Channel;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.LongPollingTransport;
import org.eclipse.jetty.client.HttpClient;

import java.net.UnknownHostException;

/**
 * Created by Dheeraj Agrawal on 14/05/15.
 */
public class CometDClient {

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();
// Here setup Jetty's HttpClient, for example:
// httpClient.setMaxConnectionsPerAddress(2);
        httpClient.start();

        LongPollingTransport transport = new LongPollingTransport(null, httpClient);
        BayeuxClient client = new BayeuxClient("http://localhost:8080/cometd", transport) {
            public void onFailure(Throwable x, Message[] messages) {
                System.out.println("Failed");
            }
        };

        client.getChannel(Channel.META_HANDSHAKE).addListener(new ClientSessionChannel.MessageListener() {
                public void onMessage(ClientSessionChannel channel, Message message) {
                    System.out.println("META HANDSHAKE");
                }
        });
        client.handshake();
    }

}
