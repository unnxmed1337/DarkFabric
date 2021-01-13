package com.github.darkforge.command.impl;

import com.github.darkforge.command.AbstractCommand;
import com.github.darkforge.util.LogHelper;

/**
 * TestCommand
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 05:04 pm
 */
@AbstractCommand.Info(aliases = {"test", "ts"}, help = "test are good", description = "This is a test command.")
public class Test extends AbstractCommand {

    @Override
    public boolean execute(String[] args) {
        LogHelper.info("Test Command");
        return true;
    }

}