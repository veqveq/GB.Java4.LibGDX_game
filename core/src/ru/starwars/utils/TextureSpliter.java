package ru.starwars.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureSpliter {

    public static TextureRegion[] split(TextureRegion region, int cols, int rows, int frames) {
        int wight = region.getRegionWidth() / cols;
        int height = region.getRegionHeight() / rows;
        int curFrame = 0;
        TextureRegion[] regions = new TextureRegion[frames];
            for (int j = 0; j < region.getRegionHeight() && curFrame < frames; j += height) {
                for (int k = 0; k < region.getRegionWidth() && curFrame < frames; k += wight) {
                    regions[curFrame] = new TextureRegion(region,k,j,wight,height);
                    curFrame++;
                }
            }
        return regions;
    }
}
