/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.api.event.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.impl.base.util.ActionResult;

/**
 * Callback for placing a block.
 *
 * <p>Upon return:
 * <ul><li>SUCCESS cancels further processing and places the block.
 * <li>PASS falls back to further processing.
 * <li>FAIL cancels further processing and does not place the block.</ul>
 */
public interface PlaceBlockCallback {
	Event<PlaceBlockCallback> EVENT = EventFactory.createArrayBacked(PlaceBlockCallback.class,
			(listeners) -> (player, world, pos, dir, hitX, hitY, hitZ) -> {
				for (PlaceBlockCallback event : listeners) {
					ActionResult result = event.blockPlaced(player, world, pos, dir, hitX, hitY, hitZ);

					if (result != ActionResult.PASS) {
						return result;
					}
				}

				return ActionResult.PASS;
			}
	);

	ActionResult blockPlaced(PlayerEntity player, World world, BlockPos pos, Direction dir, float hitX, float hitY, float hitZ);
}
