package ee.taltech.iti0301.hydra.networking;

import ee.taltech.iti0301.hydra.entity.old.Bullet;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;
import java.util.LinkedList;
import java.util.List;

public class ServerGame {
    
    private ClientGame clientGame;
    private TankBody tankBody;
    private TankBody enemy;
    private List<Bullet> bullets = new LinkedList<>();
    private static int playerCount;
    
    public ServerGame(ClientGame clientGame) {
        this.clientGame = clientGame;
    }
    
    public ServerGame(int playerCount) {
        ServerGame.playerCount = playerCount;
        for (int i = 1; i <= ServerGame.playerCount; i++) {
            if (i == 1) {
                tankBody = new TankBody(i, 75, "Player " + i);
            } else if (i == 2) {
                enemy = new TankBody(i, 675, "Player " + i));
        }
    }
    
    
    public void update() {
        tankBody = clientGame.getTankBody();
        enemy = clientGame.getEnemy();
        for (Bullet item : clientGame.getBullets()) {
            bullets.add(item);
        }
    }
    
    
    public TankBody getTankBody() {
        return tankBody;
    }
    
    public TankBody getEnemy() {
        return enemy;
    }
    
    public List<Bullet> getBullets() {
        return bullets;
    }
    
}

}
