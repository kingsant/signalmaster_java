package com.company.project.av.config;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Cooyright
 *
 * @author juffett
 * @Date 2020/12/28 22:13
 **/
@Data
public class TurnServersConfig {

    private List<InnerConfig> turnservers;

    @Data
    public static class InnerConfig {
        private List<String> url;
        private String username;
        private String credential;

        public InnerConfig(List<String> url, String username, String credential) {
            this.url = url;
            this.username = username;
            this.credential = credential;
        }
    }

    public TurnServersConfig(List<InnerConfig> turnservers) {
        this.turnservers = turnservers;
    }

    public static TurnServersConfig getConfig() {
        //TODO 填入自己的turn
        return new TurnServersConfig(Lists.newArrayList(new InnerConfig(Lists.newArrayList("turn:XX:3478"), "XX", "123456")));
    }
}
