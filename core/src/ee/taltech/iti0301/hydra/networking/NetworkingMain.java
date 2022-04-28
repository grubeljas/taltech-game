package ee.taltech.iti0301.hydra.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class NetworkingMain {
    public static final int MAIN_TCP_PORT = 8080;
    public static final String SERVER_ADDRESS = "localhost";

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(RegisterName.class);
        kryo.register(Response.class);
        kryo.register(JoinGame.class);
        kryo.register(StartGame.class);
        kryo.register(ReadyStatus.class);
    }

    public static class SomeRequest {
        public String text;
    }

    public static class RegisterName {
        public String name;
    }

    public static class Response {
        public String text;
    }

    public static class JoinGame {
        public int gameId;
    }

    public static class StartGame {
        public int gameId;
    }

    public static class ReadyStatus {
        public boolean ready;
    }
}

