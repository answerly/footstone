package com.olasharing.footstone.sdk.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

/**
 * DynamicDataSource
 *
 * @author liuyan
 * @date 2019-02-21
 */
class DynamicDataSource implements DataSource, Closeable {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DynamicDataSource.class);

    private static final Class<? extends DataSource> DATA_SOURCE_TYPE = HikariDataSource.class;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private volatile DataSource innerDataSource;

    private volatile DataSourceConfig dataSourceConfig;

    private DynamicDataSource(DataSource innerDataSource, DataSourceConfig dataSourceConfig) {
        this.innerDataSource = innerDataSource;
        this.dataSourceConfig = dataSourceConfig;
    }

    static DynamicDataSource build(DataSourceConfig dataSourceConfig) {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.type(DATA_SOURCE_TYPE);
        builder.driverClassName(dataSourceConfig.getDriverClassName());
        builder.url(dataSourceConfig.getUrl());
        builder.username(dataSourceConfig.getUsername());
        builder.password(dataSourceConfig.getPassword());

        return new DynamicDataSource(builder.build(), dataSourceConfig);
    }


    void updateDataSourceConfig(DataSourceConfig dataSourceConfig) {
        if (this.dataSourceConfig.equals(dataSourceConfig)) {
            LOGGER.info("updateDataSourceConfig fail, due to not change");
            return;
        }
        try {
            this.lock.writeLock().lock();

            DataSourceBuilder builder = DataSourceBuilder.create();
            builder.type(DATA_SOURCE_TYPE);
            builder.driverClassName(dataSourceConfig.getDriverClassName());
            builder.url(dataSourceConfig.getUrl());
            builder.username(dataSourceConfig.getUsername());
            builder.password(dataSourceConfig.getPassword());

            if (this.innerDataSource instanceof Closeable) {
                try {
                    ((Closeable) this.innerDataSource).close();
                } catch (IOException e) {
                    LOGGER.error("updateDataSourceConfig close previous warn:" + dataSourceConfig.getUrl(), e);
                }
            }

            this.innerDataSource = builder.build();
            this.dataSourceConfig = dataSourceConfig;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public String getDataSourceId() {
        try {
            lock.readLock().lock();
            return this.dataSourceConfig.getDataSourceId();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            lock.readLock().lock();
            return this.innerDataSource.getConnection();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        try {
            lock.readLock().lock();
            return this.innerDataSource.getConnection(username, password);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        try {
            lock.readLock().lock();
            return this.innerDataSource.unwrap(iface);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        try {
            lock.readLock().lock();
            return this.innerDataSource.isWrapperFor(iface);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        try {
            lock.readLock().lock();
            return this.innerDataSource.getLogWriter();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        try {
            lock.readLock().lock();
            this.innerDataSource.setLogWriter(out);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        try {
            lock.readLock().lock();
            return this.innerDataSource.getLoginTimeout();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        try {
            lock.readLock().lock();
            this.innerDataSource.setLoginTimeout(seconds);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        try {
            lock.readLock().lock();
            return this.innerDataSource.getParentLogger();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void close() throws IOException {
        if (innerDataSource instanceof Closeable) {
            try {
                lock.writeLock().lock();
                ((Closeable) innerDataSource).close();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
}
