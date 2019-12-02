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
package org.microworld.mangopay.search;

import java.util.HashMap;
import java.util.Map;

public class Page implements ParameterHolder {
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;
    private final Map<String, String> parameters = new HashMap<>();

    private Page(final int pageNumber, final int pageSize) {
        parameters.put("Page", String.valueOf(pageNumber));
        parameters.put("Per_Page", String.valueOf(pageSize));
    }

    public static Page of(final int pageNumber) {
        return of(pageNumber, DEFAULT_PAGE_SIZE);
    }

    public static Page of(final int pageNumber, final int pageSize) {
        return new Page(pageNumber, pageSize);
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }
}
