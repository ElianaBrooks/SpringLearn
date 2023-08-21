package xyz.songlin.springlearn.bean;

import org.springframework.beans.factory.FactoryBean;

import java.time.ZoneId;

// @Component
public class ZoneIdFactoryBean implements FactoryBean<ZoneId> {
    String zone = "Asia/Tokyo";

    @Override
    public ZoneId getObject() throws Exception {
        return ZoneId.of(zone);
    }

    @Override
    public Class<?> getObjectType() {
        return ZoneId.class;
    }
}
