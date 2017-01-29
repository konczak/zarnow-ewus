package pl.konczak.nzoz.ewus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import pl.konczak.nzoz.ewus.client.ewus.AuthenticationClient;

@Configuration
public class EwusConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("pl.konczak.nzoz.ewus.client.broker");
        return marshaller;
    }

    @Bean
    public AuthenticationClient authenticationClient(Jaxb2Marshaller marshaller) {
        final AuthenticationClient client = new AuthenticationClient();
        client.setDefaultUri("https://ewus.nfz.gov.pl/ws-broker-server-ewus-auth-test/services/Auth");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
