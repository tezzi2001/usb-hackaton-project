package com.heroku.labshare.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.heroku.labshare.constant.EnvironmentVariableNames.DROPBOX_ACCESS_TOKEN;
import static com.heroku.labshare.constant.EnvironmentVariableNames.DROPBOX_CLIENT_ID;

@Configuration
@Profile("prod")
public class ProdDropboxConfig {

    @Bean
    DbxRequestConfig getDbxRequestConfig() {
        return DbxRequestConfig.newBuilder(DROPBOX_CLIENT_ID).build();
    }

    @Bean
    DbxClientV2 getDbxClientV2(DbxRequestConfig config) {
        return new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);
    }
}
