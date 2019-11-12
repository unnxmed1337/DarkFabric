package uk.co.hexeption.darkforgereborn.mod;

import uk.co.hexeption.darkforgereborn.IMC;

/**
 * Category
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 09/11/2019 - 03:30 am
 */
public enum Category implements IMC {
    COMBAT("Combat", "Combat related mods", 0xFFFF0000),
    PLAYER("Player", "Mods that interact with the local player", 0xFFFF0000),
    MOVEMENT("Movement", "Mods that interact with the movement of the player", 0xFFFF0000),
    RENDER("Render", "2D/3D rendering mods", 0xFFFF0000),
    WORLD("World", "Any mod that has to do with the world", 0xFFFF0000),
    MISC("Misc", "Miscellaneous", 0xFFFF0000),
    NONE("", "", 0xFFFF0000),
    ;

    private String name;
    private String description;
    private int color;

    Category(String name, String description, int color) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getColor() {
        return color;
    }

}
