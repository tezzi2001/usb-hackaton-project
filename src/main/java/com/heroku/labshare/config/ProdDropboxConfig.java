package com.heroku.labshare.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.heroku.labshare.constant.EnvironmentVariableNames.*;

@Configuration
@Profile("prod")
public class ProdDropboxConfig {

    @Bean
    DbxRequestConfig getDbxRequestConfig() {
        String dropboxClientId = System.getenv(DROPBOX_CLIENT_ID);
        return DbxRequestConfig.newBuilder(dropboxClientId).build();
    }

    @Bean
    DbxClientV2 getDbxClientV2(DbxRequestConfig config) {
        String dropboxAccessToken = System.getenv(DROPBOX_ACCESS_TOKEN);
        return new DbxClientV2(config, dropboxAccessToken);
    }
}
