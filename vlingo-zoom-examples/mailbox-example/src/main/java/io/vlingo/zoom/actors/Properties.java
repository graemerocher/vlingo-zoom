// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.zoom.actors;

public class Properties {
  public static final java.util.Properties properties;

  private static final String propertiesFile = "/vlingo-zoom.properties";

  static {
    properties = new java.util.Properties();

    try {
      properties.load(Properties.class.getResourceAsStream(propertiesFile));
    } catch (Exception e) {
      // fall through
    }
  }
}
