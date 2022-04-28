package ee.taltech.iti0301.hydra.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;

public class NetworkingGame {

    public static final String GAME_SERVER_ADDRESS = "localhost";
    public static final int GAME_TCP_PORT = 8081;
    public static final int GAME_UDP_PORT = 8082;

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(ConnectionRequest.class);
        kryo.register(UpdateEntity.class);
    }

    public static class ConnectionRequest {
        int playerId;
        int sessionId;
    }

    public static class UpdateEntity {
        int id;
        float x;
        float y;
        float angle;
        String movementDirection;
        String rotationDirection;
        boolean blockedHorizontal;
        boolean blockedVertical;
    }

    public static UpdateEntity createUpdateEntityPacket(TankBody tank) {
        UpdateEntity packet = new NetworkingGame.UpdateEntity();
        packet.id = tank.getId();
        packet.x = tank.getX();
        packet.y = tank.getY();
        packet.angle = tank.getAngle();
        packet.movementDirection = tank.getMovementDirection().name();
        packet.rotationDirection = tank.getRotationDirection().name();
        return packet;
    }
}
