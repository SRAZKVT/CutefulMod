package cutefulmod.mixin;

import cutefulmod.CutefulMod;
import cutefulmod.utils.CommandUtils;
import cutefulmod.render.CutefulRenderController;
import cutefulmod.utils.CutefulUtils;
import cutefulmod.utils.TntToRender;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

	//@Shadow public abstract ActionResult interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult);
	@Shadow public abstract float getReachDistance();

	@Shadow public abstract ActionResult interactBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult);

	@Inject(at = @At("HEAD"), method = "interactEntity", cancellable = true)
	public void bypassItemFrame(PlayerEntity player, Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if (entity instanceof ItemFrameEntity) {
			ItemFrameEntity itemFrame = (ItemFrameEntity) entity;
			if (!player.isSneaking() && (!itemFrame.getHeldItemStack().isEmpty()) && CutefulMod.config.BYPASS_ITEM_FRAME_ENTITY) {
				MinecraftClient client = MinecraftClient.getInstance();
				BlockPos blockToClick = itemFrame.getBlockPos().offset(itemFrame.getHorizontalFacing().getOpposite());
				Block hit = itemFrame.getEntityWorld().getBlockState(blockToClick).getBlock();
				if (canBeClicked(hit)) {
					BlockHitResult hitResult = (BlockHitResult) player.raycast(getReachDistance(), 1, false);
					if (hitResult.getBlockPos().equals(blockToClick)) {
						ActionResult actionResult = interactBlock(client.player, hand, hitResult);
						cir.setReturnValue(actionResult);
					}
				}
			}
		}else if (entity instanceof TntEntity) {
			if (CutefulMod.config.TNT_RANGE_VISUALIZER) {
				TntToRender tnt = new TntToRender((TntEntity) entity);
				if (CutefulRenderController.getTntToRender().contains(tnt)) {
					CutefulRenderController.getTntToRender().remove(tnt);
				} else {
					CutefulRenderController.getTntToRender().add(tnt);
				}
			}
			if (CutefulMod.config.TNT_RAY_COUNT) {
				if (CommandUtils.getBlockToCheckRaysOn() != null) {
					CutefulUtils.simulateExplosion(1, (TntEntity) entity, true);
				} else {
					assert MinecraftClient.getInstance().player != null;
					MinecraftClient.getInstance().player.sendMessage(Text.literal("You didn't set a position to check rays for."),false);
				}
			}
			if (CutefulMod.config.TNT_RAY_COUNT || CutefulMod.config.TNT_RANGE_VISUALIZER) {
				cir.setReturnValue(ActionResult.SUCCESS);
			}
		}
	}

	public boolean canBeClicked(Block block) {
		return block instanceof CraftingTableBlock || block instanceof NoteBlock || block instanceof BlockWithEntity && !(block instanceof JukeboxBlock || block instanceof BeehiveBlock || block instanceof EndGatewayBlock);
	}
}