package ee.taltech.iti0301.hydra.session;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;
import ee.taltech.iti0301.hydra.networking.NetworkingGame;

import java.io.IOException;
import java.util.*;

public class GameSession {

    private final Client client;
    private TankBody playerTank = null;
    private final Map<Integer, TankBody> tanks = new HashMap<>();
    private final Map<Integer, Entity> entities = new HashMap<>();
    private final List<MovableEntity> movableEntities = new ArrayList<>();

    public GameSession(Client client) {
        this.client = client;
    }

    public void movePlayerTank(TankBody.Direction movementDirection, TankBody.Direction rotationDirection) {
        if (client.isConnected()) {
        } else {
            playerTank.setMovementDirection(movementDirection);
            playerTank.setRotationDirection(rotationDirection);
        }
    }

    public void start(int sessionId) throws IOException {
        client.connect(60000, NetworkingGame.GAME_SERVER_ADDRESS, NetworkingGame.GAME_TCP_PORT,
                NetworkingGame.GAME_UDP_PORT);
        NetworkingGame.ConnectionRequest request = new NetworkingGame.ConnectionRequest();
        request.sessionId = sessionId;
        client.sendTCP(request);
    }

    public void handleEntityUpdate(NetworkingGame.UpdateEntity update) {
        if (tanks.containsKey(update.id)) {
            TankBody tank = tanks.get(update.id);
            tank.setMovementDirection(TankBody.Direction.valueOf(update.movementDirection));
            tank.setRotationDirection(TankBody.Direction.valueOf(update.rotationDirection));
        } else {

        }
    }

    public TankBody getPlayerTank() {
        return playerTank;
    }

    public List<TankBody> getTanks() {
        return new ArrayList<>(tanks.values());
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entities.values());
    }

    public List<MovableEntity> getMovableEntities() {
        return movableEntities;
    }
}
