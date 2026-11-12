package com.crazydatavolt.kaleidoscopeorchard.init;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class OrchardTag {
    public static class Items {
        public static final TagKey<Item> KNIFE = createTag("knife");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(KaleidoscopeOrchard.path(name));
        }
    }

    public static class Blocks{
        public static final TagKey<Block> NUTRIENT_SOIL = createTag("nutrient_soil");
        public static final TagKey<Block> COMMON_SOIL = createTag("common_soil");
        public static final TagKey<Block> RICH_SOIL = createTag("rich_soil");

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(KaleidoscopeOrchard.path(name));
        }
    }

    public static class CommonItems {
        public static final TagKey<Item> C_KNIFE = createCommonTag("knife");

        private static TagKey<Item> createCommonTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }
}
