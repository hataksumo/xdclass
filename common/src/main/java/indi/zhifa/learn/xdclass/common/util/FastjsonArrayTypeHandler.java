package indi.zhifa.learn.xdclass.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.TypeReference;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Slf4j
public class FastjsonArrayTypeHandler extends AbstractJsonTypeHandler<Object> {
    private final Class<?> type;

    public FastjsonArrayTypeHandler(Class<?> pType) {
        if (log.isTraceEnabled()) {
            log.trace("FastjsonTypeHandler(" + pType + ")");
        }
        Assert.notNull(pType, "Type argument cannot be null");
        type = pType;
    }

    @Override
    protected Object parse(String json) {
        return JSON.parseArray(json,type);
    }

    @Override
    protected String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
    }

}
