package dev.manyroads.weather.event;

public class Event <K, T> {

    public enum Type{
        GET
    }
    private Type type;
    private K key;
    private T data;

    public Event() {
        this.type = null;
        this.key = null;
        this.data = null;
    }

    public Event(Type type, K key, T data) {
        this.type = type;
        this.key = key;
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "key=" + key +
                ", data=" + data +
                '}';
    }
}
