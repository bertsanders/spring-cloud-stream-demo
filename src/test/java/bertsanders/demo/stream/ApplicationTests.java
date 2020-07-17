package bertsanders.demo.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class ApplicationTests {

  @Autowired
  private InputDestination inputDestination;
  @Autowired
  private OutputDestination outputDestination;

  @Test
  void testRandomMessages() {

    Random randomizer = new Random();
    IntStream.range(1, 20)
        .map(i -> randomizer.nextInt(1000) + 1)
        .forEach(this::performTest);
  }

  @SneakyThrows
  private void performTest(int messageInput) {
    Message<Integer> message = new GenericMessage<>(messageInput);
    inputDestination.send(message);

    Message<byte[]> output = outputDestination.receive();
    assertThat(output).as("Failed to receive a message").isNotNull();

    BigInteger primeNumber = new ObjectMapper().readValue(output.getPayload(), BigInteger.class);
    log.info(primeNumber.toString());

    assertThat(primeNumber.isProbablePrime(10)).isTrue();
  }

}
