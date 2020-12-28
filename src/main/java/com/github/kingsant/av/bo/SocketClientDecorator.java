package com.company.project.av.bo;

import lombok.Data;

/**
 * Cooyright
 *
 * @author juffett
 * @Date 2020/12/28 19:01
 **/
@Data
public class SocketClientDecorator {

    private Boolean screen;
    private Boolean video;
    private Boolean audio;

    public SocketClientDecorator( Boolean screen, Boolean video, Boolean audio) {
        this.screen = screen;
        this.video = video;
        this.audio = audio;
    }

}
