package ee.taltech.iti0301.hydra.networking;

import ee.taltech.iti0301.hydra.entity.FakeEntity;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class ServerGame {
    
    private ClientGame clientGame;
    private HashMap<Integer, FakeEntity> tanks = new HashMap<>();
    private HashMap<Integer, FakeEntity> turrets = new HashMap<>();
    private List<FakeEntity> bullets = new LinkedList<>();
    private static int playerCount;
    private Random r = new Random();
    
    public ServerGame(ClientGame clientGame,
                      HashMap<Integer, FakeEntity> tanks,
                      HashMap<Integer, FakeEntity> turrets) {
        
        this.clientGame = clientGame;
        this.tanks = tanks;
        this.turrets = turrets;
    }
    
    public ServerGame(int playerCount) {
        try {
            
            ServerGame.playerCount = playerCount;
            
            for (int i = 1; i <= ServerGame.playerCount; i++) {
                if (i == 1) {
                    tanks.put(i, new FakeEntity(i, r.nextInt(20), r.nextInt(20), 45));
                    turrets.put(i, new FakeEntity(i, tanks.get(i).getX(), tanks.get(i).getY(), 0));
                } else if (i == 2) {
                    tanks.put(i, new FakeEntity(i, 200 - r.nextInt(20), 200 - r.nextInt(20), 225));
                    turrets.put(i, new FakeEntity(i, tanks.get(i).getX(), tanks.get(i).getY(),225));
                }
            }
        } catch (ExceptionInInitializerError e) {
            System.out.println(1);
        }
    }
    
    
    public void update() {
        FakeEntity newTank = clientGame.getFakeTank();
        FakeEntity newTurret = clientGame.getFakeTurret();
        tanks.put(newTank.getId(), newTank);
        turrets.put(newTurret.getId(), newTurret);

        System.out.println(tanks);
        for (FakeEntity item : clientGame.getProjectiles()) {
            bullets.add(item);
        }
    }
    
    public HashMap<Integer, FakeEntity> getTanks() {
        return tanks;
    }

    public HashMap<Integer, FakeEntity> getTurrets() {
        return turrets;
    }
    
    public List<FakeEntity> getBullets() {
        return bullets;
    }
}
