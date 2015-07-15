package org.microworld.mangopay.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.microworld.test.Matchers.around;

import java.time.Instant;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.microworld.mangopay.HookApi;
import org.microworld.mangopay.entities.EventType;
import org.microworld.mangopay.entities.Hook;
import org.microworld.mangopay.entities.HookStatus;
import org.microworld.mangopay.entities.HookValidity;
import org.microworld.mangopay.exceptions.MangopayException;

public class HookIT extends AbstractIntegrationTest {
  private HookApi hookApi;

  @Before
  public void setUpHookApi() {
    hookApi = HookApi.createDefault(connection);
  }

  @Test
  public void listAllHooks() {
    final List<Hook> hooks = hookApi.list();
    assertThat(hooks, is(not(empty())));
  }

  @Test
  public void getAndUpdateHook() {
    final String id = "1626293";
    final Hook hook = hookApi.get(id);
    assertThat(hook.getId(), is(equalTo(id)));

    hook.setUrl("https://example.com/hook");
    hook.setTag("New url");
    final Hook updatedHook = hookApi.update(hook);
    assertThat(updatedHook, is(hook(hook.getEventType(), "https://example.com/hook", hook.getStatus(), hook.getValidity(), "New url", hook.getCreationDate())));
    assertThat(updatedHook.getId(), is(equalTo(hook.getId())));
  }

  @Test
  public void getHookWithInvalidId() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("ressource_not_found: The ressource does not exist");
    thrown.expectMessage("RessourceNotFound: Cannot found the ressource Hook with the id=10");
    hookApi.get("10");
  }

  @Test
  public void createAlreadyRegisteredHook() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("EventType: A hook has already been registered for this EventType");
    hookApi.create(createHook(EventType.KYC_CREATED, "https://example.com/hook", HookStatus.ENABLED, null));
  }

  @Test
  public void createHookWithMissingMandatoryFields() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("Url: The Url field is required.");
    hookApi.create(createHook(EventType.KYC_FAILED, null, HookStatus.ENABLED, null));
  }

  @Test
  public void createHookWithFieldsContainingInvalidValues() {
    thrown.expect(MangopayException.class);
    thrown.expectMessage("param_error: One or several required parameters are missing or incorrect. An incorrect resource ID also raises this kind of error.");
    thrown.expectMessage("Url: The format of the URL is not correct");
    hookApi.create(createHook(EventType.KYC_SUCCEEDED, "foo@bar.com", HookStatus.ENABLED, "Too bad"));
  }

  private Hook createHook(final EventType eventType, final String url, final HookStatus status, final String tag) {
    final Hook hook = new Hook();
    hook.setEventType(eventType);
    hook.setUrl(url);
    hook.setStatus(status);
    hook.setTag(tag);
    return hook;
  }

  @SuppressWarnings("unchecked")
  private Matcher<Hook> hook(final EventType eventType, final String url, final HookStatus status, final HookValidity validity, final String tag, final Instant creationDate) {
    return allOf(
        hasProperty("id", is(notNullValue())),
        hasProperty("eventType", is(equalTo(eventType))),
        hasProperty("url", is(equalTo(url))),
        hasProperty("status", is(equalTo(status))),
        hasProperty("validity", is(equalTo(validity))),
        hasProperty("tag", is(equalTo(tag))),
        hasProperty("creationDate", is(around(creationDate))));
  }
}
