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
 */

package com.minecraftonline.nope.control;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

public class RegularRegion extends Region {
  @Nullable
  private AABB aabb;
  private UUID worldUUID;

  public RegularRegion(World world) {
    this.worldUUID = world.getUniqueId();
  }

  public RegularRegion(Location<World> corner1, Location<World> corner2) {
    if (!corner1.getExtent().equals(corner2.getExtent())) {
      throw new IllegalStateException("Cannot have a region with corners in different worlds");
    }
    this.worldUUID = corner1.getExtent().getUniqueId();
    this.aabb = new AABB(corner1.getBlockPosition(), corner2.getBlockPosition());
    super.set(Settings.REGION_MIN, this.aabb.getMin().toInt());
    super.set(Settings.REGION_MAX, this.aabb.getMax().toInt());
  }

  public RegularRegion(World world, Vector3i corner1, Vector3i corner2) {
    this.worldUUID = world.getUniqueId();
    this.aabb = new AABB(corner1, corner2);
    super.set(Settings.REGION_MIN, this.aabb.getMin().toInt());
    super.set(Settings.REGION_MAX, this.aabb.getMax().toInt());
  }

  @Override
  public boolean isLocationInRegion(Vector3i location) {
    return aabb.contains(location);
  }

  public AABB getAabb() {
    return aabb;
  }

  @Override
  public <T extends Serializable> void set(Setting<T> setting, T value) {
    super.set(setting, value);
    if (setting.equals(Settings.REGION_MIN)
        || setting.equals(Settings.REGION_MAX)) {
      updateAABB();
    }
  }

  @Override
  public <T extends Serializable> Optional<T> unset(Setting<T> setting) {
    if (setting.equals(Settings.REGION_MIN) || setting.equals(Settings.REGION_MAX)) {
      throw new UnsupportedOperationException("Cannot remove region min or max of RegularRegion");
    }
    return super.unset(setting);
  }

  /**
   * For performance reasons, regions cache their AABB box, which is pretty unlikely to change,
   * if you do change it, update it
   */
  public void updateAABB() {
    getSettingValue(Settings.REGION_MIN).ifPresent(min ->
        getSettingValue(Settings.REGION_MAX).ifPresent(max -> {
          this.aabb = new AABB(min, max);
        })
    );
  }

}
