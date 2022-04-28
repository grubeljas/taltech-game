package ee.taltech.iti0301.hydra.session;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;
import ee.taltech.iti0301.hydra.networking.NetworkingGame;

import static ee.taltech.iti0301.hydra.networking.NetworkingGame.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameSession {

    private final Client client;
    private TankBody playerTank = null;
    private final List<TankBody> tanks = new ArrayList<>();
    private final List<Entity> entities = new ArrayList<>();
    private final List<MovableEntity> movableEntities = new ArrayList<>();

    public GameSession(Client client) {
        this.client = client;

        NetworkingGame.register(client);
        client.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object object) {
            }
        });


        try {
            client.connect(60000, GAME_SERVER_ADDRESS, GAME_TCP_PORT, GAME_UDP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            playerTank = addTank(10, 10 , 10);
        }
    }

    private TankBody addTank(int tankId, int x, int y) {
        TankBody tank = new TankBody(tankId, x, y, 0);
        tanks.add(tank);
        movableEntities.add(tank);
        entities.add(tank);
        return tank;
    }

    private TankBody getTankById(int tankId) {
        for (TankBody tank : tanks) {
            if (tank.getId() == tankId) {
                return tank;
            }
        }
        return null;
    }

    public void movePlayerTank(TankBody.Direction movementDirection, TankBody.Direction rotationDirection) {
        if (client.isConnected()) {
        } else {
            playerTank.setMovementDirection(movementDirection);
            playerTank.setRotationDirection(rotationDirection);
        }
    }

    public TankBody getPlayerTank() {
        return playerTank;
    }

    public List<TankBody> getTanks() {
        return tanks;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<MovableEntity> getMovableEntities() {
        return movableEntities;
    }
}
