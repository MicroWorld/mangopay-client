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
package org.microworld.mangopay.tests;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.microworld.mangopay.entities.EventType;
import org.microworld.mangopay.entities.Hook;
import org.microworld.mangopay.entities.HookStatus;
import org.microworld.mangopay.entities.HookValidity;
import org.microworld.mangopay.exceptions.MangopayException;

import java.time.Instant;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.microworld.mangopay.entities.HookValidity.VALID;
import static org.microworld.test.Matchers.around;

public class HookIT extends AbstractIntegrationTest {
    @Test
    public void listAllHooks() {
        final List<Hook> hooks = client.getHookService().list();
        assertThat(hooks, is(not(empty())));
    }

    @Test
    public void getAndUpdateHook() {
        final String id = "1626293";
        final Hook hook = client.getHookService().get(id);
        assertThat(hook.getId(), is(equalTo(id)));

        hook.setUrl("https://example.com/hook");
        hook.setTag("New url");
        final Hook updatedHook = client.getHookService().update(hook);
        assertThat(updatedHook, is(hook(hook.getEventType(), "https://example.com/hook", hook.getStatus(), VALID, "New url", hook.getCreationDate())));
        assertThat(updatedHook.getId(), is(equalTo(hook.getId())));
    }

    @Test
    public void getHookWithInvalidId() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("ressource_not_found: The ressource does not exist");
        thrown.expectMessage("RessourceNotFound: Cannot found the ressource Hook with the id=10");
        client.getHookService().get("10");
    }

    @Test
    public void createAlreadyRegisteredHook() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
        thrown.expectMessage("EventType: A hook has already been registered for this EventType");
        client.getHookService().create(createHook(EventType.KYC_CREATED, "https://example.com/hook", HookStatus.ENABLED, null));
    }

    @Test
    public void createHookWithMissingMandatoryFields() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
        thrown.expectMessage("Url: The Url field is required.");
        client.getHookService().create(createHook(EventType.KYC_FAILED, null, HookStatus.ENABLED, null));
    }

    @Test
    public void createHookWithFieldsContainingInvalidValues() {
        thrown.expect(MangopayException.class);
        thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
        thrown.expectMessage("Url: The format of the URL is not correct");
        client.getHookService().create(createHook(EventType.KYC_SUCCEEDED, "foo@bar.com", HookStatus.ENABLED, "Too bad"));
    }

    private Hook createHook(final EventType eventType, final String url, final HookStatus status, final String tag) {
        final Hook hook = new Hook();
        hook.setEventType(eventType);
        hook.setUrl(url);
        hook.setStatus(status);
        hook.setTag(tag);
        return hook;
    }

    private Matcher<Hook> hook(final EventType eventType, final String url, final HookStatus status, final HookValidity validity, final String tag, final Instant creationDate) {
        return allOf(asList(
                hasProperty("id", is(notNullValue())),
                hasProperty("eventType", is(equalTo(eventType))),
                hasProperty("url", is(equalTo(url))),
                hasProperty("status", is(equalTo(status))),
                hasProperty("validity", is(equalTo(validity))),
                hasProperty("tag", is(equalTo(tag))),
                hasProperty("creationDate", is(around(creationDate)))));
    }
}
