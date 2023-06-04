package org.dimdev.riftloader;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModInfo {
    public static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Listener.class, (JsonSerializer<Listener>) (listener, type, context) -> {
                if (listener.priority == 0 && listener.side == Side.BOTH) {
                    return new JsonPrimitive(listener.className);
                }

                return new Gson().toJsonTree(listener);
            })
            .registerTypeAdapter(Listener.class, (JsonDeserializer<Listener>) (json, type, context) -> {
                if (json.isJsonPrimitive() && ((JsonPrimitive) json).isString()) {
                    return new Listener(json.getAsString());
                }

                Listener listener = new Gson().fromJson(json, Listener.class);
                if (listener.className == null) throw new JsonSyntaxException("Listener with no class!");
                //If the side isn't provided Gson helpfully returns null
                if (listener.side == null) listener.side = Side.BOTH;
                return listener;
            })
            .registerTypeAdapter(ModInfo.class, (JsonDeserializer<ModInfo>) (json, type, context) -> {
                ModInfo modInfo = new ModInfo();

                if (!json.isJsonObject()) {
                    throw new JsonSyntaxException("ModInfo is not an Object!");
                }

                JsonObject object = json.getAsJsonObject();

                if (object.has("id")) {
                    modInfo.id = object.get("id").getAsString();
                } else {
                    throw new JsonSyntaxException("ModInfo with no id!");
                }

                if (object.has("name")) {
                    modInfo.name = object.get("name").getAsString();
                } else {
                    modInfo.name = modInfo.id;
                }

                if (object.has("authors")) {
                    JsonElement authors = object.get("authors");
                    if (authors.isJsonArray()) {
                        JsonArray array = authors.getAsJsonArray();
                        for (JsonElement author : array) {
                            if (author.isJsonPrimitive()) {
                                modInfo.authors.add(author.getAsString());
                            }
                        }
                    } else if (authors.isJsonPrimitive()) {
                        modInfo.authors.add(authors.getAsString());
                    }
                }

                if (object.has("listeners")) {
                    JsonElement listeners = object.get("listeners");
                    if (listeners.isJsonArray()) {
                        JsonArray array = listeners.getAsJsonArray();

                        for (JsonElement listenerElement : array) {
                            modInfo.listeners.add(ModInfo.GSON.fromJson(listenerElement, Listener.class));
                        }
                    }
                }

                return modInfo;
            })
            .create();

    public static class Listener {
        @SerializedName("class") public String className;
        public int priority = 0;
        public Side side = Side.BOTH;

        public Listener(String className) {
            this.className = className;
        }
    }

    public File source;

    public String id;
    public String name;
    public List<String> authors = new ArrayList<>();
    public List<Listener> listeners = new ArrayList<>();
}
