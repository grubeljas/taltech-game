package ee.taltech.iti0301.hydra.networking;


public class Message {
    private final String text;
    private ServerGame serverGame;
    private ClientGame clientGame;
    
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
