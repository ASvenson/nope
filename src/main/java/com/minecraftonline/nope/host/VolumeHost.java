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

import com.minecraftonline.nope.structures.VolumeTree;

import java.util.UUID;

public class VolumeHost extends Host implements VolumeTree.Volume, Worlded {

  private final UUID worldUuid;

  private final int xmin;
  private final int xmax;

  private final int ymin;
  private final int ymax;

  private final int zmin;
  private final int zmax;

  public VolumeHost(UUID worldUuid, String name, int xmin, int xmax, int ymin, int ymax, int zmin, int zmax) {
    super(name);
    this.worldUuid = worldUuid;
    if (xmin > xmax || ymin > ymax || zmin > zmax) {
      throw new IllegalArgumentException("Minimum values must be less than or equal to maximum values");
    }
    this.xmin = xmin;
    this.xmax = xmax;
    this.ymin = ymin;
    this.ymax = ymax;
    this.zmin = zmin;
    this.zmax = zmax;
  }

  @Override
  public int xMin() {
    return xmin;
  }

  @Override
  public int xMax() {
    return xmax;
  }

  @Override
  public int yMin() {
    return ymin;
  }

  @Override
  public int yMax() {
    return ymax;
  }

  @Override
  public int zMin() {
    return zmin;
  }

  @Override
  public int zMax() {
    return zmax;
  }

  @Override
  public UUID getWorldUuid() {
    return this.worldUuid;
  }
}
