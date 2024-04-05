package cn.xc.starter.whitelist.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("xc.white-list")
public class WhiteListProperties {

    private List<String> users;

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

}
