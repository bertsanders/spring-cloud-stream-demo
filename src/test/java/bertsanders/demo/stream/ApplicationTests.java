package bertsanders.demo.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class ApplicationTests {

  @Autowired
  private InputDestination inputDestination;
  @Autowired
  private OutputDestination outputDestination;

  @Test
  void contextLoads() throws IOException {

      Random randomizer = new Random();
      Message<Integer> message = new GenericMessage<>(randomizer.nextInt(100));
      inputDestination.send(message);

      Message<byte[]> output = outputDestination.receive();
      BigInteger primeNumber = new ObjectMapper().readValue(output.getPayload(), BigInteger.class);

      assertThat(primeNumber.isProbablePrime(10)).isTrue();
  }

}
