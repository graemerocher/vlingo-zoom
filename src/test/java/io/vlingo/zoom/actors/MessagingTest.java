// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.zoom.actors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.vlingo.actors.World;

public class MessagingTest {

  @Test
  public void testThatActorMessagesDeliver() {
    final World world = Boot.start("test-messaging");

    final TestProtocol test = world.actorFor(TestProtocol.class, TestProtocolActor.class);

    test.reactTo();
    test.reactTo(1, 2, 3);
    test.reactTo("Hello, World!");

    test.reactions().andThenConsume(reactions -> assertEquals(3, reactions.size()));
  }
}
