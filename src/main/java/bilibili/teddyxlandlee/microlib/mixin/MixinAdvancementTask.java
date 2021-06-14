package bilibili.teddyxlandlee.microlib.mixin;

import bilibili.teddyxlandlee.microlib.api.advancements.events.AdvancementLoadingCallback;
import bilibili.teddyxlandlee.microlib.mixin.api.AdvancementTaskAccessor;
import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Advancement.Task.class)
abstract class MixinAdvancementTask {
    @Inject(
            at = @At("RETURN"),
            method = "fromJson"
    )
    private static void applyLoadingEvents(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer, CallbackInfoReturnable<Advancement.Task> cir) {
        final Identifier id = predicateDeserializer.getAdvancementId();
        Advancement.Task task = cir.getReturnValue();
        AdvancementTaskAccessor acc = (AdvancementTaskAccessor) task;
        AdvancementLoadingCallback.EVENT.forEach(cb -> cb.onLoadAdvancement(id, obj, task, acc));
    }
}
