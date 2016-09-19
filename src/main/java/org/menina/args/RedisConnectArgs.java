package org.menina.args;

import lombok.Data;

/**
 * Created by meninaChimp on 2016/9/18 0018.
 */
@Data
public class RedisConnectArgs {
    private String host;
    private int port;
    private String password;
    private int maxActive;
    private int timeout;
    private int maxIdle;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private boolean testOnBorrow;
    private boolean testOnCreate;
    private String encode;
    private boolean unlock;
    private int db;
}
