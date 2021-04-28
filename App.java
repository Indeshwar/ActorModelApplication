package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**lsa
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // The ActorSystem extends “ActorRefFactory“ which is a hierarchical group of actors which share common configuration.
        // It is also the entry point for creating or looking up actors.
        ActorSystem system = ActorSystem.create("Hello");

        ActorRef bossActor = system.actorOf(Props.create(Boss_Actor.class), "mainActor");

    }
}
