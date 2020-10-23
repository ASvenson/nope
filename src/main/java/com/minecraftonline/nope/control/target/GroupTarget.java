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

package com.minecraftonline.nope.control.target;

import org.spongepowered.api.entity.living.player.Player;

import java.util.Objects;

public class GroupTarget implements Target {
  private final String groupName;

  public GroupTarget(String group) {
    this.groupName = group;
  }

  @Override
  public boolean isTargeted(Player player) {
    return player.hasPermission("group." + groupName);
  }

  @Override
  public String serialize() {
    return groupName;
  }

  @Override
  public TargetType getTargetType() {
    return TargetType.GROUP;
  }

  public static GroupTarget deserialize(String string) {
    return new GroupTarget(string);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GroupTarget that = (GroupTarget) o;
    return groupName.equals(that.groupName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupName);
  }
}
