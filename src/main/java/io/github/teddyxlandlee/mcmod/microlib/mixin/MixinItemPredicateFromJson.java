package io.github.teddyxlandlee.mcmod.microlib.mixin;

import bilibili.teddyxlandlee.microlib.predicate.ItemSetPredicate;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(ItemPredicate.class)
public abstract class MixinItemPredicateFromJson {
    @Inject(method = "fromJson", at = @At("RETURN"), cancellable = true)
    private static void fromJson(JsonElement el, CallbackInfoReturnable<ItemPredicate> cir) {
        if (el != null && !el.isJsonNull()) {
            JsonObject obj = el.getAsJsonObject();
            if (JsonHelper.hasArray(obj, "items")) {
                int i = 0;
                Set<Item> items = new HashSet<>();
                JsonArray arr = JsonHelper.getArray(obj, "items");
                for (JsonElement element : arr) {
                    String id = JsonHelper.asString(element, "item #" + i++);
                    items.add(Registry.ITEM.get(new Identifier(id)));
                }
                if (!items.isEmpty()) {
                    ItemPredicateWiden s = (ItemPredicateWiden) cir.getReturnValue();
                    cir.setReturnValue(new ItemSetPredicate(
                            items, s.getCount(), s.getDurability(),
                            s.getEnchantments(), s.getStoredEnchantments(), s.getPotion(), s.getNbt()
                    ));
                }
            }
        }
    }
}
