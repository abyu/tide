package org.skk.tide.intnl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.skk.tide.dimpl.MethodInvoked;
import org.skk.tide.dimpl.TestHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class NoParamHandlerMethodTest {

    @Mock
    private TestHandler testHandler;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void HandlerInstanceMethodIsInvoked() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, MethodInvoked {

        Method noParamMethod = TestHandler.class.getMethod("noParam");
        NoParamHandlerMethod handlerMethod = new NoParamHandlerMethod(testHandler, noParamMethod);

        handlerMethod.invoke();

        verify(testHandler).noParam();
    }

}