package com.company.project.av.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * Cooyright
 *
 * @author juffett
 * @Date 2020/12/29 0:44
 **/
@Data
public class AvSocketCandidateBO implements Serializable {
    private String candidate;

    private Integer sdpMLineIndex;

    private String sdpMid;
}
