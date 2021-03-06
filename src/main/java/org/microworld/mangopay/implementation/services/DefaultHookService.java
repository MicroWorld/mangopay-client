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
package org.microworld.mangopay.implementation.services;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.Hook;
import org.microworld.mangopay.misc.HttpMethod;
import org.microworld.mangopay.search.Page;
import org.microworld.mangopay.search.Sort;
import org.microworld.mangopay.services.HookService;

import java.util.List;

public class DefaultHookService implements HookService {
    private final MangopayConnection connection;

    public DefaultHookService(final MangopayConnection connection) {
        this.connection = connection;
    }

    @Override
    public Hook create(final Hook hook) {
        return connection.queryForObject(Hook.class, HttpMethod.POST, "/hooks", hook);
    }

    @Override
    public Hook get(final String id) {
        return connection.queryForObject(Hook.class, HttpMethod.GET, "/hooks/{0}", null, id);
    }

    @Override
    public Hook update(final Hook hook) {
        return connection.queryForObject(Hook.class, HttpMethod.PUT, "/hooks/{0}", hook, hook.getId());
    }

    @Override
    public List<Hook> list() {
        return connection.queryForList(Hook.class, HttpMethod.GET, "/hooks", null, Sort.byDefault(), Page.of(1, Page.MAX_PAGE_SIZE));
    }
}
