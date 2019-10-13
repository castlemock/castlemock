package com.castlemock.web.basis.web.view.controller;

import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.AbstractControllerTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class ViewControllerAdviceTest extends AbstractControllerTest {

    private static final String PAGE = "partial/basis/error/error.jsp";
    private static final String MESSAGE = "message";

    @Spy
    @InjectMocks
    private ViewControllerAdvice viewControllerAdvice;

    @Override
    protected AbstractController getController() {
        return viewControllerAdvice;
    }

    @Test
    public void test() {
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        final Exception exception = new Exception("Error exception");

        Mockito.when(request.getRequestURI()).thenReturn("/web/error");
        Mockito.when(request.getMethod()).thenReturn("GET");

        final ModelAndView modelAndView = viewControllerAdvice.defaultErrorViewHandler(request, exception);
        Assert.assertEquals("index", modelAndView.getViewName());
        Assert.assertEquals("Error exception", modelAndView.getModel().get(MESSAGE));
        Assert.assertEquals(PAGE, modelAndView.getModel().get(PARTIAL));

    }
}
