package dev.manyroads.weather.shared.event;

public class Event <K, V> {

    public enum Type{
        Save,
        Delete
    }
    Type eventType;
    K eventID;
    V data;

    public Event() {
        this.eventType = null;
        this.eventID = null;
        this.data = null;
    }

    public Event(Type type, K eventID, V data) {
        this.eventType = type;
        this.eventID = eventID;
        this.data = data;
    }

    public Type getEventType() {
        return eventType;
    }

    public void setEventType(Type eventType) {
        this.eventType = eventType;
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
                "type=" + eventType +
                ", eventID=" + eventID +
                ", data=" + data +
                '}';
    }
}
