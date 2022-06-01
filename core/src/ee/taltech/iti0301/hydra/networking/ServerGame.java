package ee.taltech.iti0301.hydra.networking;

import ee.taltech.iti0301.hydra.entity.FakeTank;
import ee.taltech.iti0301.hydra.entity.projectile.Projectile;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class ServerGame {
    
    private ClientGame clientGame;
    private List<FakeTank> tanks = new LinkedList<>();
    private List<Projectile> bullets = new LinkedList<>();
    private static int playerCount;
    private Random r = new Random();
    
    public ServerGame(ClientGame clientGame) {
        this.clientGame = clientGame;
    }
    
    public ServerGame(int playerCount) {
        try {
    
            ServerGame.playerCount = playerCount;
            for (int i = 1; i <= ServerGame.playerCount; i++) {
                if (i == 1) {
                    tanks.add(new FakeTank(i, r.nextInt(20), r.nextInt(20), 0));
                } else if (i == 2) {
                    tanks.add(new FakeTank(i, 200 - r.nextInt(20), 200 - r.nextInt(20), 180));
                }
            }
        } catch (ExceptionInInitializerError e) {
            System.out.println(1);
        }
    }
    
    
    public void update() {
        for (FakeTank fakeTank: clientGame.getFakeTanks()) {
            tanks.add(fakeTank);
        }
        for (Projectile item : clientGame.getProjectiles()) {
            bullets.add(item);
        }
    }
    
    
    public List<FakeTank> getTanks() {
        return tanks;
    }
    
    public List<Projectile> getBullets() {
        return bullets;
    }
}
