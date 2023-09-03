package ru.aniby.felmoncapes;

import net.fabricmc.api.ClientModInitializer;
import ru.aniby.felmoncapes.utils.Capes;

public class FelmonCapesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FelmonCapes.config = new FelmonConfig("felmoncapes.json");
        FelmonCapes.config.load();
        Capes.initCapeList();
    }
}
