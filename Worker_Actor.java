package org.example;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.opencsv.CSVReader;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Worker_Actor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    public static int n = 1;


    public void preStart(){ //what to do when this worker is created and started

        log.info("Starting the worker actor");

    }

    @Override
    public void onReceive(Object msg){  //what to do when message is received

        if(msg instanceof JSONObject){


            log.info("Received message: " + msg.toString()); //print the message
            log.info("the sender of this message: " + getSender().path().name().toString()); //print the identity of sender

            JSONObject j = (JSONObject) msg;

            DauClass dau = new DauClass();
            ArrayList<String> key = dau.keyList(j);

            if(key.size() != 0) {
                JSONObject s = (JSONObject) j.get(key.get(0));

                int p = dau.reader(s);
                String worker = key.get(0);
                String message = worker + " reported " + p + " occurrences in file " + worker.charAt((worker.length() -1));

                //System.out.println(p);
                getSender().tell(new String(message), getSelf());

            }
        }else if(msg instanceof String && msg.equals("done")){
            log.info("Received message: " + msg.toString());
            log.info("the sender of this message: " + getSender().path().name().toString()); //print the identity of sender

            getContext().stop(getSelf()); //terminate by itself

        }else{
            unhandled(msg); //received undefined message
        }
    }

    @Override
    public void postStop(){ //what to do when terminated
        log.info("terminating the worker");
    }
}
