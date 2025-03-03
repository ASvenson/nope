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

package com.minecraftonline.nope.command;

import com.google.common.collect.Maps;
import com.minecraftonline.nope.Nope;
import com.minecraftonline.nope.arguments.NopeArguments;
import com.minecraftonline.nope.command.common.CommandNode;
import com.minecraftonline.nope.command.common.FlagDescription;
import com.minecraftonline.nope.command.common.LambdaCommandNode;
import com.minecraftonline.nope.host.Host;
import com.minecraftonline.nope.permission.Permissions;
import com.minecraftonline.nope.setting.SettingKey;
import com.minecraftonline.nope.setting.SettingValue;
import com.minecraftonline.nope.util.Format;
import java.util.Map;
import java.util.Optional;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

class TargetTypeCommand extends LambdaCommandNode {
  public TargetTypeCommand(CommandNode parent) {
    super(parent,
        Permissions.COMMAND_EDIT,
        Text.of("Set how a setting targets specific players in this zone"),
        "type");
    Map<String, Boolean> choices = Maps.newHashMap();
    choices.put("whitelist", true);
    choices.put("blacklist", false);
    addCommandElements(GenericArguments.flags()
            .valueFlag(NopeArguments.host(Text.of("zone")), "z", "-zone")
            .buildWith(GenericArguments.none()),
        NopeArguments.settingKey(Text.of("setting")),
        GenericArguments.choices(Text.of("type"), choices));
    addFlagDescription(FlagDescription.ZONE);
    setExecutor((src, args) -> {
      Host host = args.<Host>getOne("zone").orElse(NopeCommandRoot.inferHost(src).orElse(null));
      if (host == null) {
        return CommandResult.empty();
      }
      SettingKey<Object> key = args.requireOne("setting");
      boolean whitelist = args.requireOne("type");

      Optional<SettingValue<Object>> value = host.get(key);
      if (!value.isPresent()) {
        src.sendMessage(Format.error("The setting ",
            Format.settingKey(key, false),
            " is not set on zone ",
            Format.host(host)));
        return CommandResult.empty();
      }

      boolean changed;
      if (whitelist) {
        changed = value.get().getTarget().setWhitelist();
      } else {
        changed = value.get().getTarget().setBlacklist();
      }

      if (changed) {
        src.sendMessage(Format.success("The setting ",
            Format.settingKey(key, false),
            " was changed"));
        Nope.getInstance().saveState();
        return CommandResult.success();
      } else {
        src.sendMessage(Format.warn("The setting ",
            Format.settingKey(key, false),
            " already has that type!"));
        return CommandResult.empty();
      }
    });
  }
}
