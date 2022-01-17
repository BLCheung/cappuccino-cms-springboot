package com.blcheung.zblmissyouadmin.module.file.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author BLCheung
 * @date 2022/1/18 5:26 上午
 */
public class YmlPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(resource.getResource()
                                                                                      .getFilename(),
                                                                              resource.getResource());
        return sources.get(0);
    }
}
