package org.microworld.mangopay.misc;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PredicatesTest {
    @Test
    public void constructorIsPrivate() throws IllegalAccessException, InstantiationException, NoSuchMethodException {
        final Constructor<Predicates> constructor = Predicates.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InvocationTargetException e) {
            assertEquals(IllegalStateException.class, e.getCause().getClass());
            assertEquals("Utility class", e.getCause().getMessage());
        }
    }

    @Test
    public void notReturnsNegatedPredicate() {
        assertFalse(Predicates.not((Predicate) o -> true).test(null));
        assertTrue(Predicates.not((Predicate) o -> false).test(null));
    }
}
