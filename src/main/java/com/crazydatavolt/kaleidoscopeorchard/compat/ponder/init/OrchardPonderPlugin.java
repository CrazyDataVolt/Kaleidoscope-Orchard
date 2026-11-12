package com.crazydatavolt.kaleidoscopeorchard.compat.ponder.init;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.crazydatavolt.kaleidoscopeorchard.compat.ponder.scene.FruitTreeScene;
import com.crazydatavolt.kaleidoscopeorchard.compat.ponder.scene.RichSoilScene;
import com.crazydatavolt.kaleidoscopeorchard.register.ItemRegister;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.resources.ResourceLocation;

public class OrchardPonderPlugin implements PonderPlugin {

    @Override
    public String getModId() {
        return KaleidoscopeOrchard.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(KaleidoscopeOrchard.path("fruit_tree_sapling"))
                .addStoryBoard("fruit_tree_sapling_1", FruitTreeScene::scene1)
                .addStoryBoard("fruit_tree_sapling_2", FruitTreeScene::scene2);
        helper.forComponents(KaleidoscopeOrchard.path("rich_soil"), KaleidoscopeOrchard.path("rich_soil_farmland"))
                .addStoryBoard("rich_soil_1", RichSoilScene::scene1);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        ResourceLocation orchard = KaleidoscopeOrchard.path("orchard");

        helper.registerTag(orchard).addToIndex()
                .item(ItemRegister.FRUIT_TREE_SAPLING, true, false)
                .title("森罗物语：果园")
                .description("水果！")
                .register();

        helper.addToTag(orchard)
                .add(KaleidoscopeOrchard.path("fruit_tree_sapling"))
                .add(KaleidoscopeOrchard.path("rich_soil"));
    }

    public static void init() {
        PonderIndex.addPlugin(new OrchardPonderPlugin());
    }
}
