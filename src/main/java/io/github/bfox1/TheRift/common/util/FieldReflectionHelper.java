package io.github.bfox1.TheRift.common.util;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by bfox1 on 12/9/2016.
 */
public class FieldReflectionHelper
{
    public static Iterable<Field> getFieldsUpTo(@Nonnull Class<?> startClass, @Nonnull Class<?> exclusiveParent)
    {
        List<Field> currentField = Lists.newArrayList(startClass.getDeclaredFields());

        Class<?> parentClass = startClass.getSuperclass();

        if(parentClass != null && (exclusiveParent == null || !(parentClass.equals(exclusiveParent))))
        {

        }
        return null;
    }
}
