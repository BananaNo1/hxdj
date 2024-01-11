package com.leis.hxdscst.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "hxds-cst",
                description = "客户子系统",
                version = "1.0"
        )
)
@Configuration
public class SpringDocConfig {
}
