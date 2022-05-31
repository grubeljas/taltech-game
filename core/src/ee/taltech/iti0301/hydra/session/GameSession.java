package ee.taltech.iti0301.hydra.session;

import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;
import java.util.ArrayList;
import java.util.List;

public class GameSession {
    
    private TankBody playerTank = null;
    private final List<TankBody> tanks = new ArrayList<>();
    private final List<Entity> entities = new ArrayList<>();
    private final List<MovableEntity> movableEntities = new ArrayList<>();

    public GameSession(Client client) {
    
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

    public void movePlayerTank(TankBody.Direction movementDirection, TankBody.Direction rotationDirection, Vector3 mouseLocation) {

            playerTank.setMovementDirection(movementDirection);
            playerTank.setRotationDirection(rotationDirection);
            playerTank.setTurretAngle(mouseLocation);
        
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
