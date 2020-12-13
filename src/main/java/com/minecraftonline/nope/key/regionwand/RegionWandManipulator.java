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

package com.minecraftonline.nope.key.regionwand;

import com.minecraftonline.nope.key.NopeKeys;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractBooleanData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class RegionWandManipulator extends AbstractBooleanData<RegionWandManipulator, ImmutableRegionWandManipulator> {
  public static final DataQuery QUERY = DataQuery.of("noperegionwand");

  public RegionWandManipulator(boolean value) {
    super(NopeKeys.REGION_WAND, value, false);
  }

  public Value<Boolean> isWand() {
    return this.getValueGetter();
  }

  @Override
  public Optional<RegionWandManipulator> fill(DataHolder dataHolder, MergeFunction overlap) {
    return from(dataHolder.toContainer());
  }

  @Override
  public Optional<RegionWandManipulator> from(DataContainer container) {
    return container.getBoolean(NopeKeys.REGION_WAND.getQuery()).map(RegionWandManipulator::new);
  }

  @Override
  public RegionWandManipulator copy() {
    return new RegionWandManipulator(this.getValue());
  }

  @Override
  public ImmutableRegionWandManipulator asImmutable() {
    return new ImmutableRegionWandManipulator(this.getValue());
  }

  @Override
  public int getContentVersion() {
    return 0;
  }

  public static class Builder implements DataManipulatorBuilder<RegionWandManipulator, ImmutableRegionWandManipulator> {

    @Override
    public RegionWandManipulator create() {
      return new RegionWandManipulator(false);
    }

    @Override
    public Optional<RegionWandManipulator> createFrom(DataHolder dataHolder) {
      return build(dataHolder.toContainer());
    }

    @Override
    public Optional<RegionWandManipulator> build(DataView container) throws InvalidDataException {
      return container.getBoolean(NopeKeys.REGION_WAND.getQuery()).map(RegionWandManipulator::new);
    }
  }
}
