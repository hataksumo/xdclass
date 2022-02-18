package indi.zhifa.learn.xdclass.webcommon.config.web;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/**
 * @author 芝法酱
 */
@Configuration
@AllArgsConstructor
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    private final FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Iterator<HttpMessageConverter<?>> it = converters.iterator();
        while (it.hasNext()) {
            HttpMessageConverter<?> converter = it.next();
            if (converter instanceof StringHttpMessageConverter) {
                StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter) converter;
                if(!stringHttpMessageConverter.getDefaultCharset().equals(StandardCharsets.UTF_8)){
                    it.remove();
                }
            }
            if (converter instanceof MappingJackson2HttpMessageConverter ||
                    converter instanceof FastJsonHttpMessageConverter) {
                it.remove();
            }
        }
        converters.add(fastJsonHttpMessageConverter);
    }
}
