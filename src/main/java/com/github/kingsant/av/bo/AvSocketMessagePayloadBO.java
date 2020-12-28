package com.github.kingsant.av.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * Cooyright
 *
 * @author juffett
 * @Date 2020/12/28 13:13
 **/
@Data
public class AvSocketMessagePayloadBO implements Serializable {

    private String sdp;

    private String type;

    private AvSocketCandidateBO candidate;
}
