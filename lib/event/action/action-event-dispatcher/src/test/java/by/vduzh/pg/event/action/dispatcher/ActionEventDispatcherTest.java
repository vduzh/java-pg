package by.vduzh.pg.event.action.dispatcher;

import by.vduzh.pg.event.action.model.ActionEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ActionEventDispatcherTest {

    @MockitoBean
    private ActionEventHandler<String, ActionEvent<String>> stringHandler;

    @MockitoBean
    private ActionEventHandler<Integer, ActionEvent<Integer>> integerHandler;

    @Autowired
    private ActionEventDispatcher dispatcher;

    @Test
    void shouldHandleActionEvent() {
        // mock event
        var event = mock(ActionEvent.class);
        doReturn("string.action").when(event).getAction();

        // mock string handler
        doReturn(true).when(stringHandler).supports("string.action");
        doNothing().when(stringHandler).handle(any());
        // mock integer handler
        doReturn(false).when(integerHandler).supports("string.action");

        // Act
        dispatcher.dispatch(event);

        // Assert
        verify(stringHandler, times(1)).handle(any());
        verify(integerHandler, never()).handle(any());
    }

    @Test
    void shouldThrowHandlerNotFoundExceptionWhenActionIsUnknown() {
        // mock event
        var event = mock(ActionEvent.class);
        doReturn("dummy").when(event).getAction();

        // Act and assert
        assertThatExceptionOfType(HandlerNotFoundException.class)
                .isThrownBy(() -> dispatcher.dispatch(event));
    }
}