package org.chench.extra.spring.boot.sensitive;

import com.yhq.sensitive.converter.SensitiveFastJsonMessageConverter;
import com.yhq.sensitive.converter.SensitiveGsonMessageConverter;
import com.yhq.sensitive.converter.SensitiveJacksonMessageConverter;
import com.yhq.sensitive.util.JsonChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 添加自定义HttpMessageConverter，实现HTTP接口返回数据自动脱敏
 * @author chench
 * @date 2024.04.12
 */
@EnableConfigurationProperties(SensitiveProperties.class)
@Configuration
public class SensitiveWebMvcAutoConfiguration {
    SensitiveProperties sensitiveProperties;

    public SensitiveWebMvcAutoConfiguration() {
        System.out.println("SensitiveWebMvcAutoConfiguration");
    }

    /**
     * 加载自定义MessageConverter
     * @return
     */
    @Bean
    public WebMvcConfigurer sensitiveWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                if (!sensitiveProperties.isMode()) {
                    return;
                }

                HttpMessageConverter converter = resolveMessageConverter();
                if (converter != null) {
                    // 将自定义的Converter放在第一个位置，确保可以生效
                    converters.add(0, resolveMessageConverter());
                }
            }
        };

//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//                super.extendMessageConverters(converters);
//                if (!sensitiveProperties.isMode()) {
//                    return;
//                }
//
//                HttpMessageConverter converter = resolveMessageConverter();
//                if (converter != null) {
//                    // 将自定义的Converter放在第一个位置，确保可以生效
//                    converters.add(0, resolveMessageConverter());
//                }
//            }
//        };
    }

    @Autowired
    public void setSensitiveProperties(SensitiveProperties sensitiveProperties) {
        this.sensitiveProperties = sensitiveProperties;
    }

    /**
     * 根据引入的JSON组件使用对应的Converter
     * @return
     */
    private HttpMessageConverter resolveMessageConverter() {
        if (JsonChecker.isJacksonPresent()) {
            return new SensitiveJacksonMessageConverter();
        } else if (JsonChecker.isFastjsonPresent()) {
            return new SensitiveFastJsonMessageConverter();
        } else if (JsonChecker.isGsonPresent()) {
            return new SensitiveGsonMessageConverter();
        }
        return null;
    }

}