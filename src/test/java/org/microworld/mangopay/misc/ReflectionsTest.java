package org.microworld.mangopay.misc;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReflectionsTest {
    @Test
    public void constructorIsPrivate() throws NoSuchMethodException, SecurityException, IllegalAccessException, InstantiationException {
        final Constructor<Reflections> constructor = Reflections.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InvocationTargetException e) {
            assertEquals(IllegalStateException.class, e.getCause().getClass());
            assertEquals("Utility class", e.getCause().getMessage());
        }
    }
}
