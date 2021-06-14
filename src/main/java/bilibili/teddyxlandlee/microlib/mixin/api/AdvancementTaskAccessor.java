package bilibili.teddyxlandlee.microlib.mixin.api;

import net.minecraft.advancement.Advancement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Advancement.Task.class)
public interface AdvancementTaskAccessor {
    @Accessor
    String[][] getRequirements();

    @Accessor @Mutable
    void setRequirements(String[][] req);
}
