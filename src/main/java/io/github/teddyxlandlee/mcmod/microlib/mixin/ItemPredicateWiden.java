package io.github.teddyxlandlee.mcmod.microlib.mixin;

import net.minecraft.potion.Potion;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemPredicate.class)
// WARNING: Internal API, use it at your own risk!
public interface ItemPredicateWiden {
    @Accessor
    NumberRange.IntRange getCount();
    @Accessor
    NumberRange.IntRange getDurability();
    @Accessor
    EnchantmentPredicate[] getEnchantments();
    @Accessor
    EnchantmentPredicate[] getStoredEnchantments();
    @Nullable
    @Accessor
    Potion getPotion();
    @Accessor
    NbtPredicate getNbt();
}
