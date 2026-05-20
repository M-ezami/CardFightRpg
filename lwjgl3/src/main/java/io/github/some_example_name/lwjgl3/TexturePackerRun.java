package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackerRun {

    public static void main(String[] args) {

        TexturePacker.Settings settings = new TexturePacker.Settings();

        /* optional tweaks (you can leave defaults too)
        settings.paddingX = 2;
        settings.paddingY = 2;
        settings.edgePadding = true;
        settings.duplicatePadding = true;
*/
        /*
        TexturePacker.process(
            "assets/card",   // input folder (your PNGs)
            "assets/ui",       // output folder
            "card"               // atlas name → ui.atlas + ui.png
        );

        System.out.println("Atlas packed!");
    }

         */

        TexturePacker.process(
            "assets/slime",   // input folder (your PNGs)
            "assets/slime",       // output folder
            "slime"               // atlas name → ui.atlas + ui.png
        );
    }

    }
