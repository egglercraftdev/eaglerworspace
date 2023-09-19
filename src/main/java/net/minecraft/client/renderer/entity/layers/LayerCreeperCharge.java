package net.minecraft.client.renderer.entity.layers;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.ShadersRenderPassFuture;
import net.lax1dude.eaglercraft.v1_8.vector.Matrix4f;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files are (c) 2022-2023 LAX1DUDE. All Rights Reserved.
 * 
 * WITH THE EXCEPTION OF PATCH FILES, MINIFIED JAVASCRIPT, AND ALL FILES
 * NORMALLY FOUND IN AN UNMODIFIED MINECRAFT RESOURCE PACK, YOU ARE NOT ALLOWED
 * TO SHARE, DISTRIBUTE, OR REPURPOSE ANY FILE USED BY OR PRODUCED BY THE
 * SOFTWARE IN THIS REPOSITORY WITHOUT PRIOR PERMISSION FROM THE PROJECT AUTHOR.
 * 
 * NOT FOR COMMERCIAL OR MALICIOUS USE
 * 
 * (please read the 'LICENSE' file this repo's root directory for more info) 
 * 
 */
public class LayerCreeperCharge implements LayerRenderer<EntityCreeper> {
	private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation(
			"textures/entity/creeper/creeper_armor.png");
	private final RenderCreeper creeperRenderer;
	private final ModelCreeper creeperModel = new ModelCreeper(2.0F);

	public LayerCreeperCharge(RenderCreeper creeperRendererIn) {
		this.creeperRenderer = creeperRendererIn;
	}

	public void doRenderLayer(EntityCreeper entitycreeper, float f, float f1, float f2, float f3, float f4, float f5,
			float f6) {
		if (entitycreeper.getPowered()) {
			if (DeferredStateManager.isInDeferredPass()) {
				if (DeferredStateManager.forwardCallbackHandler != null
						&& !DeferredStateManager.isEnableShadowRender()) {
					final Matrix4f mat = new Matrix4f(GlStateManager.getModelViewReference());
					DeferredStateManager.forwardCallbackHandler.push(new ShadersRenderPassFuture(entitycreeper) {
						@Override
						public void draw(PassType pass) {
							if (pass == PassType.MAIN) {
								DeferredStateManager.reportForwardRenderObjectPosition2(x, y, z);
							}
							boolean flag = entitycreeper.isInvisible();
							DeferredStateManager.setDefaultMaterialConstants();
							DeferredStateManager.setRoughnessConstant(0.3f);
							DeferredStateManager.setMetalnessConstant(0.1f);
							DeferredStateManager.setEmissionConstant(0.9f);
							EntityRenderer.disableLightmapStatic();
							GlStateManager.tryBlendFuncSeparate(GL_ONE, GL_ONE, GL_ZERO, GL_ZERO);
							GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
							if (flag) {
								GlStateManager.depthMask(false);
							}
							GlStateManager.pushMatrix();
							GlStateManager.loadMatrix(mat);
							GlStateManager.disableCull();
							GlStateManager.matrixMode(GL_TEXTURE);
							GlStateManager.pushMatrix();
							GlStateManager.loadIdentity();
							float f7 = (float) entitycreeper.ticksExisted + f2;
							GlStateManager.translate(f7 * 0.01F, f7 * 0.01F, 0.0F);
							GlStateManager.matrixMode(GL_MODELVIEW);
							LayerCreeperCharge.this.creeperRenderer.bindTexture(LIGHTNING_TEXTURE);
							LayerCreeperCharge.this.creeperModel
									.setModelAttributes(LayerCreeperCharge.this.creeperRenderer.getMainModel());
							LayerCreeperCharge.this.creeperModel.setLivingAnimations(entitycreeper, f, f1, f1);
							LayerCreeperCharge.this.creeperModel.render(entitycreeper, f, f1, f3, f4, f5, f6);
							GlStateManager.matrixMode(GL_TEXTURE);
							GlStateManager.popMatrix();
							GlStateManager.matrixMode(GL_MODELVIEW);
							GlStateManager.popMatrix();
							if (flag) {
								GlStateManager.depthMask(true);
							}
							GlStateManager.enableCull();
							DeferredStateManager.setDefaultMaterialConstants();
							DeferredStateManager.setHDRTranslucentPassBlendFunc();
							GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
						}
					});
				}
				return;
			}
			boolean flag = entitycreeper.isInvisible();
			if (flag) {
				GlStateManager.depthMask(false);
			}
			this.creeperRenderer.bindTexture(LIGHTNING_TEXTURE);
			GlStateManager.matrixMode(GL_TEXTURE);
			GlStateManager.loadIdentity();
			float f7 = (float) entitycreeper.ticksExisted + f2;
			GlStateManager.translate(f7 * 0.01F, f7 * 0.01F, 0.0F);
			GlStateManager.matrixMode(GL_MODELVIEW);
			GlStateManager.enableBlend();
			float f8 = 0.5F;
			GlStateManager.color(f8, f8, f8, 1.0F);
			GlStateManager.disableLighting();
			GlStateManager.blendFunc(GL_ONE, GL_ONE);
			this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
			this.creeperModel.render(entitycreeper, f, f1, f3, f4, f5, f6);
			GlStateManager.matrixMode(GL_TEXTURE);
			GlStateManager.loadIdentity();
			GlStateManager.matrixMode(GL_MODELVIEW);
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			if (flag) {
				GlStateManager.depthMask(true);
			}
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}