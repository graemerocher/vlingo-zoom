package io.vlingo.zoom.actors;

import io.vlingo.actors.Actor;
import io.vlingo.actors.DeadLetter;
import io.vlingo.actors.LocalMessage;
import io.vlingo.actors.Mailbox;
import io.vlingo.actors.Returns;
import io.vlingo.common.Completes;
import io.vlingo.zoom.actors.TestProtocol;
import io.vlingo.common.Completes;
import java.util.List;
import java.lang.String;

public class TestProtocol__Proxy implements io.vlingo.zoom.actors.TestProtocol {

  private static final String reactToRepresentation1 = "reactTo(java.lang.String)";
  private static final String reactToRepresentation2 = "reactTo(int, int, int)";
  private static final String reactToRepresentation3 = "reactTo()";
  private static final String reactionsRepresentation4 = "reactions()";

  private final Actor actor;
  private final Mailbox mailbox;

  public TestProtocol__Proxy(final Actor actor, final Mailbox mailbox){
    this.actor = actor;
    this.mailbox = mailbox;
  }

  public void reactTo(java.lang.String arg0) {
    if (!actor.isStopped()) {
      final java.util.function.Consumer<TestProtocol> consumer = (actor) -> actor.reactTo(arg0);
      if (mailbox.isPreallocated()) { mailbox.send(actor, TestProtocol.class, consumer, null, reactToRepresentation1); }
      else { mailbox.send(new LocalMessage<TestProtocol>(actor, TestProtocol.class, consumer, reactToRepresentation1)); }
    } else {
      actor.deadLetters().failedDelivery(new DeadLetter(actor, reactToRepresentation1));
    }
  }
  public void reactTo(int arg0, int arg1, int arg2) {
    if (!actor.isStopped()) {
      final java.util.function.Consumer<TestProtocol> consumer = (actor) -> actor.reactTo(arg0, arg1, arg2);
      if (mailbox.isPreallocated()) { mailbox.send(actor, TestProtocol.class, consumer, null, reactToRepresentation2); }
      else { mailbox.send(new LocalMessage<TestProtocol>(actor, TestProtocol.class, consumer, reactToRepresentation2)); }
    } else {
      actor.deadLetters().failedDelivery(new DeadLetter(actor, reactToRepresentation2));
    }
  }
  public void reactTo() {
    if (!actor.isStopped()) {
      final java.util.function.Consumer<TestProtocol> consumer = (actor) -> actor.reactTo();
      if (mailbox.isPreallocated()) { mailbox.send(actor, TestProtocol.class, consumer, null, reactToRepresentation3); }
      else { mailbox.send(new LocalMessage<TestProtocol>(actor, TestProtocol.class, consumer, reactToRepresentation3)); }
    } else {
      actor.deadLetters().failedDelivery(new DeadLetter(actor, reactToRepresentation3));
    }
  }
  public io.vlingo.common.Completes<java.util.List<java.lang.String>> reactions() {
    if (!actor.isStopped()) {
      final java.util.function.Consumer<TestProtocol> consumer = (actor) -> actor.reactions();
      final io.vlingo.common.Completes<java.util.List<java.lang.String>> returnValue = Completes.using(actor.scheduler());
      if (mailbox.isPreallocated()) { mailbox.send(actor, TestProtocol.class, consumer, Returns.value(returnValue), reactionsRepresentation4); }
      else { mailbox.send(new LocalMessage<TestProtocol>(actor, TestProtocol.class, consumer, Returns.value(returnValue), reactionsRepresentation4)); }
      return returnValue;
    } else {
      actor.deadLetters().failedDelivery(new DeadLetter(actor, reactionsRepresentation4));
    }
    return null;
  }
}
