package org.skk.tide.dimpl;

import org.skk.tide.EventData;

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
