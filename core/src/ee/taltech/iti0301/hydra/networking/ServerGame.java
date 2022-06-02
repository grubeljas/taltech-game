package ee.taltech.iti0301.hydra.networking;

import ee.taltech.iti0301.hydra.entity.fakeEntity;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class ServerGame {
    
    private ClientGame clientGame;
    private HashMap<Integer, fakeEntity> tanks = new HashMap<>();
    private HashMap<Integer, fakeEntity> turrets = new HashMap<>();
    private List<fakeEntity> bullets = new LinkedList<>();
    private static int playerCount;
    private Random r = new Random();
    
    public ServerGame(ClientGame clientGame,
                      HashMap<Integer, fakeEntity> tanks,
                      HashMap<Integer, fakeEntity> turrets) {
        System.out.println("servergame again");
        this.clientGame = clientGame;
        this.tanks = tanks;
        this.turrets = turrets;
    }
    
    public ServerGame(int playerCount) {
        try {
            System.out.println("creating server game first time");
            ServerGame.playerCount = playerCount;
            System.out.println(ServerGame.playerCount);
            for (int i = 1; i <= ServerGame.playerCount; i++) {
                if (i == 1) {
                    tanks.put(i, new fakeEntity(i, r.nextInt(20), r.nextInt(20), 45));
                    turrets.put(i, new fakeEntity(i, tanks.get(i).getX(), tanks.get(i).getY(), 0));
                } else if (i == 2) {
                    tanks.put(i, new fakeEntity(i, 200 - r.nextInt(20), 200 - r.nextInt(20), 225));
                    turrets.put(i, new fakeEntity(i, tanks.get(i).getX(), tanks.get(i).getY(),225));
                }
            }
        } catch (ExceptionInInitializerError e) {
            System.out.println(1);
        }
    }
    
    
    public void update() {
        fakeEntity newTank = clientGame.getFakeTank();
        fakeEntity newTurret = clientGame.getFakeTurret();
        System.out.println("NEW ID IS "+ newTurret.toString());
        tanks.put(newTank.getId(), newTank);
        turrets.put(newTurret.getId(), newTurret);

        System.out.println(tanks);
        for (fakeEntity item : clientGame.getProjectiles()) {
            bullets.add(item);
        }
    }
    
    public HashMap<Integer, fakeEntity> getTanks() {
        return tanks;
    }

    public HashMap<Integer, fakeEntity> getTurrets() {
        return turrets;
    }
    
    public List<fakeEntity> getBullets() {
        return bullets;
    }
}
