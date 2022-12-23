package org.abos.fabricmc.magic.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractConfig implements Iterable<ConfigProperty<?,? extends GameRules.Rule<?>>> {

    public final static Logger LOGGER = LoggerFactory.getLogger("AbosConfig");

    public void saveTo(Path file, World world) throws IllegalStateException {
        if (Files.isDirectory(file)) {
            LOGGER.warn("Please don't offer a directory as a config file!");
            return;
        }
        JsonObject config = new JsonObject();
        for (ConfigProperty<?,?> property : this) {
            if (property instanceof IntConfigProperty intProperty) {
                config.addProperty(intProperty.getName(), intProperty.getValue(world));
            }
            else if (property instanceof BooleanConfigProperty booleanProperty) {
                config.addProperty(booleanProperty.getName(), booleanProperty.getValue());
            }
            else {
                throw new IllegalStateException("Attempted to save unknown config property type "+property.getClass().getName()+"!");
            }
        }
        try {
            Files.writeString(file, new GsonBuilder().setPrettyPrinting().create().toJson(config));
        } catch (IOException e) {
            LOGGER.warn("Config file couldn't be written!");
        }
    }

    public void loadFrom(Path file, MinecraftServer server) throws IllegalStateException {
        if (!Files.isReadable(file)) {
            LOGGER.warn("No readable config file found!");
            return;
        }
        JsonObject config;
        try {
            config = new Gson().fromJson(Files.readString(file), JsonObject.class);
        } catch (IOException e) {
            LOGGER.warn("Config file couldn't be read!");
            return;
        }
        for (ConfigProperty<?,?> property : this) {
            JsonElement value;
            if (property instanceof IntConfigProperty intProperty) {
                value = config.get(intProperty.getName());
                if (value == null) {
                    LOGGER.warn("Missing config value for "+intProperty.getName()+", default will be used!");
                    continue;
                }
                intProperty.setValue(value.getAsInt(), server);
            }
            else if (property instanceof BooleanConfigProperty booleanProperty) {
                value = config.get(booleanProperty.getName());
                if (value == null) {
                    LOGGER.warn("Missing config value for "+booleanProperty.getName()+", default will be used!");
                    continue;
                }
                booleanProperty.setValue(value.getAsBoolean(), server);
            }
            else {
                throw new IllegalStateException("Attempted to load unknown config property type "+property.getClass().getName()+"!");
            }
        }
    }

}
