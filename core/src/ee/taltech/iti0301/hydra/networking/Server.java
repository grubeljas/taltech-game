package ee.taltech.iti0301.hydra.networking;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Server extends WebSocketServer {
    private final Serializer<Message> serializer = new Serializer<>(Message.class);
    private ServerGame serverGame;
    private Map<WebSocket, String> clientsMap = new HashMap<>();
    private int playerCount = 0;
    private List<String> connectedPlayerNames = new ArrayList<>();
    
    private boolean inLobby = true;
    private boolean gameEnded = true;
    
    
    public Server(InetSocketAddress address) {
        super(address);
    }
    
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send(serializer.encode(new Message("Welcome to the server!"))); //This method sends a message to the new client
        gameEnded = false;
        if (playerCount < 4) {
            System.out.println(playerCount);
            playerCount++;
            clientsMap.put(conn, "Player " + playerCount);
            connectedPlayerNames.add("Player " + playerCount);
            conn.send(serializer.encode(new Message("Player " + playerCount)));
            broadcast(serializer.encode(new Message("new connection: " + handshake.getResourceDescriptor())));
            
        }
        broadcast(serializer.encode(new Message("connected players", connectedPlayerNames))); // Tekitas enne probleeme
        //This method sends a message to all clients connected
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
    }
    
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
        if (inLobby && !gameEnded) {
            // vähendan playercounti ning kustutan connectedplayerNamesist ära
            playerCount--;
            connectedPlayerNames.clear();
            //connectedPlayerNames.remove(clientsMap.get(conn));
            // Võtan mängija playeri numbri ning kustutan ta clientmapist ära
            int disconnectedPlayerNumber = Integer.parseInt(clientsMap.get(conn).split(" ")[1]);
            clientsMap.remove(conn);
            // Muudan nende mängijate id-d ühe võrra väiksemaks, mille id-d olid suuremad kui disconnectinud mängija oma, ning saadan need välja.
            for (Map.Entry<WebSocket, String> entry : clientsMap.entrySet()) {
                int existingPlayerNumber = Integer.parseInt(clientsMap.get(entry.getKey()).split(" ")[1]);
                if (existingPlayerNumber > disconnectedPlayerNumber) {
                    existingPlayerNumber--;
                    clientsMap.put(entry.getKey(), "Player " + existingPlayerNumber);
                    entry.getKey().send(serializer.encode(new Message("New id:" + clientsMap.get(entry.getKey()))));
                }
                connectedPlayerNames.add("Player " + existingPlayerNumber);
            }
            broadcast(serializer.encode(new Message("connected players", connectedPlayerNames)));
        } else {
            broadcast(serializer.encode(new Message("disconnection:" + clientsMap.get(conn))));
        }
    }
    
    @Override
    public void onMessage(WebSocket conn, String message) {
        final Message decoded = serializer.decode(message);
        if (decoded.getText().equals("Start game")) {
            serverGame = new ServerGame(playerCount);
            sendMessagesToClientsToStartGame();
        }
        if (decoded.getText().equals("Sending client data")) {
            if (decoded.getClientGame() != null) {
                serverGame = new ServerGame(decoded.getClientGame());
                serverGame.update();
                sendMessagesToClientsPlayerData();
            }
        }
        if (decoded.getText().equals("Game over")) {
            System.out.println("GAME IS OVER");
            gameEnded = true;
            resetServer();
        }
        
    }
    
    public void sendMessagesToClientsToStartGame() {
        broadcast(serializer.encode(new Message("Start game", this.serverGame)));
    }
    
    public void sendMessagesToClientsPlayerData() {
        broadcast(serializer.encode(new Message("Broadcasting players data", this.serverGame)));
    }
    
    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println("received ByteBuffer from " + conn.getRemoteSocketAddress());
    }
    
    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex);
    }
    
    @Override
    public void onStart() {
        System.out.println("server started successfully");
    }
    
    
    public static void main(String[] args) {
        String host = "10.192.244.9"; // 193.40.255.17
        int port = 5000;
        
        WebSocketServer server = new Server(new InetSocketAddress(host, port));
        server.run();
    }
    
    public void resetServer() {
        serverGame = null;
        clientsMap.clear();
        inLobby = true;
        playerCount = 0;
        connectedPlayerNames.clear();
    }
}
