package io.github.xarrow.rmt.spring.boot.starter.autoconfigure;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author: helix
 * @Time:2022/7/30
 * @Site: https://github.com/xarrow
 */
public class RmtImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{RmtConfiguration.class.getName(), RmtWebSocketConfigurer.class.getName(), RmtWebMvcConfigurer.class.getName()};
    }
}
