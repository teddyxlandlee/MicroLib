package bilibili.teddyxlandlee.microlib.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface TickableBlockEntity {
    void tick(World world, BlockPos pos, BlockState state);

    static <T extends BlockEntity & TickableBlockEntity> void tick(World world, BlockPos pos, BlockState state,
                                                                   @NotNull T blockEntity) {
        blockEntity.tick(world, pos, state);
    }
}
