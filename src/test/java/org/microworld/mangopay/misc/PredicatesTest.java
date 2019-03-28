/**
 * Copyright © 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        assertFalse(Predicates.not((Predicate<Void>) o -> true).test(null));
        assertTrue(Predicates.not((Predicate<Void>) o -> false).test(null));
    }
}
