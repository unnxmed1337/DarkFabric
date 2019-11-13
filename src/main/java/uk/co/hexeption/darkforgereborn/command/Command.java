package uk.co.hexeption.darkforgereborn.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import uk.co.hexeption.darkforgereborn.IMC;

/**
 * Command
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 05:00 pm
 */
public abstract class Command implements IMC {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface CMDInfo {

        String[] name();

        String help();

        String description();
    }

    private String[] name = getClass().getAnnotation(CMDInfo.class).name();
    private String help = getClass().getAnnotation(CMDInfo.class).help();
    private String description = getClass().getAnnotation(CMDInfo.class).description();

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void execute(String input, String[] args) throws Exception;

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CommandNotLoad {

    }

}
