package org.skk.tide.intnl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.skk.tide.EventData;
import org.skk.tide.dimpl.MethodInvoked;
import org.skk.tide.dimpl.TestHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class SingleParamHandlerMethodTest {

    @Mock
    private TestHandler testHandler;

    @Mock
    private EventData eventData;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void HandlerInstanceMethodIsInvokedWithTheEventData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, MethodInvoked {

        Method singleParamMethod = TestHandler.class.getMethod("handleEvent", EventData.class);
        SingleParamHandlerMethod handlerMethod = new SingleParamHandlerMethod(testHandler, singleParamMethod, eventData);

        handlerMethod.invoke();

        verify(testHandler).handleEvent(eventData);
    }

}