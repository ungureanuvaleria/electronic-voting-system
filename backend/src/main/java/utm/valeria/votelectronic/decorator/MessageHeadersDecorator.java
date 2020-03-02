package utm.valeria.votelectronic.decorator;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import utm.valeria.votelectronic.exception.HeaderValueNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHeadersDecorator {
    
    private MessageHeaders messageHeaders;
    
    public MessageHeadersDecorator(MessageHeaders messageHeaders) {
        this.messageHeaders = messageHeaders;
    }
    
    public Map<String, String> getHeaderValue(String requiredHeader) throws HeaderValueNotFoundException {
        Object nativeHeaders = getNativeHeadersOrReturnEmptyMap();
        return retrieveHeaderValue(nativeHeaders, requiredHeader);
    }
    
    private Object getNativeHeadersOrReturnEmptyMap() {
        Object nativeHeaders = this.messageHeaders.get(StompHeaderAccessor.NATIVE_HEADERS);
        if (nativeHeaders == null) {
            return new HashMap<String, List<String>>();
        } else {
            return nativeHeaders;
        }
    }
    
    private Map<String, String> retrieveHeaderValue(Object nativeHeaders, String requiredHeader) throws HeaderValueNotFoundException {
        if(nativeHeaders instanceof Map) {
            Map<String, List<String>> headerMap = (Map<String, List<String>>) nativeHeaders;
            List<String> headerValues = headerMap.get(requiredHeader);
            if (headerValues == null) {
                throw new HeaderValueNotFoundException("Could not retrieve headers values since values list is empty!");
            } else {
                Map<String, String> headerValueMap = new HashMap<>();
                headerValueMap.put(requiredHeader, headerValues.get(0));
                return headerValueMap;
            }
        } else {
            throw new HeaderValueNotFoundException("Could not retrieve headers as object is not instanceof Map!");
        }
    }
}

