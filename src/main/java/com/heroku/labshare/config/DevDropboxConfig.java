package com.heroku.labshare.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevDropboxConfig {

    @Value("${dropbox.client-id}")
    private String clientId;

    @Value("${dropbox.access-token}")
    private String accessToken;

    @Bean
    DbxRequestConfig getDbxRequestConfig() {
        return DbxRequestConfig.newBuilder(clientId).build();
    }

    @Bean
    DbxClientV2 getDbxClientV2(DbxRequestConfig config) {
        return new DbxClientV2(config, accessToken);
    }
}
