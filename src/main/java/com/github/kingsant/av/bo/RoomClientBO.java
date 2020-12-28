package com.company.project.av.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Cooyright
 *
 * @author juffett
 * @Date 2020/12/28 21:33
 **/
@Data
public class RoomClientBO implements Serializable {

    private Map<UUID, SocketClientDecorator> clients;

    public void addClient(UUID id, SocketClientDecorator socketClientBO) {
        if (clients == null) {
            clients = new HashMap<>();
        }
        clients.put(id, socketClientBO);
    }
}
