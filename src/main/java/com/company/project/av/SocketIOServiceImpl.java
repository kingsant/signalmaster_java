package com.company.project.av;

import com.company.project.av.bo.AvSocketMessageBO;
import com.company.project.av.bo.RoomClientBO;
import com.company.project.av.bo.SocketClientBO;
import com.company.project.av.bo.SocketClientDecorator;
import com.company.project.av.config.SocketIOConfig;
import com.company.project.av.config.StunServersConfig;
import com.company.project.av.config.TurnServersConfig;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cooyright
 *
 * @author juffett
 * @Date 2020/12/28 12:31
 **/
@Slf4j
@Service(value = "socketIOService")
public class SocketIOServiceImpl  implements ISocketIOService  {
    /**
     * 存放已连接的客户端
     */
    private static Map<String, SocketClientDecorator> clientMap = new ConcurrentHashMap<>();

    /**
     * 自定义事件`push_data_event`,用于服务端与客户端通信
     */
    private static final String PUSH_DATA_EVENT = "push_data_event";

    public SocketIOServer socketIOServer;

    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     */
    @PostConstruct
    private void autoStartup() {
        socketIOServer =  new SocketIOConfig().socketIOServer();
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     */
    @PreDestroy
    private void autoStop() {
        stop();
    }

    @Override
    public void start() {
        socketIOServer.addConnectListener(client -> {
            log.debug("************ 客户端： " + getIpByClient(client) + " 已连接 ************");
            client.sendEvent("connected", "你成功连接上了哦...");
            clientMap.put(client.getSessionId().toString(), new SocketClientDecorator(false, true, false));
            client.sendEvent("stunservers", StunServersConfig.getStunServer().getStunservers());
            client.sendEvent("turnservers", TurnServersConfig.getConfig().getTurnservers());

        });

        socketIOServer.addDisconnectListener(client -> {
            String clientIp = getIpByClient(client);
            log.debug(clientIp + " *********************** " + "客户端已断开连接");
            String userId = getParamsByClient(client);
            if (userId != null) {
                clientMap.remove(userId);
                client.disconnect();
            }
        });

        socketIOServer.addEventListener("create", String.class, (client, roomName, ackSender) -> {
            String clientIp = getIpByClient(client);
            log.info(clientIp + " ************ create：" + roomName);
            log.info("************ create ack：" + ackSender.isAckRequested());
            if (roomName == null) {
                return;
            }

            BroadcastOperations roomOperations = socketIOServer.getRoomOperations(roomName);
            if (CollectionUtils.isEmpty(roomOperations.getClients())) {
                join(client, roomName);
                ackSender.sendAckData(null, roomName);
            } else {
                ackSender.sendAckData("taken");
            }

        });

        socketIOServer.addEventListener("join", String.class, (client, data, ackSender) -> {
            String clientIp = getIpByClient(client);
            log.info(clientIp + " ************ join：" + data);
            log.info("************ join ack：" + ackSender.isAckRequested());
            ackSender.sendAckData(null, describeRoom(data));
            join(client, data);
        });

        socketIOServer.addEventListener("message", AvSocketMessageBO.class, (client, data, ackSender) -> {
            String clientIp = getIpByClient(client);
            log.debug(clientIp + " ************ message：" + data);

            if (data == null) {
                return;
            }
            SocketIOClient toClient = socketIOServer.getClient(data.getTo());
            if (toClient == null) {
                return;
            }
            data.setFrom(client.getSessionId());
            toClient.sendEvent("message", data);

        });

        socketIOServer.addEventListener("shareScreen", String.class, (client, data, ackSender) -> {
            String clientIp = getIpByClient(client);
            log.info(clientIp + " ************ shareScreen：" + data);

            SocketClientDecorator decorator = clientMap.get(client.getSessionId().toString());
            if (decorator != null) {
                decorator.setScreen(true);
            }
        });

        socketIOServer.addEventListener("unshareScreen", String.class, (client, data, ackSender) -> {
            String clientIp = getIpByClient(client);
            log.info(clientIp + " ************ unshareScreen：" + data);

            SocketClientDecorator decorator = clientMap.get(client.getSessionId().toString());
            if (decorator != null) {
                decorator.setScreen(false);
            }
        });


        socketIOServer.addEventListener("disconnect", String.class, (client, data, ackSender) -> {
            String clientIp = getIpByClient(client);
            log.info(clientIp + " ************ disconnect：" + data);

            removeFeed(client, null);
        });

        socketIOServer.addEventListener("leave", String.class, (client, data, ackSender) -> {
            String clientIp = getIpByClient(client);
            log.info(clientIp + " ************ leave：" + data);

            removeFeed(client, null);
        });
        socketIOServer.addEventListener("trace", String.class, (client, data, ackSender) -> {
            String clientIp = getIpByClient(client);
            log.info(clientIp + " ************ trace：" + data);
        });
        socketIOServer.start();
        log.info("signal start success");
    }

    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    private String getParamsByClient(SocketIOClient client) {
        // 获取客户端url参数（这里的userId是唯一标识）
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        List<String> userIdList = params.get("userId");
        if (!CollectionUtils.isEmpty(userIdList)) {
            return userIdList.get(0);
        }
        return null;
    }

    /**
     * 获取连接的客户端ip地址
     *
     * @param client: 客户端
     * @return: java.lang.String
     */
    private String getIpByClient(SocketIOClient client) {
        String sa = client.getRemoteAddress().toString();
        String clientIp = sa.substring(1, sa.indexOf(":"));
        return clientIp;
    }

    /**
     * join
     * @param client
     * @param roomName
     */
    private void join(SocketIOClient client, String roomName) {
        removeFeed(client, null);
        client.joinRoom(roomName);
    }

    private void removeFeed(SocketIOClient client, String type) {
        Set<String> allRooms = client.getAllRooms();
        if (CollectionUtils.isEmpty(allRooms)) {
            return;
        }
        for (String room : allRooms) {
            socketIOServer.getRoomOperations(room).sendEvent("remove", new SocketClientBO(client.getSessionId().toString(), type));
            if (type != null) {
                client.leaveRoom(room);
            }
        }
    }

    private int clientsInRoom(String roomName) {
        return socketIOServer.getRoomOperations(roomName).getClients().size();
    }

    public RoomClientBO describeRoom(String roomName) {
        BroadcastOperations roomOperations = socketIOServer.getRoomOperations(roomName);
        RoomClientBO roomClientBO = new RoomClientBO();
        for (SocketIOClient client : roomOperations.getClients()) {
            SocketClientDecorator clientDecorator = clientMap.get(client.getSessionId().toString());
            roomClientBO.addClient(client.getSessionId(), clientDecorator);
        }
        return roomClientBO;
    }
}
