package components.font;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader {
    
    public static Font loadCustomFont(String fontPath, float fontSize) {
        Font customFont = null;
        try {
            // Load the font from a file (path to the font file)
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            customFont = customFont.deriveFont(Font.PLAIN, fontSize); // Set font size
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // If font loading fails, fall back to a default font
            customFont = new Font("Arial", Font.PLAIN, (int)fontSize);
        }
        return customFont;
    }
}