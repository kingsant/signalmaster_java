package com.company.project.av.config;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Cooyright
 *
 * @author juffett
 * @Date 2020/12/28 12:29
 **/
public class SocketIOConfig {

    @Value("${socketio.host}")
    private String host = "0.0.0.0";

    @Value("${socketio.port}")
    private Integer port = 11111;

    @Value("${socketio.bossCount}")
    private int bossCount = 1;

    @Value("${socketio.workCount}")
    private int workCount = 20;

    @Value("${socketio.allowCustomRequests}")
    private boolean allowCustomRequests = true;

    @Value("${socketio.upgradeTimeout}")
    private int upgradeTimeout = 1000000;

    @Value("${socketio.pingTimeout}")
    private int pingTimeout = 1000000;

    @Value("${socketio.pingInterval}")
    private int pingInterval = 25000;

    @Bean
    public SocketIOServer socketIOServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname(host);
        config.setPort(port);
        config.setBossThreads(bossCount);
        config.setWorkerThreads(workCount);
        config.setAllowCustomRequests(allowCustomRequests);
        config.setUpgradeTimeout(upgradeTimeout);
        config.setPingTimeout(pingTimeout);
        config.setPingInterval(pingInterval);
        return new SocketIOServer(config);
    }
}
