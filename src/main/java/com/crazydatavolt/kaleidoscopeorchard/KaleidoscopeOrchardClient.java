package com.crazydatavolt.kaleidoscopeorchard;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = KaleidoscopeOrchard.MODID, dist = Dist.CLIENT)
public class KaleidoscopeOrchardClient {
    public KaleidoscopeOrchardClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }


}
