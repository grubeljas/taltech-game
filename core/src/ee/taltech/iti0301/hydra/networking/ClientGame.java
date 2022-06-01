package ee.taltech.iti0301.hydra.networking;

import ee.taltech.iti0301.hydra.entity.FakeTank;
import ee.taltech.iti0301.hydra.entity.projectile.Projectile;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;
import java.util.LinkedList;
import java.util.List;

public class ClientGame {

    private List<FakeTank> fakeTanks = new LinkedList<>();
    private List<Projectile> Projectiles = new LinkedList<>();
    
    public List<FakeTank> getFakeTanks() {
        return fakeTanks;
    }
    
    public List<Projectile> getProjectiles() {
        return Projectiles;
    }
    
    public void addTankBody() {
        List<TankBody> tankBodies = Client.getPlayScreen().getAllTanks();

        for (TankBody tankBody: tankBodies) {
            fakeTanks.add(new FakeTank(tankBody));
        }

    }
    public void addProjectiles() {
        this.Projectiles = Client.getPlayScreen().getProjectiles();
    }
    
    public void clearAllInfo() {
        this.Projectiles.clear();
        this.fakeTanks.clear();
    }
    
}
