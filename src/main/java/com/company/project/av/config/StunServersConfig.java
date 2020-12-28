package com.company.project.av.config;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Cooyright
 *
 * @author juffett
 * @Date 2020/12/28 21:48
 **/
@Data
public class StunServersConfig {
    private List<InnerUrl> stunservers;

    public StunServersConfig( ) {
    }
    public StunServersConfig(List<InnerUrl> stunservers) {
        this.stunservers = stunservers;
    }

    public static StunServersConfig getStunServer() {
        //TODO 填入自己的stun
        return new StunServersConfig(Lists.newArrayList(new InnerUrl("stun:XX:3478")));
    }

    @Data
    public static class InnerUrl {
        private String urls;

        public InnerUrl(String urls) {
            this.urls = urls;
        }
    }
}
