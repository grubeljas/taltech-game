package ee.taltech.iti0301.hydra.networking;

import ee.taltech.iti0301.hydra.entity.old.Bullet;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;
import java.util.LinkedList;
import java.util.List;

public class ClientGame {
    
    private TankBody tankBody;
    private List<Bullet> bullets = new LinkedList<>();
    
    private TankBody enemy;
    
    public TankBody getTankBody() {
        return tankBody;
    }
    
    public List<Bullet> getBullets() {
        return bullets;
    }
    
    public TankBody getEnemy() {
        return enemy;
    }
    
    public void addTankBody() {
        this.tankBody = Client.getPlayScreen().getTankBody();
    }
    
    public void addEnemy() {
        this.enemy = Client.getPlayScreen().getEnemy();
    }
    
    public void addBullets() {
        this.bullets = Client.getPlayScreen().getBullets();
    }
    
    public void clearAllInfo() {
        this.bullets.clear();
        this.tankBody = null;
        this.enemy = null;
    }
    
}
