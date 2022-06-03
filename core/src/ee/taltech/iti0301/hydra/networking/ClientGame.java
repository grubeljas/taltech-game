package ee.taltech.iti0301.hydra.networking;

import ee.taltech.iti0301.hydra.entity.FakeEntity;
import ee.taltech.iti0301.hydra.entity.projectile.Projectile;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ClientGame {

    private FakeEntity fakeTank;
    private HashMap<String, Boolean> isDead = new HashMap<>();
    private FakeEntity fakeTurret;
    private List<FakeEntity> projectiles = new LinkedList<>();
    
    public FakeEntity getFakeTank() {
        return fakeTank;
    }

    public FakeEntity getFakeTurret() {
        return fakeTurret;
    }
    
    public List<FakeEntity> getProjectiles() {
        return projectiles;
    }
    
    public void addTankBody() {
        fakeTank = new FakeEntity(Client.getPlayScreen().getMyTank());
        fakeTurret = new FakeEntity(Client.getPlayScreen().getMyTank().getTurret());
    }
    public void addProjectiles() {
        List<Projectile> fakeProjectiles = Client.getPlayScreen().getProjectiles();
    
        for (Projectile projectile: fakeProjectiles) {
            projectiles.add(new FakeEntity(projectile));
        }
    }
    
    public void addDead(String id) {
        isDead.put(id, true);
    }
    
    public void clearAllInfo() {
        this.projectiles.clear();
        this.fakeTank = null;
    }
}
