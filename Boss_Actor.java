package org.example;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

public class Boss_Actor extends UntypedActor{
//    public static String firstName;
//    public static String lastName;
    //print debugging message
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart(){ //what to do when this worker is created and started
        log.info("Starting the boss actor");

        Scanner sc = new Scanner(System.in);

        System.out.println("Do you want enter just first name or last name or both?");
        String anwer = sc.nextLine();
        String firstName = "";
        String lastName = "";
        if(anwer.equals("first name")){
            System.out.println("Enter just first name:");
            firstName = sc.nextLine();

        }else if(anwer.equals("last name")){
            System.out.println("Enter just last name:");
            lastName = sc.nextLine();

        }else{
            System.out.println("Enter first name:");
            firstName = sc.nextLine();
            System.out.println("Enter last name:");
            lastName = sc.nextLine();

        }

        int n = 1;
        String name = "Brook";
        while(n <= 4){
            JSONObject j1 = new JSONObject();
            JSONObject j2 = new JSONObject();
            if(!firstName.equals("") && !lastName.equals("")){
                j2.put("firstName", firstName);
                j2.put("lastName", lastName);

            }if(!firstName.equals("")){
                j2.put("firstName", firstName);

            }else if(!lastName.equals("")){
                j2.put("lastName", lastName);

            }

            String path = "/Users/indeshwarchaudhary/Desktop/myfile/File_" + n + ".csv"; //path of the file
            String w = "Worker" + n;   //name of worker
            j2.put("pathName", path);  //insert the path of file in Json object j1

            String key = "To " + w;    //Key of j1
            j1.put(key, j2);           //insert object j2 as value of j1
            ActorRef worker = getContext().actorOf(Props.create(Worker_Actor.class), w); //create an actor
            worker.tell(j1, getSelf()); //send the Object of message to the worker with its identity
            n++;
        }
    }

    @Override
    public void onReceive(Object msg){  //what to do when message is received
        if(msg instanceof String){
            log.info("Message received: " + msg.toString()); //print the message
            log.info("Sender of this message: " + getSender().path().name().toString()); // print the identity of sender
            getSender().tell(new String("done"), getSelf()); // ask to worker to terminate
        }else{
            unhandled(msg); //received undefined msg
        }
    }

    @Override
    public void postStop(){ //what tod when terminated
        log.info("terminating the boss");
    }


}
