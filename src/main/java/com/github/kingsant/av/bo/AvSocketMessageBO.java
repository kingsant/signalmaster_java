package com.github.kingsant.av.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Cooyright
 *
 * @author juffett
 * @Date 2020/12/28 13:11
 **/
@Data
public class AvSocketMessageBO implements Serializable {
    private UUID from;

    private UUID to;
    private String prefix;
    private String roomType;
    private String sid;
    private String type;
    private AvSocketMessagePayloadBO payload;
}
