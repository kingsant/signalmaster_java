package com.github.kingsant.av.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * Cooyright
 *
 * @author juffett
 * @Date 2020/12/28 16:09
 **/
@Data
public class SocketClientBO implements Serializable {
    private String id;

    private String type;

    public SocketClientBO(String id, String type) {
        this.id = id;
        this.type = type;
    }
}
