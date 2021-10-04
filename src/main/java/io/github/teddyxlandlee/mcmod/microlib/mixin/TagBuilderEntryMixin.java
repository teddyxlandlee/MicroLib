package io.github.teddyxlandlee.mcmod.microlib.mixin;

import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Tag.Builder.class)
// WARNING: Internal API, use it at your own risk!
public interface TagBuilderEntryMixin {
    @Accessor
    List<Tag.TrackedEntry> getEntries();
}
