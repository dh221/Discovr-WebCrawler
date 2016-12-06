import java.time.LocalDateTime;

/**
 * Simple model object for an event.
 */
public class Event {
    public final String name;
    public final String host;
    public final String location;
    public final String description;
    public final LocalDateTime start;
    public final LocalDateTime end;

    public Event(String name, String host, String location, String description, LocalDateTime start, LocalDateTime end) {
        this.name = name;
        this.host = host;
        this.location = location;
        this.description = description;
        this.start = start;
        this.end = end;
    }

}
