package indi.zhifa.learn.xdclass.common.util;

import indi.zhifa.learn.xdclass.common.entity.BasePo;
import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DtoEntityUtil Created by IntelliJ IDEA.
 *
 * @Author: 芝法酱
 * @Create 2021/3/16 10:34
 */
public class DtoEntityUtil {
    static DozerBeanMapper mapper;

    public static void init(){
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
    }

    public static <D, E> E dtoToPo(D t, Class<E> clazz) {
        if (t == null) {
            return null;
        }
        E result = mapper.map(t, clazz);
        if(result instanceof BasePo){
            ((BasePo) result).createInit();
        }
        return result;
    }

    public static <D, E> E dtoToPo(D pDto, E pPo, Class<E> pPoClass) {
        if (pDto == null) {
            return null;
        }
        E result = mapper.map(pPo, pPoClass);
        copy(result,pDto);
        if(result instanceof BasePo){
            ((BasePo) result).updateInit();
        }
        return result;
    }

    public static <D, E> E trans(D t, Class<E> clazz) {
        if (t == null) {
            return null;
        }
        return mapper.map(t, clazz);
    }

    public static <D, E> List<E> trans(D[] ts, Class<E> clazz) {
        List<E> es = new ArrayList<E>();
        if (ts == null) {
            return es;
        }

        for (D d : ts) {
            E e = (E) trans(d, clazz);
            if (e != null) {
                es.add(e);
            }
        }

        return es;
    }


    public static <D, E> List<E> trans(List<D> ts, Class<E> clazz) {
        List<E> es = new ArrayList<E>();
        if (ts == null) {
            return es;
        }
        for (D d : ts) {
            E e = (E) trans(d, clazz);
            if (e != null) {
                es.add(e);
            }
        }
        return es;
    }

    public static <T> void copy(T dst, T src){
        mapper.map(src, dst);
    }
}
