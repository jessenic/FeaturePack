package net.digiex.featurepack.task;

import org.bukkit.World;

public class TimeLockTask implements Runnable {

    private World world;
    private long time;

    public TimeLockTask(World world, long time) {
        this.world = world;
        this.time = time;
    }

    @Override
    public void run() {
        this.world.setFullTime(this.time);
    }
}