// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.zoom.actors;

import io.vlingo.actors.Stoppable;
import io.vlingo.common.Completes;

public interface TestRequestProtocol extends Stoppable {
  void request(final int value, final TestResponseProtocol respondTo);

  public static interface TestResponseProtocol extends Stoppable {
    Completes<Integer> total();
    void response(final int value, final TestRequestProtocol requestOf);
  }
}
