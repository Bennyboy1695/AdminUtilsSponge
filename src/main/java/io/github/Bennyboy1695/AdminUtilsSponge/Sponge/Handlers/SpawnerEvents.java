package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Handlers;

import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.InteractBlockEvent;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Handlers was created by Bennyboy1695 on 07/11/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class SpawnerEvents {

    String prefix = AdminUtilsSponge.getInstance().getPrefix();

    @Listener(order = Order.FIRST)
    public void onSpawnerRightClick(InteractBlockEvent event) {
        event.getTargetBlock();
    }
}
