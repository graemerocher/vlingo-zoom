// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.zoom.actors.plugin.mailbox.blocking;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

import io.vlingo.actors.Actor;
import io.vlingo.actors.Mailbox;
import io.vlingo.actors.Message;

public class BlockingMailbox implements Mailbox {
  public static final String Name = "blockingMailbox";

  private boolean closed;
  private final Queue<Message> queue;
  private AtomicReference<Stack<List<Class<?>>>> suspendedOverrides;

  public BlockingMailbox() {
    this.queue = new ConcurrentLinkedQueue<>();
    this.suspendedOverrides = new AtomicReference<>(new Stack<>());
  }

  @Override
  public void run() {
    throw new UnsupportedOperationException("BlockingMailbox does not support this operation.");
  }

  @Override
  public void close() {
    closed = true;
  }

  @Override
  public boolean isClosed() {
    return closed;
  }

  @Override
  public boolean isDelivering() {
    throw new UnsupportedOperationException("BlockingMailbox does not support this operation.");
  }

  @Override
  public int concurrencyCapacity() {
    return 1;
  }

  @Override
  public void resume(final String name) {
    if (!suspendedOverrides.get().empty()) {
      suspendedOverrides.get().pop();
    }
    resumeAll();
  }

  @Override
  public void send(final Message message) {
    try {
      if (isSuspended()) {
        queue.add(message);
        return;
      } else {
        resumeAll();
      }

      message.actor().viewTestStateInitialization(null);
      message.deliver();
    } catch (Throwable t) {
      throw new RuntimeException(t.getMessage(), t);
    }
  }

  @Override
  public void suspendExceptFor(final String name, final Class<?>... overrides) {
    suspendedOverrides.get().push(Arrays.asList(overrides));
  }

  @Override
  public boolean isSuspended() {
    return !suspendedOverrides.get().empty();
  }

  @Override
  public Message receive() {
    throw new UnsupportedOperationException("BlockingMailbox does not support this operation.");
  }

  @Override
  public int pendingMessages() {
    throw new UnsupportedOperationException("BlockingMailbox does not support this operation");
  }

  private void resumeAll() {
    while (!queue.isEmpty()) {
      final Message queued = queue.poll();
      if (queued != null) {
        final Actor actor = queued.actor();
        if (actor != null) {
          actor.viewTestStateInitialization(null);
          queued.deliver();
        }
      }
    }
  }
}
