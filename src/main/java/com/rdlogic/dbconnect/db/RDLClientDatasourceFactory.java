package com.rdlogic.dbconnect.db;

import com.rdlogic.dbconnect.config.ClientEnvironmentConfig;
import com.rdlogic.dbconnect.config.ConnectionConfig;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.SQLException;

@Named
public class RDLClientDatasourceFactory {

    @Inject
    private ConnectionConfig connectionConfig;
    @Inject
    private ClientEnvironmentConfig environmentConfig;
    @Value("${rdl.db.url.prefix}")
    private String urlPrefix;
    @Value("${rdl.client}")
    private String clientId;

    public ConnectionConfig getConnectionConfig() {
        return connectionConfig;
    }

    public void setConnectionConfig(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    public ClientEnvironmentConfig getEnvironmentConfig() {
        return environmentConfig;
    }

    public void setEnvironmentConfig(ClientEnvironmentConfig environmentConfig) {
        this.environmentConfig = environmentConfig;
    }



    public DataSource buildClientDatasource(String client) throws SQLException {
        ConnectionConfig.DB db = connectionConfig.getDB(environmentConfig.getClientEnv());
        ConnectionConfig.DBInstance instance = db.getInstances().values().stream().filter(i->i.getClients().contains(environmentConfig.getClientCode())).findFirst().get();
        String clientUrl = String.format("%s%s:%d/%s", urlPrefix, db.getHostname(), db.getPort(), instance.getSid());
//        DataSourceBuilder dsFactory = DataSourceBuilder.create().driverClassName("org.oracle.jdbc.pool.DataSource")
        OracleDataSource ods = new OracleDataSource();
        ods.setURL(clientUrl);
        ods.setUser(client);
        ods.setPassword(client);
        return ods;
    }
}
