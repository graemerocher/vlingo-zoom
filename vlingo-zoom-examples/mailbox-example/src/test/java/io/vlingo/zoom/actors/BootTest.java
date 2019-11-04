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

public class BootTest {
  private static final String BootWorldName = "test-boot";

  @Test
  public void testThatWorldBoots() {
    Boot.main(new String[] { BootWorldName });

    final World world = Boot.zoomBootWorld();

    assertEquals(BootWorldName, world.name());
  }

  @Test
  public void testThatWorldStarts() {
    final World world = Boot.start(BootWorldName);

    assertEquals(BootWorldName, world.name());
  }
}
