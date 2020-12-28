package com.castlemock.model.core.system;

public class MongoProperties {

    private static final int DEFAULT_MONGODB_PORT = 27017;
    
    private String host;
    private Integer port;
    private String uri;
    private String database;
    private boolean usesUri;

    public MongoProperties(String host, Integer port, String uri, String database, boolean usesUri) {
        this.uri = uri;
        this.host = getValue(host, "localhost");
        this.port = getValue(port, DEFAULT_MONGODB_PORT);
        this.database = database;
        this.usesUri = usesUri;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public boolean isUsesUri() {
        return usesUri;
    }

    public void setUsesUri(boolean usesUri) {
        this.usesUri = usesUri;
    }

    private <T> T getValue(T value, T fallback) {
        return (value != null ? value : fallback);
    }
}
