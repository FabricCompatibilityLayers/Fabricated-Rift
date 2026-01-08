package io.github.fabriccompatibilitylayers.fabricatedrift.mixin.rei;

import com.google.gson.*;
import me.shedaniel.rei.client.ConfigManagerImpl;
import me.shedaniel.rei.gui.config.RecipeScreenType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ConfigManagerImpl.class)
public class ConfigManagerImplMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/google/gson/GsonBuilder;create()Lcom/google/gson/Gson;"))
    private static Gson registerGsonAdapters(GsonBuilder instance) {
        return instance.registerTypeAdapter(RecipeScreenType.class, (JsonDeserializer<RecipeScreenType>) (jsonElement, type, jsonDeserializationContext) ->
                        jsonElement.isJsonPrimitive() && ((JsonPrimitive) jsonElement).isString() ? RecipeScreenType.valueOf(jsonElement.getAsString()) : null)
                .registerTypeAdapter(RecipeScreenType.class, (JsonSerializer<RecipeScreenType>) (recipeScreenType, type, jsonSerializationContext) ->
                        new JsonPrimitive(recipeScreenType.name()))
                .create();
    }
}
