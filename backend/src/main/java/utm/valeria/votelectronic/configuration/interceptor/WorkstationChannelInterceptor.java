package utm.valeria.votelectronic.configuration.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import utm.valeria.votelectronic.decorator.MessageHeadersDecorator;
import utm.valeria.votelectronic.exception.FingerprintNotFoundException;
import utm.valeria.votelectronic.exception.HeaderValueNotFoundException;
import utm.valeria.votelectronic.exception.WorkstationNotFoundException;
import utm.valeria.votelectronic.service.FingerprintService;
import utm.valeria.votelectronic.service.WorkstationService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class WorkstationChannelInterceptor implements ExecutorChannelInterceptor {
    private static final String WORKSTATION_HEADER_NAME = "workstation_id";
    private static final String FINGERPRINT_HEADER_NAME = "fingerprint_id";
    
    private WorkstationService workstationService;
    private FingerprintService fingerprintService;
    
    @Inject
    public void setWorkstationService(WorkstationService workstationService) {
        this.workstationService = workstationService;
    }
    
    @Inject
    public void setFingerprintService(FingerprintService fingerprintService) {
        this.fingerprintService = fingerprintService;
    }
    
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (isConnectCommand(headerAccessor.getCommand())) {
            MessageHeadersDecorator messageHeadersDecorator = new MessageHeadersDecorator(message.getHeaders());
            try {
                Map<String, String> headerValue = messageHeadersDecorator.getHeaderValue(WORKSTATION_HEADER_NAME);
                String workstationId = headerValue.get(WORKSTATION_HEADER_NAME);
                this.workstationService.setWorkstationSessionId(workstationId, headerAccessor.getSessionId());
            } catch (HeaderValueNotFoundException | WorkstationNotFoundException ex) {
                try {
                    Map<String, String> headerValue = messageHeadersDecorator.getHeaderValue(FINGERPRINT_HEADER_NAME);
                    String fingerprintId = headerValue.get(FINGERPRINT_HEADER_NAME);
                    this.fingerprintService.setFingerprintSessionId(fingerprintId, headerAccessor.getSessionId());
                } catch (HeaderValueNotFoundException | FingerprintNotFoundException exception) {
                        throw new IllegalArgumentException("No header was present in message");
                }
            }
        }
        return message;
    }
    
    private boolean isConnectCommand(StompCommand command) {
        return StompCommand.CONNECT.equals(command);
    }
    
}
