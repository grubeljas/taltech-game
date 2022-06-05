package ee.taltech.iti0301.hydra.networking;

import com.google.gson.Gson;

public class Serializer<T> {
    
    private final Class<T> typeParameterClass;
    private final Gson gson = new Gson();
    
    public Serializer(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }
    
    public String encode(T object) {
        return gson.toJson(object, object.getClass());
    }
    
    public T decode(String string) {
        return gson.fromJson(string, typeParameterClass);
    }
    
}
