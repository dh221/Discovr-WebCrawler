import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

/**
 * Adapter object for converting Event into JSON with proper DateTime format.
 */
public class EventAdapter implements JsonSerializer<Event> {


    @Override
    public JsonElement serialize(Event event, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", event.name);
        jsonObject.addProperty("host", event.host);
        jsonObject.addProperty("location", event.location);
        jsonObject.addProperty("description", event.description);

        // Format the date into the following pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        jsonObject.addProperty("start_time", event.start.format(formatter));
        jsonObject.addProperty("end_time", event.end.format(formatter));

        return jsonObject;
    }
}
