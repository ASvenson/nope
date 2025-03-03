package com.minecraftonline.nope.command;

import com.minecraftonline.nope.Nope;
import com.minecraftonline.nope.command.common.CommandNode;
import com.minecraftonline.nope.command.common.LambdaCommandNode;
import com.minecraftonline.nope.game.listener.DynamicSettingListeners;
import com.minecraftonline.nope.permission.Permissions;
import com.minecraftonline.nope.util.Format;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.text.Text;

/**
 * The command to reload all config data from storage.
 */
public class ReloadCommand extends LambdaCommandNode {

  /**
   * Default constructor.
   *
   * @param parent parent node
   */
  public ReloadCommand(CommandNode parent) {
    super(parent, Permissions.COMMAND_RELOAD,
        Text.of("Reloads all config data from storage"),
        "reload", true);
    addAliases("load");

    setExecutor((src, args) -> {
      Nope.getInstance().loadState();
      DynamicSettingListeners.register();
      src.sendMessage(Format.success("Successfully reloaded"));
      return CommandResult.success();
    });
  }
}
