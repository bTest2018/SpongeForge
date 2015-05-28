/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.mod.mixin.core.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;

@Mixin(value = SaveHandler.class, priority = 1001)
public abstract class MixinSaveHandler {
    @Shadow private File worldDirectory;

    @Redirect(method = "saveWorldInfo", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/FMLCommonHandler;handleWorldDataSave"
            + "(Lnet/minecraft/world/storage/SaveHandler;Lnet/minecraft/world/storage/WorldInfo;Lnet/minecraft/nbt/NBTTagCompound;)V", remap = false))
    public void redirectHandleWorldDataSave(FMLCommonHandler fml, SaveHandler handler, WorldInfo worldInformation, NBTTagCompound compound) {
        if (fml.getSavesDirectory().equals(this.worldDirectory.getParentFile())) {
            fml.handleWorldDataSave(handler, worldInformation, compound);
        }
    }

    @Redirect(method = "saveWorldInfoWithPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/FMLCommonHandler;handleWorldDataSave"
            + "(Lnet/minecraft/world/storage/SaveHandler;Lnet/minecraft/world/storage/WorldInfo;Lnet/minecraft/nbt/NBTTagCompound;)V", remap = false))
    public void redirectHandleWorldDataSaveWithPlayer(FMLCommonHandler fml, SaveHandler handler, WorldInfo worldInformation, NBTTagCompound
            compound) {
        if (fml.getSavesDirectory().equals(this.worldDirectory.getParentFile())) {
            fml.handleWorldDataSave(handler, worldInformation, compound);
        }
    }

    private void loadDimensionAndOtherData(SaveHandler handler, WorldInfo info, NBTTagCompound compound) {
        //NOOP cause Forge does this in the SaveHandler
    }

    private void saveDimensionAndOtherData(SaveHandler handler, WorldInfo info, NBTTagCompound compound) {
        //NOOP cause Forge does this in the SaveHandler
    }
}
