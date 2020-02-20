package utm.valeria.votelectronic.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import utm.valeria.votelectronic.configuration.interceptor.CustomChannelInterceptor;

import javax.inject.Inject;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {
    
    private ExecutorChannelInterceptor executorChannelInterceptor;
    
    @Inject
    public void setExecutorChannelInterceptor(CustomChannelInterceptor customChannelInterceptor) {
        this.executorChannelInterceptor = customChannelInterceptor;
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/votingSystemApp")
                .setAllowedOrigins("*")
                .withSockJS();
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/fingerprints");
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(this.executorChannelInterceptor);
    }
}
