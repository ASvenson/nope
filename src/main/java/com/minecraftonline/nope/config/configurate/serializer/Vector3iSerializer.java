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

package com.minecraftonline.nope.config.configurate.serializer;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A serializer for a {@link Vector3i}.
 */
public class Vector3iSerializer implements TypeSerializer<Vector3i> {

  @Nullable
  @Override
  @SuppressWarnings("UnstableApiUsage")
  public Vector3i deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode value) {
    if (value.isVirtual()) {
      return null;
    }
    return new Vector3i(
        value.getNode("x").getInt(),
        value.getNode("y").getInt(),
        value.getNode("z").getInt()
    );
  }

  @Override
  @SuppressWarnings("UnstableApiUsage")
  public void serialize(@NonNull TypeToken<?> type,
                        @Nullable Vector3i obj,
                        @NonNull ConfigurationNode value) {
    if (obj == null) {
      return;
    }
    value.getNode("x").setValue(obj.getX());
    value.getNode("y").setValue(obj.getY());
    value.getNode("z").setValue(obj.getZ());
  }
}
