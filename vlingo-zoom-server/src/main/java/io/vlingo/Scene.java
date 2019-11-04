package io.vlingo;

import io.micronaut.context.annotation.Context;
import io.vlingo.actors.Configuration;
import io.vlingo.actors.World;
import io.vlingo.actors.plugin.mailbox.concurrentqueue.ConcurrentQueueMailboxPlugin;
import io.vlingo.config.ApplicationConfiguration;
import io.vlingo.config.ServerConfiguration;

@Context
public class Scene {

    private World world;
    private final ApplicationConfiguration applicationConfiguration;
    private final ServerConfiguration serverConfiguration;

    public Scene(ApplicationConfiguration applicationConfiguration, ServerConfiguration serverConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
        this.serverConfiguration = serverConfiguration;

        this.world = World.start(applicationConfiguration.getName(), Configuration.define()
                .with(ConcurrentQueueMailboxPlugin.ConcurrentQueueMailboxPluginConfiguration.define()
                        .defaultMailbox()
                        .numberOfDispatchersFactor(serverConfiguration.getDispatchersConfiguration()
                                .getFactor())
                        .numberOfDispatchers(serverConfiguration.getDispatchersConfiguration().getCount())
                        .dispatcherThrottlingCount(serverConfiguration.getDispatchersConfiguration()
                                .getThrottlingCount())));
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
}
