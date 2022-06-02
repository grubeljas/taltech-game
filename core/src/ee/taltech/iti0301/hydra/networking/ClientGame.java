package ee.taltech.iti0301.hydra.networking;

import ee.taltech.iti0301.hydra.entity.fakeEntity;
import ee.taltech.iti0301.hydra.entity.projectile.Projectile;
import java.util.LinkedList;
import java.util.List;

public class ClientGame {

    private fakeEntity fakeTank;
    private fakeEntity fakeTurret;
    private List<fakeEntity> projectiles = new LinkedList<>();
    
    public fakeEntity getFakeTank() {
        return fakeTank;
    }

    public fakeEntity getFakeTurret() {
        return fakeTurret;
    }
    
    public List<fakeEntity> getProjectiles() {
        return projectiles;
    }
    
    public void addTankBody() {
        fakeTank = new fakeEntity(Client.getPlayScreen().getMyTank());
        fakeTurret = new fakeEntity(Client.getPlayScreen().getMyTank().getTurret());
        System.out.println("CLIENT GAME TANK ID IS "+ fakeTank.getId());
    }
    public void addProjectiles() {
        List<Projectile> fakeProjectiles = Client.getPlayScreen().getProjectiles();
    
        for (Projectile projectile: fakeProjectiles) {
            projectiles.add(new fakeEntity(projectile));
        }
    }
    
    public void clearAllInfo() {
        this.projectiles.clear();
        this.fakeTank = null;
    }
}
