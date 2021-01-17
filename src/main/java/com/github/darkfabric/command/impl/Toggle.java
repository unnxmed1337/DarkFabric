package com.github.darkfabric.command.impl;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.command.AbstractCommand;

/**
 * TestCommand
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 05:04 pm
 */
@AbstractCommand.Info(aliases = {"toggle", "t"}, help = "toggle <module>", description = "Toggle a registered module.")
public class Toggle extends AbstractCommand {

    @Override
    public boolean execute(String[] args) {
        if (args.length != 1) return true;
        try {
            DarkFabric.getInstance().getModuleRegistry().getByName(args[0]).toggle();
        } catch (Exception ignored) {
        }
        return false;
    }

}