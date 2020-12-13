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

package com.minecraftonline.nope.command.region;

import com.minecraftonline.nope.arguments.NopeArguments;
import com.minecraftonline.nope.arguments.RegionWrapper;
import com.minecraftonline.nope.command.common.CommandNode;
import com.minecraftonline.nope.command.common.LambdaCommandNode;
import com.minecraftonline.nope.control.GlobalRegion;
import com.minecraftonline.nope.control.Region;
import com.minecraftonline.nope.control.Setting;
import com.minecraftonline.nope.control.Settings;
import com.minecraftonline.nope.control.flags.Flag;
import com.minecraftonline.nope.control.target.Target;
import com.minecraftonline.nope.control.target.TargetSet;
import com.minecraftonline.nope.permission.Permissions;
import com.minecraftonline.nope.util.Format;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import java.util.Map;

public class RegionInfoCommand extends LambdaCommandNode {
  public RegionInfoCommand(CommandNode parent) {
    super(parent,
        Permissions.INFO_REGION,
        Text.of("View detailed information about a region"),
        "info",
        "i");

    setCommandElement(GenericArguments.onlyOne(NopeArguments.regionWrapper(Text.of("region"))));
    setExecutor((src, args) -> {
      RegionWrapper regionWrapper = args.<RegionWrapper>getOne(Text.of("region")).get();
      Region region = regionWrapper.getRegion();
      boolean isGlobal = region instanceof GlobalRegion;
      src.sendMessage(Format.info("-- Info for region " + regionWrapper.getRegionName() + " --"));

      if (!isGlobal) {
        // Non global regions only:
        src.sendMessage(Format.regionInfo("min: ", region.getSettingValue(Settings.REGION_MIN).get().toString()));
        src.sendMessage(Format.regionInfo("min: ", region.getSettingValue(Settings.REGION_MAX).get().toString()));
      }

      // All regions
      src.sendMessage(Format.regionInfo("owners: ", serializeTargetSet(region.getSettingValue(Settings.REGION_OWNERS).orElse(new TargetSet()))));
      src.sendMessage(Format.regionInfo("members: ", serializeTargetSet(region.getSettingValue(Settings.REGION_MEMBERS).orElse(new TargetSet()))));
      src.sendMessage(Format.regionInfo("priority: ", region.getSettingValue(Settings.REGION_PRIORITY).orElse(0).toString()));

      // Flags
      StringBuilder builder = new StringBuilder("{ ");
      for (Map.Entry<Setting<?>, ?> settingEntry : regionWrapper.getRegion().getSettingMap().entrySet()) {
        if (!(settingEntry.getValue() instanceof Flag)) {
          continue; // Only look at flags
        }
        Flag<?> flag = (Flag<?>) settingEntry.getValue();
        Flag<?> defaultValue = (Flag<?>) settingEntry.getKey().getDefaultValue();

        String settingName = settingEntry.getKey().getName();
        builder.append(settingName).append(": ").append(serialize(defaultValue, flag)).append(",");
        if (flag.getGroup() != Flag.TargetGroup.ALL) {
          builder.append(" ").append(settingName).append("-group: ").append(flag.getGroup().toString().toLowerCase());
        }
      }
      builder.deleteCharAt(builder.length() - 1).append(" }");

      src.sendMessage(Format.regionInfo("flags: ", builder.toString()));
      return CommandResult.success();
    });
  }

  @SuppressWarnings("unchecked")
  private static <T> String serialize(Flag<T> defaultValue, Flag<?> value) {
    return defaultValue.serialize((Flag<T>)value);
  }

  private static String serializeTargetSet(TargetSet targetSet) {
    StringBuilder builder = new StringBuilder("{ ");
    for (Map.Entry<Target.TargetType, Target> entry : targetSet.getTargets().entries()) {
      switch (entry.getKey()) {
        case PLAYER: {
          builder.append("p:");
          break;
        }
        case GROUP: {
          builder.append("g:");
          break;
        }
        default: {
          throw new IllegalStateException("Missed an enum!");
        }
      }
      builder.append(entry.getValue().serialize()).append(",");
    }
    // Remove trailing comma, add closing bracket.
    builder.deleteCharAt(builder.length() - 1).append(" }");
    return builder.toString();
  }
}
