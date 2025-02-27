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

package com.minecraftonline.nope.setting;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.minecraftonline.nope.Nope;
import com.minecraftonline.nope.update.SettingUpdates;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import org.spongepowered.api.entity.EnderCrystal;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.monster.Wither;
import org.spongepowered.api.entity.projectile.Firework;
import org.spongepowered.api.entity.projectile.explosive.WitherSkull;
import org.spongepowered.api.entity.projectile.explosive.fireball.LargeFireball;
import org.spongepowered.api.entity.vehicle.minecart.TNTMinecart;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * A utility class to store all {@link SettingKey}s in Nope.
 */
@SuppressWarnings("checkstyle:LineLength")
public final class SettingLibrary {

  @Blurb("Armor stand destruction restriction")
  @Description("When disabled, armor stands may not be broken by players.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> ARMOR_STAND_DESTROY = new StateSettingKey(
      "armor-stand-destroy",
      true
  );
  @Blurb("Armor stand interact restriction")
  @Description("When disabled, armor stands may not be interacted with by a player.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> ARMOR_STAND_INTERACT = new StateSettingKey(
      "armor-stand-interact",
      true
  );
  @Blurb("Armor stand placement restriction")
  @Description("When disabled, armor stands may not be placed.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> ARMOR_STAND_PLACE = new StateSettingKey(
      "armor-stand-place",
      true
  );
  @Blurb("Block break restriction")
  @Description("When disabled, blocks may not be broken by players.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> BLOCK_BREAK = new StateSettingKey(
      "block-break",
      true
  );
  @Blurb("Block place restriction")
  @Description("When disabled, blocks may not be placed by players.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> BLOCK_PLACE = new StateSettingKey(
      "block-place",
      true
  );
  @Blurb("Inside to outside block updates")
  @Description("When disabled, block updates will not affect others across the zone boundary.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> BLOCK_PROPAGATE_ACROSS = new BooleanSettingKey(
      "block-propagate-across",
      true
  );
  @Blurb("Inside to inside block updates")
  @Description("When disabled, block updates will not affect others within the zone.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> BLOCK_PROPAGATE_WITHIN = new BooleanSettingKey(
      "block-propagate-within",
      true
  );
  @Blurb("Trample restriction")
  @Description("When disabled, blocks like farmland may not be trampled.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> BLOCK_TRAMPLE = new StateSettingKey(
      "block-trample",
      true
  );
  @Blurb("Size of world block caches")
  @Description("This is the quantity of block locations to cache for each world. "
      + "Total memory is roughly this multiplied by 56 bytes, "
      + "multiplied by the number of worlds. Set 0 to disable caching.")
  @Global
  public static final SettingKey<Integer> CACHE_SIZE = new PositiveIntegerSettingKey(
      "cache-size",
      75000
  );
  @Blurb("Chest access restriction")
  @Description("When disabled, players may not open chests.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> CHEST_ACCESS = new StateSettingKey(
      "chest-access",
      true
  );
  @Blurb("Chorus fruit teleport restriction")
  @Description("When disabled, players may not teleport by eating a chorus fruit.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  @PlayerRestrictive
  public static final SettingKey<Boolean> CHORUS_FRUIT_TELEPORT = new StateSettingKey(
      "chorus-fruit-teleport",
      true
  );
  @Blurb("Concrete powder solidification")
  @Description("When disabled, concrete powder does not solidify into concrete.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> CONCRETE_SOLIDIFICATION = new BooleanSettingKey(
      "concrete-solidification",
      true
  );
  @Blurb("Crop growth")
  @Description("When disabled, crops do not grow.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> CROP_GROWTH = new BooleanSettingKey(
      "crop-growth",
      true
  );
  @Blurb("Entity experience drop")
  @Description("When disabled, experience points are never dropped.")
  @PlayerRestrictive
  public static final SettingKey<Boolean> DROP_EXP = new BooleanSettingKey(
      "drop-exp",
      false
  );
  @Blurb("Grief caused by the enderdragon")
  @Description("Enables grief caused by the enderdragon.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> ENDERDRAGON_GRIEF = new BooleanSettingKey(
      "enderdragon-grief",
      true
  );
  @Blurb("Grief caused by endermen")
  @Description("When disabled, endermen do not grief blocks by picking them up.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> ENDERMAN_GRIEF = new BooleanSettingKey(
      "enderman-grief",
      true
  );
  @Blurb("Enderpearl teleport restriction")
  @Description("When disabled, enderpearls may not be used for teleportation.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  @PlayerRestrictive
  public static final SettingKey<Boolean> ENDERPEARL_TELEPORT = new StateSettingKey(
      "enderpearl-teleport",
      true
  );
  @Blurb("Host entrance restriction")
  @Description("Specify which type of movement is allowed by players to enter.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  @PlayerRestrictive
  public static final SettingKey<Movement> ENTRY = new EnumSettingKey<>(
      "entry",
      Movement.ALL,
      Movement.class
  );
  @Blurb("Message when entry is denied")
  @Description("The message that is sent to a player if they are barred from entry.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> ENTRY_DENY_MESSAGE = new TextSettingKey(
      "entry-deny-message",
      Text.of(TextColors.RED, "You are not allowed to go there")
  );
  @Blurb("Subtitle when entry is denied")
  @Description("The subtitle that is sent to a player if they are barred from entry.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> ENTRY_DENY_SUBTITLE = new TextSettingKey(
      "entry-deny-subtitle",
      Text.EMPTY
  );
  @Blurb("Title when entry is denied")
  @Description("The title that is sent to a player if they are barred from entry.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> ENTRY_DENY_TITLE = new TextSettingKey(
      "entry-deny-title",
      Text.EMPTY
  );
  @Blurb("Environment-to-player damage")
  @Description("When disabled, the environment cannot inflict damage on players.")
  @Category(SettingKey.CategoryType.DAMAGE)
  @PlayerRestrictive
  public static final SettingKey<Boolean> EVP = new StateSettingKey(
      "evp",
      true
  );
  @Blurb("Host exit restriction")
  @Description("Specify which type of movement is allowed by players to exit.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  @PlayerRestrictive
  public static final SettingKey<Movement> EXIT = new EnumSettingKey<>(
      "exit",
      Movement.ALL,
      Movement.class
  );
  @Blurb("Message when exit is denied")
  @Description("The message that is sent to the player if they are barred from exiting.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> EXIT_DENY_MESSAGE = new TextSettingKey(
      "exit-deny-message",
      Text.of(TextColors.RED, "You are not allowed to leave here")
  );
  @Blurb("Subtitle when exit is denied")
  @Description("The subtitle that is sent to a player if they are barred from exiting.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> EXIT_DENY_SUBTITLE = new TextSettingKey(
      "exit-deny-subtitle",
      Text.EMPTY
  );
  @Blurb("Title when exit is denied")
  @Description("The title that is sent to a player if they are barred from exiting.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> EXIT_DENY_TITLE = new TextSettingKey(
      "exit-deny-title",
      Text.EMPTY
  );
  @Blurb("Harmless explosions")
  @Description("A list of explosives whose explosions do not cause damage.")
  @Category(SettingKey.CategoryType.DAMAGE)
  public static final SettingKey<Set<Explosive>> EXPLOSION_DAMAGE_BLACKLIST = new EnumSetSettingKey<>(
      "explosion-damage-blacklist",
      new HashSet<>(),
      Explosive.class
  );
  @Blurb("Nondestructive explosions")
  @Description("A list of explosives whose explosions do not grief.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Set<Explosive>> EXPLOSION_GRIEF_BLACKLIST = new EnumSetSettingKey<>(
      "explosion-block-grief-blacklist",
      new HashSet<>(),
      Explosive.class
  );
  @Blurb("Fall damage")
  @Description("When disabled, players do not experience fall damage.")
  @Category(SettingKey.CategoryType.DAMAGE)
  public static final SettingKey<Boolean> FALL_DAMAGE = new BooleanSettingKey(
      "fall-damage",
      false
  );
  @Blurb("Message upon exit")
  @Description("The message to a player when they leave the host.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> FAREWELL = new TextSettingKey(
      "farewell",
      Text.EMPTY
  );
  @Blurb("Subtitle upon exit")
  @Description("The subtitle that appears to a player when they leave.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> FAREWELL_SUBTITLE = new TextSettingKey(
      "farewell-subtitle",
      Text.EMPTY
  );
  @Blurb("Title upon exit")
  @Description("The title that appears to a player when they leave.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> FAREWELL_TITLE = new TextSettingKey(
      "farewell-title",
      Text.EMPTY
  );
  @Blurb("Fire spread/damage")
  @Description("When disabled, fire does not spread or cause block damage.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> FIRE_EFFECT = new StateSettingKey(
      "fire-effect",
      true
  );
  @Blurb("Fire ignition restriction")
  @Description("When disabled, players cannot light fire")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> FIRE_IGNITION = new StateSettingKey(
      "fire-ignition",
      true
  );
  @Blurb("Natural fire ignition")
  @Description("When disabled, fire is not started naturally.")
  public static final SettingKey<Boolean> FIRE_NATURAL_IGNITION = new StateSettingKey(
      "fire-natural-ignition",
      true
  );
  @Blurb("Flower pot interaction restriction")
  @Description("When disabled, players cannot interact with flower pots.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> FLOWER_POT_INTERACT = new StateSettingKey(
      "flower-pot-interact",
      true
  );
  @Blurb("Frosted ice formation")
  @Description("When disabled, frosted ice does not form.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> FROSTED_ICE_FORM = new StateSettingKey(
      "frosted-ice-form",
      true
  );
  @Blurb("Frosted ice melt")
  @Description("When disabled, frosted ice does not melt.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> FROSTED_ICE_MELT = new StateSettingKey(
      "frosted-ice-melt",
      true
  );
  @Blurb("Ghast production of fireballs")
  @Description("When disabled, ghasts do not shoot fireballs.")
  @Category(SettingKey.CategoryType.ENTITIES)
  public static final SettingKey<Boolean> GHAST_FIREBALL = new StateSettingKey(
      "ghast-fireball",
      true
  );
  @Blurb("Grass growth")
  @Description("When disabled, grass does not grow naturally.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> GRASS_GROWTH = new StateSettingKey(
      "grass-growth",
      true
  );
  @Blurb("Message upon entry")
  @Description("The message to a player when they enter")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> GREETING = new TextSettingKey(
      "greeting",
      Text.EMPTY
  );
  @Blurb("Subtitle upon entry")
  @Description("The subtitle that appears to a player when they enter.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> GREETING_SUBTITLE = new TextSettingKey(
      "greeting-subtitle",
      Text.EMPTY
  );
  @Blurb("Title upon entry")
  @Description("The title that appears to a player when they enter.")
  @Category(SettingKey.CategoryType.MOVEMENT)
  public static final SettingKey<Text> GREETING_TITLE = new TextSettingKey(
      "greeting-title",
      Text.EMPTY
  );
  @Blurb("Fishing hook to entity attachment")
  @Description("When disabled, entities cannot be hooked with a fishing hook.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> HOOK_ENTITY = new StateSettingKey(
      "hook-entity",
      true
  );
  @Blurb("Hostile to player damage")
  @Description("When disabled, hostile creatures cannot inflict damage on players.")
  @Category(SettingKey.CategoryType.DAMAGE)
  public static final SettingKey<Boolean> HVP = new StateSettingKey(
      "hvp",
      true
  );
  @Blurb("Ice formation")
  @Description("When disabled, ice does not form.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> ICE_FORM = new BooleanSettingKey(
      "ice-form",
      true
  );
  @Blurb("Ice melt")
  @Description("When disabled, ice does not melt.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> ICE_MELT = new BooleanSettingKey(
      "ice-melt",
      true
  );
  @Blurb("General interaction restriction")
  @Description("When disabled, players may not interact with any blocks.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> INTERACT = new StateSettingKey(
      "interact",
      true
  );
  @Blurb("Animal invincibility")
  @Description("When disabled, animals are invincible.")
  @Category(SettingKey.CategoryType.DAMAGE)
  public static final SettingKey<Boolean> INVINCIBLE_ANIMALS = new BooleanSettingKey(
      "invincible-animals",
      false
  );
  @Blurb("All mob invincibility")
  @Description("When disabled, mobs cannot take damage.")
  @Category(SettingKey.CategoryType.DAMAGE)
  public static final SettingKey<Boolean> INVINCIBLE_MOBS = new BooleanSettingKey(
      "invincible-mobs",
      false
  );
  @Blurb("Player invincibility")
  @Description("When enabled, players cannot take damage.")
  @Category(SettingKey.CategoryType.DAMAGE)
  public static final SettingKey<Boolean> INVINCIBLE_PLAYERS = new BooleanSettingKey(
      "invincible-players",
      false
  );
  @Blurb("Item drop restriction")
  @Description("When disabled, players cannot drop items.")
  @PlayerRestrictive
  public static final SettingKey<Boolean> ITEM_DROP = new BooleanSettingKey(
      "item-drop",
      true
  );
  @Blurb("Item frame destruction restriction")
  @Description("When disabled, item frames may not be attacked by players.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> ITEM_FRAME_DESTROY = new StateSettingKey(
      "item-frame-destroy",
      true
  );
  @Blurb("Item frame interaction restriction")
  @Description("When disabled, item frames may not be interacted with by a player.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> ITEM_FRAME_INTERACT = new StateSettingKey(
      "item-frame-interact",
      true
  );
  @Blurb("Item frame placement restriction")
  @Description("When disabled, item frames may not be placed.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> ITEM_FRAME_PLACE = new StateSettingKey(
      "item-frame-place",
      true
  );
  @Blurb("Item pickup restriction")
  @Description("When disabled, players cannot pick up items.")
  @PlayerRestrictive
  public static final SettingKey<Boolean> ITEM_PICKUP = new StateSettingKey(
      "item-pickup",
      true
  );
  @Blurb("Lava flow")
  @Description("When disabled, lava does not spread.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> LAVA_FLOW = new BooleanSettingKey(
      "lava-flow",
      true
  );
  @Blurb("Grief caused by lava")
  @Description("When disabled, lava does not break blocks.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> LAVA_GRIEF = new BooleanSettingKey(
      "lava-grief",
      true
  );
  @Blurb("Leaf decay")
  @Description("When disabled, leaf will not decay naturally.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> LEAF_DECAY = new StateSettingKey(
      "leaf-decay",
      true
  );
  @Blurb("Putting leads on entities restriction")
  @Description("When disabled, players cannot put leads on entities.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> LEASH = new StateSettingKey(
      "leash",
      true
  );
  @Blurb("Lightning strikes")
  @Description("When disabled, lightning cannot strike.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> LIGHTNING = new BooleanSettingKey(
      "lightning",
      true
  );
  @Blurb("Mushroom growth")
  @Description("When disabled, mushrooms do not grow.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> MUSHROOM_GROWTH = new BooleanSettingKey(
      "mushroom-growth",
      true
  );
  @Blurb("Mycelium spread")
  @Description("When disabled, mycelium does not spread.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> MYCELIUM_SPREAD = new BooleanSettingKey(
      "mycelium-spread",
      true
  );
  @Blurb("Player health regeneration")
  @Description("When disabled, player health does not regenerate naturally.")
  @PlayerRestrictive
  public static final SettingKey<Boolean> NATURAL_HEALTH_REGEN = new BooleanSettingKey(
      "natural-health-regen",
      true
  );
  @Blurb("Player hunger drain")
  @Description("When disabled, player hunger does not drain naturally.")
  @NotImplemented
  public static final SettingKey<Boolean> NATURAL_HUNGER_DRAIN = new BooleanSettingKey(
      "natural-hunger-drain",
      true
  );
  @Blurb("Painting destruction restriction")
  @Description("When disabled, paintings may not be broken by players.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> PAINTING_DESTROY = new StateSettingKey(
      "painting-destroy",
      true
  );
  @Blurb("Painting placement restriction")
  @Description("When disabled, paintings may not be placed.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> PAINTING_PLACE = new StateSettingKey(
      "painting-place",
      true
  );
  @Blurb("Player collision")
  @Description("When disabled, all interactions caused by player collision are cancelled.")
  @Category(SettingKey.CategoryType.ENTITIES)
  public static final SettingKey<Boolean> PLAYER_COLLISION = new StateSettingKey(
      "player-collision",
      true
  );
  @Blurb("Player to animal damage")
  @Description("When disabled, players cannot inflict damage on animals.")
  @Category(SettingKey.CategoryType.DAMAGE)
  @PlayerRestrictive
  public static final SettingKey<Boolean> PVA = new StateSettingKey(
      "pva",
      true
  );
  @Blurb("Player to hostile mob damage")
  @Description("When disabled, players cannot inflict damage on hostile creatures.")
  @Category(SettingKey.CategoryType.DAMAGE)
  @PlayerRestrictive
  public static final SettingKey<Boolean> PVH = new StateSettingKey(
      "pvh",
      true
  );
  @Blurb("Player to player damage")
  @Description("When disabled, players cannot inflict damage on other players.")
  @Category(SettingKey.CategoryType.DAMAGE)
  @PlayerRestrictive
  public static final SettingKey<Boolean> PVP = new StateSettingKey(
      "pvp",
      true
  );
  @Blurb("Plugins which are considered restricted")
  @Description("The plugins that cannot break restrictive setting rules.")
  @NotImplemented
  public static final SettingKey<Set<String>> RESTRICTED_PLUGINS = new StringSetSettingKey(
      "restricted-plugins",
      new HashSet<>()
  );
  @Blurb("Players' ability to ride entities")
  @Description("When disabled, players cannot ride other entities.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> RIDE = new StateSettingKey(
      "ride",
      true
  );
  public static final String SET_SPLIT_REGEX = "(?<![ ,])(( )+|( *, *))(?![ ,])";
  @Blurb("Players' ability to sleep")
  @Description("When disabled, players cannot sleep.")
  @PlayerRestrictive
  public static final SettingKey<Boolean> SLEEP = new StateSettingKey(
      "sleep",
      true
  );
  @Blurb("Snowman snow trail creation")
  @Description("When disabled, snowmen do not make snow trails.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> SNOWMAN_TRAILS = new StateSettingKey(
      "snowman-trails",
      true
  );
  @Blurb("Snowfall snow placement")
  @Description("When disabled, snow does not accumulate naturally.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> SNOW_ACCUMULATION = new StateSettingKey(
      "snow-accumulation",
      true
  );
  @Blurb("Snow melt")
  @Description("When disabled, snow does not melt.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> SNOW_MELT = new StateSettingKey(
      "snow-melt",
      true
  );
  @Blurb("Animal spawning")
  @Description("When disabled, animals cannot spawn.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> SPAWN_ANIMAL = new StateSettingKey(
      "spawn-animal",
      true
  );
  @Blurb("Hostile mob spawning")
  @Description("When disabled, hostile mobs cannot spawn.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> SPAWN_HOSTILE = new StateSettingKey(
      "spawn-hostile",
      true
  );
  @Blurb("All mob spawning")
  @Description("When disabled, no mobs can spawn.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> SPAWN_MOB = new StateSettingKey(
      "spawn-mob",
      true
  );
  @Blurb("SQL data source name")
  @Description("The Data Source name of the SQL database to be used if SQL is the storage type.")
  @NotImplemented
  @Global
  public static final SettingKey<String> SQL_DSN = new StringSettingKey(
      "sql-dsn",
      "jdbc:mysql://localhost/nope"
  );
  @Blurb("SQL password")
  @Description("The password for the SQL database to be used if SQL is the storage type.")
  @NotImplemented
  @Global
  public static final SettingKey<String> SQL_PASSWORD = new StringSettingKey(
      "sql-password",
      "nope"
  );
  @Blurb("SQL table prefix")
  @Description("The table prefix to be placed before SQL tables if SQL is the storage type.")
  @NotImplemented
  @Global
  public static final SettingKey<String> SQL_TABLE_PREFIX = new StringSettingKey(
      "sql-table-prefix",
      "nope"
  );
  @Blurb("SQL username")
  @Description("The username for the SQL database to be used if SQL is the storage type.")
  @NotImplemented
  @Global
  public static final SettingKey<String> SQL_USERNAME = new StringSettingKey(
      "sql-username",
      "nope"
  );
  @Blurb("Storage type")
  @Description("The type of storage to persist Nope server state.")
  @NotImplemented
  @Global
  public static final SettingKey<Storage> STORAGE_TYPE = new EnumSettingKey<>(
      "storage-type",
      Storage.HOCON,
      Storage.class
  );
  @Blurb("Location at which to teleport")
  @Description("The designated point of access to the zone via teleport.")
  public static final SettingKey<Vector3d> TELEPORT_LOCATION = new Vector3dSetting(
      "teleport-location",
      Vector3d.ZERO
  );
  @Blurb("TNT ignition restriction")
  @Description("When disabled, tnt may not be activated.")
  @PlayerRestrictive
  public static final SettingKey<Boolean> TNT_IGNITION = new StateSettingKey(
      "tnt-ignition",
      true
  );
  @Blurb("TNT placement restriction")
  @Description("When disabled, tnt may not be placed.")
  @Category(SettingKey.CategoryType.BLOCKS)
  @PlayerRestrictive
  public static final SettingKey<Boolean> TNT_PLACEMENT = new StateSettingKey(
      "tnt-placement",
      true
  );
  @Blurb("Commands which directly cause movement")
  @Description("These commands will be considered unnatural methods of teleportation.")
  @PlayerRestrictive
  @Global
  public static final SettingKey<Set<String>> MOVEMENT_COMMANDS = new StringSetSettingKey(
      "movement-commands",
      Sets.newHashSet()
  );
  @Blurb("Mobs which are unspawnable")
  @Description("These entity types will not be allowed to spawn.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Set<EntityType>> UNSPAWNABLE_MOBS = new EntityTypeSetSettingKey(
      "unspawnable-mobs",
      Sets.newHashSet()
  );
  @Blurb("Name tag use")
  @Description("When disabled, players may not use a name tag to rename an entity")
  @PlayerRestrictive
  public static final SettingKey<Boolean> USE_NAME_TAG = new StateSettingKey(
      "use-name-tag",
      true
  );
  @Blurb("Vehicle destruction restriction")
  @Description("When disabled, players may not break vehicles.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> VEHICLE_DESTROY = new StateSettingKey(
      "vehicle-destroy",
      true
  );
  @Blurb("Vehicle placement restriction")
  @Description("When disabled, players may not place vehicles.")
  @Category(SettingKey.CategoryType.ENTITIES)
  @PlayerRestrictive
  public static final SettingKey<Boolean> VEHICLE_PLACE = new StateSettingKey(
      "vehicle-place",
      true
  );
  @Blurb("Vine growth")
  @Description("When disabled, vines do not grow naturally.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> VINE_GROWTH = new StateSettingKey(
      "vine-growth",
      true
  );
  @Blurb("Item type used as the Nope Wand")
  @Description("The type of item to be used as the Nope Wand.")
  @Global
  public static final SettingKey<String> WAND_ITEM = new CatalogTypeSettingKey<>(
      "wand-item",
      ItemTypes.STICK,
      ItemType.class
  );
  @Blurb("Water flow")
  @Description("When disabled, water cannot flow")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> WATER_FLOW = new BooleanSettingKey(
      "water-flow",
      true
  );
  @Blurb("Grief caused by water")
  @Description("When disabled, water cannot break blocks")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> WATER_GRIEF = new BooleanSettingKey(
      "water-grief",
      true
  );
  @Blurb("Grief caused by zombies")
  @Description("When disabled, zombies cannot break blocks.")
  @Category(SettingKey.CategoryType.BLOCKS)
  public static final SettingKey<Boolean> ZOMBIE_GRIEF = new BooleanSettingKey(
      "zombie-grief",
      true
  );
  private static final HashMap<String, SettingKey<?>> settingMap = Maps.newHashMap();

  /**
   * Private constructor because its not supposed to be instantiated.
   */
  private SettingLibrary() {
  }

  private static void ensureInitialized() {
    if (settingMap.isEmpty()) {
      throw new RuntimeException("The SettingLibrary must be initialized");
    }
  }

  /**
   * Get a SettingKey based on its id.
   *
   * @param id the id of a SettingKey
   * @return the SettingKey keyed with that id
   * @throws NoSuchElementException if there is no SettingKey with that id
   */
  public static SettingKey<?> lookup(@Nonnull String id) throws NoSuchElementException {
    ensureInitialized();
    SettingKey<?> output = settingMap.get(id);
    if (output == null) {
      throw new NoSuchElementException(String.format(
          "There is no setting with id '%s'",
          id));
    }
    return output;
  }

  public static Collection<SettingKey<?>> getAll() {
    ensureInitialized();
    return settingMap.values();
  }

  /**
   * Prepare the SettingLibrary for easy accessing of SettingKeys.
   *
   * @throws IllegalStateException if there are multiple SettingKeys with the same ID.
   */
  public static void initialize() throws IllegalStateException {
    Arrays.stream(SettingLibrary.class.getDeclaredFields())
        .filter(field -> Modifier.isStatic(field.getModifiers()))
        .filter(field -> SettingKey.class.isAssignableFrom(field.getType()))
        .forEach(field -> {
          try {
            SettingKey<?> key = (SettingKey<?>) field.get(null);
            if (settingMap.put(key.getId(), key) != null) {
              throw new IllegalStateException("SettingKeys may not have the same id: " + key.getId());
            }
            for (Annotation annotation : field.getAnnotations()) {
              if (annotation instanceof Description) {
                key.setDescription(((Description) annotation).value());
              } else if (annotation instanceof Blurb) {
                key.setBlurb(((Blurb) annotation).value());
              } else if (annotation instanceof Category) {
                key.setCategory(((Category) annotation).value());
              } else if (annotation instanceof Global) {
                key.setCategory(SettingKey.CategoryType.GLOBAL);
                key.setGlobal(true);
              } else if (annotation instanceof NotImplemented) {
                key.setImplemented(false);
              } else if (annotation instanceof UnnaturalDefault) {
                key.setUnnaturalDefault(true);
              } else if (annotation instanceof PlayerRestrictive) {
                key.setPlayerRestrictive(true);
              }
            }
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        });
    if (settingMap.isEmpty()) {
      throw new RuntimeException("Tried to initialize SettingLibrary, "
          + "but it did not appear to work");
    }
  }

  /**
   * Send a SettingMap to a JsonElement to be saved elsewhere
   * so that it can be restored later using
   * {@link #deserializeSettingAssignments(JsonElement, String)} )}.
   *
   * @param map the map of settings
   * @return the finished json element
   */
  public static JsonElement serializeSettingAssignments(SettingMap map) {
    List<Map<String, Object>> settingList = Lists.newLinkedList();
    for (Setting<?> setting : map.entries()) {
      Map<String, Object> elem = Maps.newHashMap();
      elem.put("id", setting.getKey().getId());
      // This does not deserialize:
      if (setting.getKey().getDescription() != null) {
        elem.put("description", setting.getKey().getDescription());
      }
      // This does not deserialize
      elem.put("restricted", setting.getKey().isPlayerRestrictive());
      elem.put("value", setting.getKey().dataToJson(setting.getValue().getData()));
      elem.put("target", SettingValue.Target.toJson(setting.getValue().getTarget()));
      settingList.add(elem);
    }
    return new Gson().toJsonTree(settingList);
  }

  /**
   * Rebuild a SettingMap from a JsonElement that was stored
   * in some persistent storage location.
   *
   * @param json the json object
   * @return the restored SettingMap
   */
  @SuppressWarnings("unchecked")
  public static SettingMap deserializeSettingAssignments(JsonElement json, String hostName) {
    JsonElement element = json.getAsJsonObject().get("settings");
    SettingMap map = new SettingMap();

    if (element == null) {
      return map;
    }

    JsonArray serializedSettings = element.getAsJsonArray();
    for (JsonElement serializedSetting : serializedSettings) {
      JsonObject object = serializedSetting.getAsJsonObject();
      SettingKey<?> key;

      try {
        key = lookup(object.get("id").getAsString());
      } catch (NoSuchElementException e) {
        Optional<? extends Setting<?>> updated = SettingUpdates.convertSetting(
            object.get("id").getAsString(),
            object.get("value"),
            object.get("target"));
        if (updated.isPresent()) {
          Nope.getInstance().getLogger().warn("Old SettingKey id: "
              + object.get("id")
              + ". This was replaced with the a new Setting with SettingKey: "
              + updated.get().getKey().getId());
          map.put(updated.get());
        } else {
          Nope.getInstance().getLogger().error("Invalid SettingKey id: "
              + object.get("id")
              + ". Is this old? Skipping...");
        }
        continue;
      }

      SettingValue<Object> val;
      try {
        val = SettingValue.of(
            key.dataFromJson(object.get("value")),
            SettingValue.Target.fromJson(object.get("target")));
      } catch (SettingKey.ParseSettingException e) {
        Nope.getInstance().getLogger().error("Host: "
            + hostName
            + ", Invalid SettingKey value: "
            + object.get("value")
            + ". Is this old? Skipping...");
        continue;
      } catch (Exception generalException) {
        Nope.getInstance().getLogger().error("Couldn't deserialize setting: " + key.getId());
        continue;
      }
      map.put(Setting.of((SettingKey<Object>) key, val));
    }
    return map;
  }

  /**
   * Enumeration for all movement types considered by Nope.
   */
  public enum Movement {
    ALL,
    NATURAL,
    NONE,
    UNNATURAL
  }

  /**
   * Enumeration for all storage types considered by Nope.
   */
  public enum Storage {
    MARIADB,
    SQLITE,
    HOCON
  }

  /**
   * Enumeration for all explosive types considered by Nope.
   */
  public enum Explosive {
    CREEPER(Creeper.class),
    ENDERCRYSTAL(EnderCrystal.class),
    FIREWORK(Firework.class),
    LARGEFIREBALL(LargeFireball.class),
    PRIMEDTNT(PrimedTNT.class),
    TNTMINECART(TNTMinecart.class),
    WITHER(Wither.class),
    WITHERSKULL(WitherSkull.class);

    private final Class<? extends org.spongepowered.api.entity.explosive.Explosive> wrapped;

    Explosive(Class<? extends org.spongepowered.api.entity.explosive.Explosive> wrapped) {
      this.wrapped = wrapped;
    }

    public Class<? extends org.spongepowered.api.entity.explosive.Explosive> getExplosive() {
      return wrapped;
    }
  }

  /**
   * A description of a {@link SettingKey}.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface Description {
    /**
     * The description of a {@link SettingKey}.
     *
     * @return SettingKey description
     */
    String value();
  }

  /**
   * A short description of a {@link SettingKey}.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface Blurb {
    /**
     * A very short description of a {@link SettingKey}.
     *
     * @return SettingKey blurb
     */
    String value();
  }

  /**
   * An annotation to mark a {@link SettingKey} as
   * only to be set on a global host.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface Global {
    // Empty
  }

  /**
   * An annotation to mark a {@link SettingKey}'s category
   * for sorting and listing purposes.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface Category {
    /**
     * The category of a {@link SettingKey}.
     *
     * @return SettingKey category
     */
    SettingKey.CategoryType value();
  }

  /**
   * Marks a {@link SettingKey} as not implemented
   * for formatting purposes.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface NotImplemented {
    // Empty
  }

  /**
   * Marks a {@link SettingKey} as player-restrictive,
   * meaning that a changed setting key's value would restrict
   * a player if it applied to that player from normal Minecraft game behavior.
   * For the purposes of not encumbering administrators.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface PlayerRestrictive {
    // Empty
  }

  /**
   * An annotation for SettingKeys to designate its
   * default value as unnatural. This is used for
   * listener initializations.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface UnnaturalDefault {
    // Empty
  }

}
