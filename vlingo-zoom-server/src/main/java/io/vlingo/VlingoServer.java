package io.vlingo;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.LifeCycle;
import io.micronaut.context.annotation.Context;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.runtime.server.EmbeddedServer;
import io.vlingo.actors.Stage;
import io.vlingo.config.ServerConfiguration;
import io.vlingo.http.resource.Configuration;
import io.vlingo.http.resource.Resource;
import io.vlingo.http.resource.Resources;
import io.vlingo.http.resource.Server;
import io.vlingo.resource.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.stream.Stream;

/**
 * The {@link VlingoServer} is a bootstrapper for loading and auto-configuring a vlingo/http server.
 */
@Context
public class VlingoServer implements EmbeddedServer {
    private static Logger log = LoggerFactory.getLogger(VlingoServer.class);
    private Server server;
    private Stage stage;
    private Scene scene;
    private Resource[] resources;
    private ApplicationContext applicationContext;
    private ApplicationConfiguration applicationConfiguration;
    private boolean isRunning = false;

    public VlingoServer(ApplicationContext applicationContext, ApplicationConfiguration applicationConfiguration,
                        Scene scene, Stream<Endpoint> endpoints) {
        // Load the world context with auto-configured settings
        this.applicationContext = applicationContext;
        this.applicationConfiguration = applicationConfiguration;
        this.stage = scene.getWorld().stage();
        this.scene = scene;
        this.resources = endpoints.map(Endpoint::getResource).toArray(Resource[]::new);
    }

    public Server getServer() {
        return server;
    }

    public Stage getStage() {
        return stage;
    }

    public Resource[] getResources() {
        return resources;
    }

    @Override
    public int getPort() {
        return Math.toIntExact(scene.getServerConfiguration().getPort());
    }

    @Override
    public String getHost() {
        return "localhost";
    }

    @Override
    public String getScheme() {
        return "http";
    }

    @Override
    public URL getURL() {
        try {
            return getURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public URI getURI() {
        return URI.create(getScheme() + "://" + getHost() + ":" + getPort());
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public @Nonnull
    VlingoServer start() {
        if (!isRunning) {
            // Start the server with auto-configured settings
            this.server = Server.startWith(stage, Resources.are(resources),
                    scene.getServerConfiguration().getPort().intValue(),
                    new Configuration.Sizing(scene.getServerConfiguration()
                            .getProcessorsConfiguration().getPoolSize(),
                            scene.getServerConfiguration().getDispatchersConfiguration().getPoolSize(),
                            scene.getServerConfiguration().getMaxBufferPoolSize(),
                            scene.getServerConfiguration().getMaxMessageSize()),
                    new Configuration.Timing(scene.getServerConfiguration()
                            .getActorsConfiguration().getProbeInterval(),
                            scene.getServerConfiguration().getActorsConfiguration().getRequestMissingTimeout()));
            isRunning = true;
            log.info(ServerConfiguration.getBanner());
            log.info("Started embedded Vlingo Zoom server at " + getURI().toASCIIString());
        } else {
            throw new RuntimeException("A Vlingo Zoom server is already running in the current Micronaut context");
        }
        return this;
    }

    @Override
    public @Nonnull
    VlingoServer stop() {
        if (isRunning) {
            server.stop();
            isRunning = false;
            log.info("Stopped embedded Vlingo Zoom server at " + getURI().toASCIIString());
        } else {
            throw new RuntimeException("A Vlingo Zoom server is not running in the current Micronaut context");
        }
        return this;
    }

    @Override
    public void close() {
        if (!this.getStage().isStopped())
            this.getStage().stop();
        this.getStage().world().terminate();
    }


    @Override
    public @Nonnull
    LifeCycle refresh() {
        this.stop();
        return this.start();
    }
}
