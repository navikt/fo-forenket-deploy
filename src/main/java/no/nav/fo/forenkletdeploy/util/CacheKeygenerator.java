package no.nav.fo.forenkletdeploy.util;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Collection;

public class CacheKeygenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        String key = "";
        for(Object param : params) {
            if (param instanceof Collection) {
                for(Object e : (Collection)param) {
                    key += e.hashCode();
                }
            } else if(param != null) {
                key += param.hashCode();
            }
        }

        return key.hashCode();
    }
}
