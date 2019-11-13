package uk.co.hexeption.darkforgereborn.command.commands;

import uk.co.hexeption.darkforgereborn.command.Command;
import uk.co.hexeption.darkforgereborn.command.Command.CMDInfo;
import uk.co.hexeption.darkforgereborn.util.LogHelper;

/**
 * TestCommand
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 05:04 pm
 */
@CMDInfo(name = {"test", "ts"}, help = "test are good", description = "This is a test command.")
public class TestCommand extends Command {

    @Override
    public void execute(String input, String[] args) throws Exception {
        LogHelper.info("Test Command");
    }
}
