package io.github.teddyxlandlee.mcmod.microlib.mixin;

import bilibili.teddyxlandlee.microlib.tag.TagInsertion;
import io.github.teddyxlandlee.mcmod.microlib.tag.TagInsertionsImpl;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(TagGroupLoader.class)
public class MixinTagGroupLoader {
    @Shadow @Final private String dataType;

    @Inject(method = "loadTags", at = @At("RETURN"))
    private void insertInsertions(ResourceManager rm, CallbackInfoReturnable<Map<Identifier, Tag.Builder>> cir) {
        Map<Identifier, Tag.Builder> m = cir.getReturnValue();  // HashMap
        for (TagInsertion.TagModification event : TagInsertionsImpl.fromDataType(dataType).getEvents()) {
            event.accept(m);
        }
    }
}
