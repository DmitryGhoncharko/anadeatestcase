package testcase.model.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.ProxyConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPConnectionPool implements ConnectionPool {

    private static final HikariConfig CONFIG = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        CONFIG.setJdbcUrl( "jdbc:postgresql://localhost:5432/postgres" );
        CONFIG.setUsername( "root" );
        CONFIG.setPassword( "root" );
        CONFIG.setMaximumPoolSize(6);
        CONFIG.setDriverClassName("org.postgresql.Driver");
        ds = new HikariDataSource(CONFIG);
    }

    @Override
    public ProxyConnection getConnection() throws SQLException {
        return (ProxyConnection) ds.getConnection();
    }
}
