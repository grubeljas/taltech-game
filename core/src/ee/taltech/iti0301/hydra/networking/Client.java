package ee.taltech.iti0301.hydra.networking;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import ee.taltech.iti0301.hydra.Hydra;
import ee.taltech.iti0301.hydra.entity.FakeEntity;
import ee.taltech.iti0301.hydra.screens.GameScreen;
import java.net.URI;
import java.nio.ByteBuffer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

public class Client extends WebSocketClient {
    private final Serializer<Message> serializer = new Serializer<>(Message.class);
    private Game game;
    private static GameScreen gameScreen;
    private ClientGame clientGame;
    private String clientId;
    private float time;
    private float messageSentTime;
    private float timeDifference = (float) 0.005;
    private Client client = this;
    
    
    public Client(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }
    
    public Client(URI serverURI) {
        super(serverURI);
    }
    
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send(serializer.encode(new Message("NEW CONNECTION")));
        System.out.println("new connection opened");
    }
    
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }
    
    @Override
    public void onMessage(String message) {
        final Message decoded = serializer.decode(message);
        if (decoded.getText().contains("Player ") && !decoded.getText().contains("disconnection") && !decoded.getText().contains("New id:")) {
            clientId = decoded.getText().split(" ")[1];
        }
        if (decoded.getText().contains("New id:")) {
            clientId = decoded.getText().split(":")[1];
        }
        if (decoded.getText().equals("Start game")) {
            System.out.println("Client has got a message to start game.");
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    gameScreen = createPlayScreen((Hydra) game);
                    clientGame = createClientGame();
                    gameScreen.setServerGame(decoded.getServerGame());
                    game.setScreen(gameScreen);
                }
            });
        }
        if (decoded.getText().equals("Broadcasting players data")) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    gameScreen.setServerGame(decoded.getServerGame());
                }
            });
        }

    }
    
    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }
    
    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }
    
    public GameScreen createPlayScreen(Hydra bomberman) {
        this.gameScreen = new GameScreen(bomberman, this);
        return gameScreen;
    }
    
    public ClientGame createClientGame() {
        clientGame = new ClientGame();
        gameScreen.setClientGame(clientGame);
        return clientGame;
    }
    
    public String getName() {
        return clientId;
    }
    
    public void sendMessageToServer() {
        if ((this.time - messageSentTime) > timeDifference) {
            sendMessageToServerClientData();
            clientGame = new ClientGame();
            gameScreen.setClientGame(clientGame);
            this.messageSentTime = this.time;
        }
    }
    
    public void sendMessageToServerToStartGame() {
        send(serializer.encode(new Message("Start game")));  // Kutsutakse välja lobbys nupu vajutusel
    }
    
    public void sendTankData() {
        System.out.println("SEND TANK COORD" + clientId);
        send(serializer.encode(new Message("Sending tank data from:" + clientId, new FakeEntity(gameScreen.getMyTank()))));
    }
    
    public void sendMessageToServerClientData() {
        send(serializer.encode(new Message("Sending client data", clientGame)));
    }
    
    public void sendMessageToServerGameOver() {
        send(serializer.encode(new Message("Game over", clientGame)));
        //Lobby.getConnectedPlayers().clear();
        close();
    }
    
    public void update(float dt) {
        this.time += dt;
        this.sendMessageToServer();
        /**if (gameScreen.isGameOver()) {
            sendMessageToServerGameOver();
        }**/
    }
    
    public void setClientGame(ClientGame clientGame) {
        this.clientGame = clientGame;
    }
    
    public static GameScreen getPlayScreen() {
        return gameScreen;
    }
    
    
    public void setGameToClient(Game game) {
        this.game = game;
    }
    
    public void setPlayScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
