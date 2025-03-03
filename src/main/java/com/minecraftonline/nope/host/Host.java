/*
 * MIT License
 *
 * Copyright (c) 2020 MinecraftOnline
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.minecraftonline.nope.host;

import com.google.gson.JsonElement;
import com.minecraftonline.nope.Nope;
import com.minecraftonline.nope.setting.Setting;
import com.minecraftonline.nope.setting.SettingKey;
import com.minecraftonline.nope.setting.SettingLibrary;
import com.minecraftonline.nope.setting.SettingMap;
import com.minecraftonline.nope.setting.SettingValue;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * A storage class for setting assignments.
 */
public abstract class Host {

  @Getter
  private final String name;
  private final SettingMap settings = new SettingMap();
  @Getter
  private final Context context;
  @Setter
  @Getter
  private Host parent;
  @Getter
  private int priority;

  /**
   * Default constructor.
   *
   * @param name     the name for this host; best practice is to have it
   *                 be unique
   * @param priority the priority level for this host for identifying
   *                 conflicting settings
   */
  public Host(String name, int priority) {
    this.name = name.toLowerCase();
    this.priority = priority;
    this.context = new Context(nameToContextKey(this.name), "true");
  }

  public Host(String name) {
    this(name, 0);
  }

  public static String nameToContextKey(String name) {
    return "nope.host." + name;
  }

  /**
   * Statically convert a {@link Context} key into the name of a host.
   *
   * @param key the context key
   * @return the name of the encoded host
   */
  public static Optional<String> contextKeyToName(String key) {
    if (!isContextKey(key)) {
      return Optional.empty();
    }
    return Optional.of(key.substring(10));
  }

  /**
   * Checks whether the given string is a context key encoding a host's name.
   *
   * @param key the string that may be a key
   * @return true if key
   */
  public static boolean isContextKey(String key) {
    if (key == null || key.length() < 11) {
      return false;
    }
    return key.startsWith("nope.host.");
  }

  /**
   * Assign a value to a setting for this Host.
   *
   * @param key   The setting
   * @param value The value to assign
   * @param <A>   The type of value this is
   * @return The value which was saved prior
   * @see SettingLibrary
   */
  @Nonnull
  @SuppressWarnings("unchecked")
  public <A> Optional<A> put(SettingKey<A> key, SettingValue<A> value) {
    return Optional.ofNullable((A) settings.put(Setting.of(key, value)));
  }

  /**
   * Assigns all the values in the map under the given settings.
   *
   * @param settings all settings
   * @see SettingLibrary
   */
  public void putAll(SettingMap settings) {
    this.settings.putAll(settings);
  }

  /**
   * Retrieves the value associated with a setting under this Host.
   *
   * @param key the setting which keys the value
   * @param <A> the type of value stored
   * @return the value
   * @see SettingLibrary
   */
  @Nonnull
  public <A> Optional<SettingValue<A>> get(SettingKey<A> key) {
    return Optional.ofNullable(this.settings.get(key));
  }

  /**
   * Retrieves all values associated with settings under this Host.
   * The types are hidden, so it is suggested you use {@link #get(SettingKey)}
   * wherever possible.
   *
   * @return a copy of all the setting assignments
   * @see SettingLibrary
   */
  @Nonnull
  public SettingMap getAll() {
    SettingMap settings = new SettingMap();
    settings.putAll(this.settings);
    return settings;
  }

  /**
   * Get the data associated on this host, regardless of the
   * {@link com.minecraftonline.nope.setting.SettingValue.Target}.
   *
   * @param key the key for which to search
   * @param <A> the type of data to retrieve
   * @return the data
   */
  @Nonnull
  public <A> A getData(SettingKey<A> key) {
    return this.get(key).map(SettingValue::getData).orElse(key.getDefaultData());
  }

  /**
   * Get the data associated on this host.
   *
   * @param key    the key for which to search
   * @param player the player to test for targeting
   * @param <A>    the type of data to retrieve
   * @return the data
   */
  public <A> A getData(SettingKey<A> key, Player player) {
    return this.get(key)
        .filter(value -> value.getTarget().test(key, player))
        .map(SettingValue::getData)
        .orElse(key.getDefaultData());
  }

  /**
   * Set the priority of this host.
   *
   * @param priority the new priority
   * @throws IllegalArgumentException if the value is too large
   */
  public void setPriority(int priority) throws IllegalArgumentException {
    if (priority > Nope.MAX_HOST_COUNT) {
      throw new IllegalArgumentException(String.format(
          "The priority set for host %s is too large!",
          getName()));
    }
    this.priority = priority;
  }

  /**
   * Check if a setting is assigned for this Host.
   *
   * @param setting the setting to check for existence
   * @return true if exists
   * @see SettingLibrary
   */
  boolean has(SettingKey<?> setting) {
    return this.settings.containsKey(setting);
  }

  /**
   * Removes any value associated with the given
   * {@link SettingKey} from this host.
   *
   * @param key Key to remove the mapping for.
   * @return The no longer associated {@link SettingValue}, or null, if nothing was removed.
   */
  @Nullable
  public <A> SettingValue<A> remove(SettingKey<A> key) {
    return settings.remove(key);
  }

  /**
   * Clears all the {@link Setting} assignments.
   */
  public void clear() {
    this.settings.clear();
  }

  /**
   * Check if a Sponge locatable exists within this host.
   * This is the same as calling this method with the
   * locatable location with {@link #encompasses(Location)}
   *
   * @param spongeLocatable the locatable
   * @return true if within the host
   */
  public final boolean encompasses(Locatable spongeLocatable) {
    return encompasses(spongeLocatable.getLocation());
  }

  /**
   * Check if a Sponge Location exists within this host.
   *
   * @param spongeLocation the location
   * @return true if within the host
   */
  public abstract boolean encompasses(Location<World> spongeLocation);

  /**
   * Get the UUID of the Sponge Minecraft
   * world to which this Host is associated.
   *
   * @return the world's UUID, or null if the host is not associated with a world
   */
  @Nullable
  public abstract UUID getWorldUuid();

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Host && ((Host) obj).getName().equals(this.getName());
  }

  /**
   * A functional container for serializing and deserializing a {@link Host}.
   *
   * @param <T> the type of host
   */
  public interface HostSerializer<T extends Host> {
    JsonElement serialize(T host);

    T deserialize(JsonElement json) throws IllegalArgumentException;
  }

}
