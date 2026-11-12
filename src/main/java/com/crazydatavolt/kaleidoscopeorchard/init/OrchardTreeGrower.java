package com.crazydatavolt.kaleidoscopeorchard.init;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class OrchardTreeGrower {
    public static final TreeGrower FRUIT_TREE_GROWER = buildSimpleTreeGrower("fruit_tree");
    public static final TreeGrower ORANGE_TREE_GROWER = buildSimpleTreeGrower("orange_tree");
    public static final TreeGrower KIWI_TREE_GROWER = buildSimpleTreeGrower("kiwi_tree");
    public static final TreeGrower LEMON_TREE_GROWER = buildSimpleTreeGrower("lemon_tree");

    private static TreeGrower buildSimpleTreeGrower(String name){
        return new TreeGrower(
                name,
                Optional.empty(),
                Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, KaleidoscopeOrchard.path(name))),
                Optional.empty()
        );
    }
}
