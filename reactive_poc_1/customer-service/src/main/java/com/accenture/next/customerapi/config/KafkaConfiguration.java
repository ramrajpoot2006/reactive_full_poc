package com.accenture.next.customerapi.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaConfiguration {

  @Value("${kafka.bootstrap-servers}")
  List<String> bootStrapServers;

  @Value("${kafka.producer.client-id-config}")
  String clientId;

  @Value("${kafka.ssl.enabled}")
  boolean isSslEnabled;

  @Value("${kafka.ssl.security-protocol}")
  String secProtocol;

  @Value("${kafka.ssl.keystore-type}")
  String keystoreType;

  @Value("${kafka.ssl.truststore-type}")
  String trustStoreType;

  @Value("${kafka.ssl.key-password}")
  String keyPassword;

  @Value("${kafka.ssl.trust-store-password}")
  String trustStorePassword;

  @Value("${kafka.ssl.trust-store-location-path}")
  String trustStoreLocation;

  @Value("${kafka.ssl.key-store-password}")
  String keyStorePassword;

  @Value("${kafka.ssl.key-store-location-path}")
  String keyStoreLocation;

  @Value("${kafka.ssl.protocol}")
  String protocol;


  @Bean
  public KafkaSender<String, String> kafkaSender() {

    Map<String, Object> producerProps = new HashMap<>();
    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
    producerProps.put(ProducerConfig.ACKS_CONFIG, "all");
    producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    producerProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");
    sslProperties(producerProps);

    SenderOptions<String, String> senderOptions = SenderOptions.create(producerProps);
    return KafkaSender.create(senderOptions);

  }

  private void sslProperties(Map<String, Object> props) {
    if (isSslEnabled) {
      props.put("security.protocol", secProtocol);
      props.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, keystoreType);
      props.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, trustStoreType);
      props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, keyPassword);
      props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, trustStorePassword);
      props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, trustStoreLocation);
      props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keyStorePassword);
      props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keyStoreLocation);
      props.put(SslConfigs.SSL_PROTOCOL_CONFIG, protocol);
    }
  }
}
