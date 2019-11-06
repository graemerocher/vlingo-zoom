package io.vlingo.config;

import io.micronaut.context.annotation.BootstrapContextCompatible;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Primary;

/**
 * The {@link ServerConfiguration} class loads application properties from the application.yml file on the
 * classpath of the application.
 */
@ConfigurationProperties(ServerConfiguration.PREFIX)
@Primary
@BootstrapContextCompatible
@Context
public class ServerConfiguration {

    public static final String PREFIX = "server";

    private Long port;
    private Integer maxBufferPoolSize;
    private Integer maxMessageSize;

    private DispatchersConfiguration dispatchersConfiguration;
    private ProcessorsConfiguration processorsConfiguration;
    private ActorsConfiguration actorsConfiguration;

    public ServerConfiguration(DispatchersConfiguration dispatchersConfiguration,
                               ProcessorsConfiguration processorsConfiguration,
                               ActorsConfiguration actorsConfiguration) {
        this.dispatchersConfiguration = dispatchersConfiguration;
        this.processorsConfiguration = processorsConfiguration;
        this.actorsConfiguration = actorsConfiguration;
    }

    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    public Integer getMaxBufferPoolSize() {
        return maxBufferPoolSize;
    }

    public void setMaxBufferPoolSize(Integer maxBufferPoolSize) {
        this.maxBufferPoolSize = maxBufferPoolSize;
    }

    public Integer getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(Integer maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public DispatchersConfiguration getDispatchersConfiguration() {
        return dispatchersConfiguration;
    }

    public void setDispatchersConfiguration(DispatchersConfiguration dispatchersConfiguration) {
        if(dispatchersConfiguration != null) {
            this.dispatchersConfiguration = dispatchersConfiguration;
        }
    }

    public ProcessorsConfiguration getProcessorsConfiguration() {
        return processorsConfiguration;
    }

    public void setProcessorsConfiguration(ProcessorsConfiguration processorsConfiguration) {
        if (processorsConfiguration != null) {
            this.processorsConfiguration = processorsConfiguration;
        }
    }

    public ActorsConfiguration getActorsConfiguration() {
        return actorsConfiguration;
    }

    public void setActorsConfiguration(ActorsConfiguration actorsConfiguration) {
        if (actorsConfiguration != null) {
            this.actorsConfiguration = actorsConfiguration;
        }
    }

    @ConfigurationProperties(DispatchersConfiguration.PREFIX)
    @BootstrapContextCompatible
    @Context
    public static class DispatchersConfiguration {

        public static final String PREFIX = "dispatchers";

        private Float factor;
        private Integer count;
        private Integer throttlingCount;
        private Integer poolSize;

        public Float getFactor() {
            return factor;
        }

        public void setFactor(Float factor) {
            this.factor = factor;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getThrottlingCount() {
            return throttlingCount;
        }

        public void setThrottlingCount(Integer throttlingCount) {
            this.throttlingCount = throttlingCount;
        }

        public Integer getPoolSize() {
            return poolSize;
        }

        public void setPoolSize(Integer poolSize) {
            this.poolSize = poolSize;
        }
    }

    @ConfigurationProperties(ProcessorsConfiguration.PREFIX)
    @BootstrapContextCompatible
    @Context
    public static class ProcessorsConfiguration {

        public static final String PREFIX = "processors";

        private Integer poolSize;

        public Integer getPoolSize() {
            return poolSize;
        }

        public void setPoolSize(Integer poolSize) {
            this.poolSize = poolSize;
        }
    }

    @ConfigurationProperties(ActorsConfiguration.PREFIX)
    @BootstrapContextCompatible
    @Context
    public static class ActorsConfiguration {

        public static final String PREFIX = "actors";

        private Integer probeInterval;
        private Integer requestMissingTimeout;

        public Integer getProbeInterval() {
            return probeInterval;
        }

        public void setProbeInterval(Integer probeInterval) {
            this.probeInterval = probeInterval;
        }

        public Integer getRequestMissingTimeout() {
            return requestMissingTimeout;
        }

        public void setRequestMissingTimeout(Integer requestMissingTimeout) {
            this.requestMissingTimeout = requestMissingTimeout;
        }
    }

    private static final String banner = "\n" +
            "            ░▒░▒░      _ _                    \n" +
            "            ░▒░▒░     | (_)                   \n" +
            " ▒░▒░▒   ░▒░▒░  __   _| |_ _ __   __ _  ___   \n" +
            " ▒░▒░▒   ░▒░▒░  \\ \\ / / | | '_ \\ / _` |/ _ \\\n" +
            "     ▒░▒░▒       \\ V /| | | | | | (_| | (_) |\n" +
            "     ░▒░▒░        \\_/ |_|_|_| |_|\\__, |\\___/\n" +
            "                                  __/ | Zoom v0.1.0\n" +
            "                                 |___/";

    public static String getBanner() {
        return banner;
    }
}
