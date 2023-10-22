package dev.manyroads.weather.model;

public class Event <K, V> {

    enum Type{
        Save,
        Delete
    }
    Type type;
    K eventID;
    V data;

    public Event() {
        this.type = null;
        this.eventID = null;
        this.data = null;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public K getEventID() {
        return eventID;
    }

    public void setEventID(K eventID) {
        this.eventID = eventID;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", eventID=" + eventID +
                ", data=" + data +
                '}';
    }
}
