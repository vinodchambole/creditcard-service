package com.bank.creditcard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

//@Component
public class DataLoader implements CommandLineRunner, ResourceLoaderAware {

    private final DataSource dataSource;
    private ResourceLoader resourceLoader;

    @Autowired
    public DataLoader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(resourceLoader.getResource("classpath:data.sql"));

        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
