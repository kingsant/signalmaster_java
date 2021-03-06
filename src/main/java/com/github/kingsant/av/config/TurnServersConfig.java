package com.github.kingsant.av.config;

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
        //TODO
        return new TurnServersConfig(Lists.newArrayList(new InnerConfig(Lists.newArrayList("turn:xx:3478"), "xx", "123456")));
    }
}
