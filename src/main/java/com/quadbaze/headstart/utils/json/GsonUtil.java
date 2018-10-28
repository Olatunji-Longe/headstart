package com.quadbaze.headstart.utils.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Created by Olatunji O. Longe on 7/5/2017.
 */
public class GsonUtil {

	/**
	 * Parses a json String
	 * @param jsonString
	 * @return jsonElement
	 */
	public static JsonElement parse(String jsonString) {
		try {
			JsonElement jsonElement = new JsonParser().parse(jsonString);
			return jsonElement.isJsonNull() ? JsonNull.INSTANCE : jsonElement;
		} catch (JsonParseException ex) {
			return JsonNull.INSTANCE;
		}
	}

	public static boolean isJsonValid(String jsonString) {
		return parse(jsonString) != null;
	}

	public static Gson gson() {
		return new GsonBuilder().registerTypeAdapter(Map.class, new GsonDeserializer()).create();
	}

	public static Map<String, Object> toMap(Object object) {
		Type type = new TypeToken<LinkedHashMap<String, Object>>() {}.getType();
		return gson().fromJson(toJson(object), type);
	}

	public static Map<String, Object> toMap(JsonElement json) {
		Type type = new TypeToken<LinkedHashMap<String, Object>>() {}.getType();
		return gson().fromJson(json, type);
	}

	public static JsonElement toJson(Object object) {
		return object.getClass().isAssignableFrom(String.class) ? parse(String.valueOf(object)) : gson().toJsonTree(object); //parse(gson().xmlToJson(object, object.getClass()));
	}

	public static String toString(Object object){
		return gson().toJson(object);
	}

    public static JsonElement emptyJson(){
        return gson().toJsonTree(new LinkedHashMap<String, Object>());
    }

    public static String emptyJsonString(){
        return toString(new LinkedHashMap<String, Object>());
    }

}