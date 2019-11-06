package io.vlingo;

import io.micronaut.context.LifeCycle;
import io.micronaut.context.annotation.Context;
import io.vlingo.actors.World;
import io.vlingo.actors.plugin.mailbox.concurrentqueue.ConcurrentQueueMailboxPlugin;
import io.vlingo.config.ApplicationConfiguration;
import io.vlingo.config.ServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

@Context
public class VlingoScene implements LifeCycle<VlingoScene> {
    private static Logger log = LoggerFactory.getLogger(VlingoScene.class);
    private World world;
    private final ApplicationConfiguration applicationConfiguration;
    private final ServerConfiguration serverConfiguration;
    private boolean isRunning;

    public VlingoScene(ApplicationConfiguration applicationConfiguration, ServerConfiguration serverConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
        this.serverConfiguration = serverConfiguration;
    }

    public World getWorld() {
        return world;
    }

    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    public ServerConfiguration getServerConfiguration() {
        return serverConfiguration;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Nonnull
    @Override
    public VlingoScene start() {
        if (!isRunning) {
            if(world == null || world.isTerminated()) {
                this.world = World.start(this.applicationConfiguration.getName(), io.vlingo.actors.Configuration.define()
                        .with(ConcurrentQueueMailboxPlugin.ConcurrentQueueMailboxPluginConfiguration.define()
                                .defaultMailbox()
                                .numberOfDispatchersFactor(this.serverConfiguration.getDispatchersConfiguration()
                                        .getFactor())
                                .numberOfDispatchers(this.serverConfiguration.getDispatchersConfiguration()
                                        .getCount())
                                .dispatcherThrottlingCount(this.serverConfiguration.getDispatchersConfiguration()
                                        .getThrottlingCount())));
                log.info("New scene created: " + this.world.stage().name());
            }
            this.isRunning = true;
        } else {
            throw new RuntimeException("A Vlingo Zoom scene is already running in the current Micronaut context");
        }
        return this;
    }

    @Nonnull
    @Override
    public VlingoScene stop() {
        if (isRunning) {
            world.stage().stop();
            world.terminate();
            isRunning = false;
        } else {
            throw new RuntimeException("A Vlingo Zoom scene is not running in the current Micronaut context");
        }
        return this;
    }

    @Override
    public void close() {
        this.stop();
    }

    @Nonnull
    @Override
    public VlingoScene refresh() {
        this.stop();
        return this.start();
    }
}
