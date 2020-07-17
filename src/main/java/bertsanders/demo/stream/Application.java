package bertsanders.demo.stream;

import java.math.BigInteger;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  Function<Integer, BigInteger> calculateNthPrime(PrimeNumberService primeNumberService) {
    return primeNumberService::nthPrime;
  }
}
