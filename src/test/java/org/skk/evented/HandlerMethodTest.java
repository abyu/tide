package org.skk.evented;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.lang.reflect.Method;

import static org.mockito.MockitoAnnotations.initMocks;

public class HandlerMethodTest {

    @Mock private EventHandler handler;
    @Mock private Event event;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void EmptyHandlerMethodIsReturnedWhenHandlerIsNull() throws HandlerMethodNotFoundException {

        HandlerMethod handlerMethod = HandlerMethod.get(null, null, null);

        Assert.assertThat(handlerMethod, IsInstanceOf.instanceOf(EmptyHandlerMethod.class));
    }

    @Test
    public void NoParamHandlerIsReturnedWhenHandlerMethodTakesNoArgument() throws HandlerMethodNotFoundException, NoSuchMethodException {

        Method method = Methods.class.getMethod("noParamMethod");

        HandlerMethod handlerMethod = HandlerMethod.get(handler, method, event);

        Assert.assertThat(handlerMethod, IsInstanceOf.instanceOf(NoParamHandlerMethod.class));

    }

    @Test
    public void SingleParamHandlerIsReturnedWhenHandlerMethodTakesOneArgument() throws HandlerMethodNotFoundException, NoSuchMethodException {

        Method method = Methods.class.getMethod("singleParamMethod", EventData.class);

        HandlerMethod handlerMethod = HandlerMethod.get(handler, method, event);

        Assert.assertThat(handlerMethod, IsInstanceOf.instanceOf(SingleParamHandlerMethod.class));

    }

    @Test(expected = HandlerMethodNotFoundException.class)
    public void MethodNotFoundExceptionIsThrownWhenNotFound() throws NoSuchMethodException, HandlerMethodNotFoundException {

        Method method = Methods.class.getMethod("doubleParamMethod", EventData.class, int.class);

        HandlerMethod.get(handler, method, event);
        Assert.fail("Expected HandlerMethodNotFoundException to be thrown");

    }

}

class Methods {

    public void noParamMethod(){

    }

    public void singleParamMethod(EventData eventData){

    }

    public void doubleParamMethod(EventData eventData, int a){

    }
}