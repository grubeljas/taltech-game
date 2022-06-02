package ee.taltech.iti0301.hydra.networking;


import ee.taltech.iti0301.hydra.entity.fakeEntity;
import java.util.List;

public class Message {
    private final String text;
    private ServerGame serverGame;
    private ClientGame clientGame;
    
    private fakeEntity fakeEntity;
    
    private List<String> playerNames;
    
    public Message(String text) {
        this.text = text;
        this.serverGame = null;
    }
    
    public Message(String text, ServerGame serverGame) {
        this.text = text;
        this.serverGame = serverGame;
    }
    
    public Message(String text, ClientGame clientGame) {
        this.text = text;
        this.clientGame = clientGame;
    }
    
    public Message(String text, List<String> playerNames) {
        this.text = text;
        this.playerNames = playerNames;
    }
    
    public Message(String text, fakeEntity fakeEntity) {
        this.text = text;
        this.fakeEntity = fakeEntity;
    }
    
    public String getText() {
        return text;
    }
    
    public ServerGame getServerGame() {
        return serverGame;
    }
    
    public ClientGame getClientGame() {
        return clientGame;
    }
}
