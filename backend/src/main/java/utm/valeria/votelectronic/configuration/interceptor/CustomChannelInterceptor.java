package utm.valeria.votelectronic.configuration.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import utm.valeria.votelectronic.decorator.MessageHeadersDecorator;
import utm.valeria.votelectronic.exception.HeaderValueNotFoundException;
import utm.valeria.votelectronic.exception.WorkstationNotFoundException;
import utm.valeria.votelectronic.service.WorkstationService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class CustomChannelInterceptor implements ExecutorChannelInterceptor {
    
    private WorkstationService workstationService;
    
    @Inject
    public void setWorkstationService(WorkstationService workstationService) {
        this.workstationService = workstationService;
    }
    
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (isConnectCommand(headerAccessor.getCommand())) {
            MessageHeadersDecorator messageHeadersDecorator = new MessageHeadersDecorator(message.getHeaders());
            try {
                String workstationId = messageHeadersDecorator.getWorkstationIdHeaderValue();
                this.workstationService.setWorkstationSessionId(workstationId, headerAccessor.getSessionId());
            } catch (HeaderValueNotFoundException | WorkstationNotFoundException ex) {
                throw new IllegalArgumentException(ex.getMessage());
            }
        }
        return message;
    }
    
    private boolean isConnectCommand(StompCommand command) {
        return StompCommand.CONNECT.equals(command);
    }
    
}
