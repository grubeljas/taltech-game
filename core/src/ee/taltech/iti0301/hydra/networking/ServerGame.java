package ee.taltech.iti0301.hydra.networking;

import ee.taltech.iti0301.hydra.entity.fakeEntity;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class ServerGame {
    
    private ClientGame clientGame;
    private HashMap<Integer, fakeEntity> tanks = new HashMap<>();
    private List<fakeEntity> bullets = new LinkedList<>();
    private static int playerCount;
    private Random r = new Random();
    
    public ServerGame(ClientGame clientGame, HashMap<Integer, fakeEntity> tanks) {
        System.out.println("servergame again");
        this.clientGame = clientGame;
        this.tanks = tanks;
    }
    
    public ServerGame(int playerCount) {
        try {
            System.out.println("creating server game first time");
            ServerGame.playerCount = playerCount;
            System.out.println(ServerGame.playerCount);
            for (int i = 1; i <= ServerGame.playerCount; i++) {
                if (i == 1) {
                    tanks.put(i, new fakeEntity(i, r.nextInt(20), r.nextInt(20), 0));
                } else if (i == 2) {
                    tanks.put(i, new fakeEntity(i, 200 - r.nextInt(20), 200 - r.nextInt(20), 180));
                }
            }
        } catch (ExceptionInInitializerError e) {
            System.out.println(1);
        }
    }
    
    
    public void update() {
        fakeEntity newTank = clientGame.getFakeTank();
        System.out.println("NEW ID IS "+ newTank.getId());
        tanks.put(newTank.getId(), newTank);

        System.out.println(tanks);
        for (fakeEntity item : clientGame.getProjectiles()) {
            bullets.add(item);
        }
    }
    
    public HashMap<Integer, fakeEntity> getTanks() {
        return tanks;
    }
    
    public List<fakeEntity> getBullets() {
        return bullets;
    }
}
