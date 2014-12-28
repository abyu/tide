package org.skk.evented.dimpl;

import org.skk.evented.EventData;

public class MethodInvoked extends Exception {

   private EventData data;

   public MethodInvoked(EventData data) {

       this.data = data;
   }

   public MethodInvoked() {

   }

   public EventData getData() {
       return data;
   }
}
