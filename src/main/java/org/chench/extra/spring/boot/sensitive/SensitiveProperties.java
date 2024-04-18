package org.chench.extra.spring.boot.sensitive;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 脱敏配置
 * @author chench
 * @date 2024.04.12
 */
@ConfigurationProperties(prefix = "sensitive")
public class SensitiveProperties {
    /** 是否启用脱敏配置，默认true */
    private boolean mode = true;

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }
}