package com.example.library.hints;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aot.context.bootstrap.generator.infrastructure.nativex.BeanFactoryNativeConfigurationProcessor;
import org.springframework.aot.context.bootstrap.generator.infrastructure.nativex.NativeConfigurationRegistry;
import org.springframework.aot.context.bootstrap.generator.infrastructure.nativex.NativeResourcesEntry;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.nativex.hint.TypeAccess;

@Slf4j
public class ShapeHints implements BeanFactoryNativeConfigurationProcessor {

    @Override
    public void process(ConfigurableListableBeanFactory beanFactory, NativeConfigurationRegistry registry) {
        var shapes = ShapeCollector.collect(AutoConfigurationPackages.get(beanFactory));
        for (var c : shapes) {
            registry.reflection().forType(c).withAccess(TypeAccess.values()).build();
            registry.resources().add(NativeResourcesEntry.ofClassName( c.getName()));
            log.info ("registering " + c.getName());
        }

    }

}
