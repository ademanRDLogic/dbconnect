package com.rdlogic.dbconnect.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@PropertySource("classpath:connections.properties")
@ConfigurationProperties("rdl.clients")
public class ConnectionConfig {

    private DB dev;

    private DB trn;

    private DB prd;

    public DB getDev() {
        return dev;
    }

    public void setDev(DB dev) {
        this.dev = dev;
    }

    public DB getTrn() {
        return trn;
    }

    public void setTrn(DB trn) {
        this.trn = trn;
    }

    public DB getPrd() {
        return prd;
    }

    public void setPrd(DB prd) {
        this.prd = prd;
    }

    public DB getDB(String env) {
        switch (env) {
            case "prd": return prd;
            case "trn": return trn;
            case "dev":
                default: return dev;
        }
    }

    public static class DB {
        private String hostname, env;
        private int port;
        private Map<String, DBInstance> instances;

        public String getEnv() {
            return env;
        }

        public void setEnv(String env) {
            this.env = env;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public Map<String, DBInstance> getInstances() {
            return instances;
        }

        public void setInstances(Map<String, DBInstance> instances) {
            this.instances = instances;
        }
    }

    public static class DBInstance {
         private String sid;
         private Set<String> clients;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public Set<String> getClients() {
            return clients;
        }

        public void setClients(Set<String> clients) {
            this.clients = clients;
        }
    }
}
