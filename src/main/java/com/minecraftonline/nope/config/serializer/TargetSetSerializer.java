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

package com.minecraftonline.nope.config.serializer;

import com.google.common.reflect.TypeToken;
import com.minecraftonline.nope.control.target.Target;
import com.minecraftonline.nope.control.target.TargetSet;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.util.TypeTokens;

public class TargetSetSerializer implements TypeSerializer<TargetSet> {
  @Nullable
  @Override
  public TargetSet deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode value) throws ObjectMappingException {
    TargetSet targetSet = new TargetSet();
    for (Target.TargetType targetType : Target.TargetType.values()) {
      ConfigurationNode node = value.getNode(targetType.getKey());
      node.getList(TypeTokens.STRING_TOKEN).stream()
          .map(targetType::deserialize)
          .forEach(targetSet::add);
    }
    return targetSet;
  }

  @Override
  public void serialize(@NonNull TypeToken<?> type, @Nullable TargetSet obj, @NonNull ConfigurationNode value) throws ObjectMappingException {
    if (obj == null) {
      throw new ObjectMappingException("Cannot serialize a null TargetSet");
    }
    for (Target.TargetType targetType : obj.getTargets().keySet()) {
      value.getNode(targetType.getKey()).setValue(obj.getTargets().get(targetType));
    }
  }
}
