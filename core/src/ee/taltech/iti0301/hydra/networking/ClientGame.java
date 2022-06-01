package ee.taltech.iti0301.hydra.networking;

import ee.taltech.iti0301.hydra.entity.projectile.Projectile;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;
import java.util.LinkedList;
import java.util.List;

public class ClientGame {
    
    private TankBody tankBody;
    private List<Projectile> Projectiles = new LinkedList<>();
    
    public TankBody getTankBody() {
        return tankBody;
    }
    
    public List<Projectile> getProjectiles() {
        return Projectiles;
    }
    
    public void addTankBody() {
        this.tankBody = Client.getPlayScreen().getMyTank();
    }
    public void addProjectiles() {
        this.Projectiles = Client.getPlayScreen().getProjectiles();
    }
    
    public void clearAllInfo() {
        this.Projectiles.clear();
        this.tankBody = null;
    }
    
}
