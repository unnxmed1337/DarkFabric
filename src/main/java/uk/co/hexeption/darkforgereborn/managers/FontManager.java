package uk.co.hexeption.darkforgereborn.managers;


import java.awt.Font;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.font.MinecraftFontRenderer;

/**
 * FontManager
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 06:15 pm
 */
public class FontManager {

    private static String fontName = "Comfortaa";

    public MinecraftFontRenderer hud = new MinecraftFontRenderer();

    public MinecraftFontRenderer arraylist = new MinecraftFontRenderer();

    public MinecraftFontRenderer mainMenu = new MinecraftFontRenderer();

    public MinecraftFontRenderer button = new MinecraftFontRenderer();

    public MinecraftFontRenderer buttonHoverd = new MinecraftFontRenderer();

    public MinecraftFontRenderer chat = new MinecraftFontRenderer();

    public MinecraftFontRenderer guiTitle = new MinecraftFontRenderer();

    public MinecraftFontRenderer clickGui = new MinecraftFontRenderer();

    public MinecraftFontRenderer huzuni = new MinecraftFontRenderer();

    public MinecraftFontRenderer hud_big = new MinecraftFontRenderer();

    public MinecraftFontRenderer hud_small = new MinecraftFontRenderer();


    public static String getFontName() {

        return fontName;
    }

    public static void setFontName(String fontName) {

        FontManager.fontName = fontName;
        DarkForgeReborn.INSTANCE.fontManager.init();
    }

    public void init() {

        hud.setFont(new Font(fontName, Font.PLAIN, 22), true);
        arraylist.setFont(new Font(fontName, Font.PLAIN, 18), true);
        mainMenu.setFont(new Font(fontName, Font.PLAIN, 50), true);
        button.setFont(new Font(fontName, Font.PLAIN, 22), true);
        buttonHoverd.setFont(new Font(fontName, Font.PLAIN, 25), true);
        chat.setFont(new Font("Verdana", Font.PLAIN, 18), true);
        guiTitle.setFont(new Font(fontName, Font.PLAIN, 17), true);
        clickGui.setFont(new Font(fontName, Font.PLAIN, 16), true);
        huzuni.setFont(new Font("Roboto Condensed", Font.PLAIN, 16), true);
        hud_big.setFont(new Font(fontName, Font.PLAIN, 40), true);
        hud_small.setFont(new Font(fontName, Font.PLAIN, 12), true);
    }
}