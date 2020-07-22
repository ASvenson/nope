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

package com.minecraftonline.nope.control.flags;

import org.spongepowered.api.entity.EntityType;

import java.util.Set;

public class FlagEntitySet extends Flag<Set<EntityType>> {
  public FlagEntitySet(Set<EntityType> value) {
    super(value, (Class<Set<EntityType>>) value.getClass());
  }

  public FlagEntitySet(Set<EntityType> value, TargetGroup group) {
    super(value, (Class<Set<EntityType>>) value.getClass(), group);
  }

  @Override
  public String serialize(Flag<Set<EntityType>> flag) {
    StringBuilder builder = new StringBuilder("{");
    flag.getValue().stream()
        .map(EntityType::getName)
        .forEach(name -> builder.append(" ").append(name).append(","));
    return builder.deleteCharAt(builder.length() - 1).append(" }").toString();
  }
}
