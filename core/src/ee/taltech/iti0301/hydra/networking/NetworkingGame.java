package ee.taltech.iti0301.hydra.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class NetworkingGame {
    public static final int GAME_TCP_PORT = 8081;
    public static final int GAME_UDP_PORT = 8082;

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(CurrentCoordinates.class);
        kryo.register(MovementChange.class);
    }

    public static class CurrentCoordinates {
        public float x;
        public float y;
    }

    public static class MovementChange {
        public int x;
        public int y;
        public int speed;
    }
}
