package ee.taltech.iti0301.hydra.entity;

public class FakeEntity {
    
    protected int id;
    protected float x;
    protected float y;
    protected float angle;
    
    public FakeEntity(int id, float x, float y, float angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    
    public FakeEntity(Entity entity) {
        this(entity.id, entity.x, entity.y, entity.angle);
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getRotation() {
        return angle;
    }
    
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "FakeEntity{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", angle=" + angle +
                '}';
    }
}
