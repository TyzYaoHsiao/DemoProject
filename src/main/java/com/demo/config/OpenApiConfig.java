package com.demo.config;

import com.demo.constant.JwtConst;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${springdoc.info.title:Backend API}")
    private String title;

    @Value("${springdoc.info.description:Backend API}")
    private String description;

    @Value("${springdoc.info.version:0.0.1}")
    private String version;

    @Value("#{'${springdoc.servers}'.split(',')}")
    private transient List<String> servers;

    @Bean
    public OpenAPI springOpenAPI() {

        List<Server> serverList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(servers)) {
            for (String url : servers) {
                Server server = new Server();
                server.setUrl(url);
                serverList.add(server);
            }
        }

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(JwtConst.JWT_HEADER_NAME, new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(JwtConst.JWT_HEADER_NAME)))
                .addSecurityItem(new SecurityRequirement().addList(JwtConst.JWT_HEADER_NAME))
                .servers(serverList)
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version));
    }

}
