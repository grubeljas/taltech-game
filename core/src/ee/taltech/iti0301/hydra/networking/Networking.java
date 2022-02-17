package ee.taltech.iti0301.hydra.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Networking {
    public static final int MAIN_TCP_PORT = 8080;
    public static final int MAIN_UPD_PORT = 8081;
    public static final String SERVER_ADDRESS = "localhost";

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(RegisterName.class);
        kryo.register(RegistrationResponse.class);
        kryo.register(GameServerPorts.class);
        kryo.register(CurrentCoordinates.class);
        kryo.register(MovementChange.class);
    }

    public static class SomeRequest {
        public String text;
    }

    public static class RegisterName {
        public String name;
    }

    public static class RegistrationResponse {
        public String text;
    }

    public static class GameServerPorts {
        public int tcp;
        public int udp;
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

