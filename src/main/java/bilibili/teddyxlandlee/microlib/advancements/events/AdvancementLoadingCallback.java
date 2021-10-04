package bilibili.teddyxlandlee.microlib.advancements.events;

import bilibili.teddyxlandlee.microlib.SimpleEvent;
import bilibili.teddyxlandlee.microlib.advancements.CommonAdvancementHelper;
import bilibili.teddyxlandlee.microlib.hooks.AdvancementTaskHooks;
import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.util.Identifier;
import org.apiguardian.api.API;

/**
 * <p>Of course we can create advancements by writing JSONs.
 * However, if we want to modify the advancements from vanilla,
 * then overwrite them with JSONs will no longer be a good
 * idea.</p><p>This callback enables us to modify them by
 * using event system. This can prove the cross-mod
 * advancement modification compatibility.</p>
 *
 * @see CommonAdvancementHelper
 */
@FunctionalInterface
@API(status = API.Status.STABLE)
public interface AdvancementLoadingCallback {
    void onLoadAdvancement(Identifier id, JsonObject json, Advancement.Task task, AdvancementTaskHooks taskAccessor);

    /**
     * <p>The original event. Here's an example about how to
     * use it:</p><pre><code>
     * Identifier target = new Identifier("husbandry/plant_seed");
     * AdvancementLoadingCallback.EVENT.register((id, json, task, acc) -> {
     *     if (!target.equals(id)) return;
     *
     *     String[][] requirementsOld = acc.getRequirements();
     *     String[][] requirementsNew = new String[requirementsOld.length + 1]
     *     System.arraycopy(requirementsOld, 0, requirementsNew, 1, requirementsOld.length);
     *
     *     requirementsNew[0] = task.criterion(
     *          "my_mod:husbandry$plant_seed$$my_mod_block",
     *          new new PlacedBlockCriterion.Conditions(
     *             EntityPredicate.Extended.EMPTY,
     *             MyMod.MY_MOD_BLOCK,
     *             StatePredicate.ANY,
     *             LocationPredicate.ANY,
     *             ItemPredicate.ANY
     *     ));
     *     acc.setRequirements(requirementsNew);
     * })
     * </code></pre>
     */
    SimpleEvent<AdvancementLoadingCallback> EVENT = SimpleEvent.of();
}
