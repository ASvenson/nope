/*
 * MIT License
 *
 * Copyright (c) 2021 MinecraftOnline
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

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.Optional;

/**
 * A setting to store a "state", which is essentially a boolean but
 * is formatted as "allow" and "deny" instead of
 * "true" and "false".
 */
public class StateSettingKey extends SettingKey<Boolean> {
  public StateSettingKey(String id, Boolean defaultValue) {
    super(id, defaultValue);
  }

  @Override
  public JsonElement dataToJsonGenerified(Boolean value) {
    return new JsonPrimitive(value ? "allow" : "deny");
  }

  @Override
  public Boolean dataFromJsonGenerified(JsonElement jsonElement) {
    final String s = jsonElement.getAsString();
    return parse(s);
  }

  @Override
  public Boolean parse(String s) throws ParseSettingException {
    switch (s.toLowerCase()) {
      case "allow":
      case "true":
        return true;
      case "deny":
      case "false":
        return false;
      default:
        throw new ParseSettingException("Invalid state string. "
            + "Should be allow or deny. Was: " + s);
    }
  }

  @Override
  public Optional<List<String>> getParsable() {
    return Optional.of(Lists.newArrayList("allow", "deny"));
  }
}
