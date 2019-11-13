package uk.co.hexeption.darkforgereborn.command.commands;

import net.minecraft.util.text.StringTextComponent;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.command.Command;
import uk.co.hexeption.darkforgereborn.command.Command.CMDInfo;
import uk.co.hexeption.darkforgereborn.command.Command.CommandNotLoad;
import uk.co.hexeption.darkforgereborn.mod.Mod;

/**
 * ModCommand
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 05:38 pm
 */
@CommandNotLoad
@CMDInfo(name = {"mod"}, help = "dummy", description = "dummy.")
public class ModCommand extends Command {

    public ModCommand(String[] name, String description, String help) {
        setName(name);
        setDescription(description);
        setHelp(help);
    }

    @Override
    public void execute(String input, String[] args) throws Exception {
        Mod mod = DarkForgeReborn.INSTANCE.modManager.getModByName(input);
        if (args.length == 0) {
            mod.toggle();
            mc.player.sendMessage(new StringTextComponent(String.format("%s: %s", mod.getName(), mod.getState() ? "On" : "Off")));
        }
    }
}
