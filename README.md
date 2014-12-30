Tide
========

[![Build Status](https://travis-ci.org/abyu/tide.svg)](https://travis-ci.org/abyu/tide)

Event based publisher-subscriber framework for java.

Usage
======

Create events by extending Event class.
Create handlers for these events by annotating your method with HandleEvent passing in the event class name.
The handler class should implement EventHandler interface and should register to any events via EventBus.

If the handler method requires any data, create a data object of type EventData.

Raise a event by instantiating a object of your Event, passing any any data if required, via EventBus.

Instantiate a singleton EventBus for your application. Use EventBus.getInstance() in case it's manually injected.
