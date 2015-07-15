package org.microworld.mangopay;

import java.util.List;

import org.microworld.mangopay.entities.Hook;
import org.microworld.mangopay.implementation.DefaultHookApi;

public interface HookApi {
  static HookApi createDefault(final MangopayConnection connection) {
    return new DefaultHookApi(connection);
  }

  Hook create(final Hook hook);

  Hook get(String id);

  Hook update(Hook hook);

  List<Hook> list();
}
