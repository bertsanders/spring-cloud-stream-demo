package bertsanders.demo.stream;

import java.math.BigInteger;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PrimeNumberService {
  private Random randomizer;

  @PostConstruct
  public void init() {
    randomizer = new Random();
  }

  public BigInteger nthPrime(int input) {
    log.info("Calculating the Nth Prime for " + input);
    if (randomizer.nextInt(5) < 1) {
      throw new RuntimeException("random failure");
    }

    int primeCount = 0;
    BigInteger number = BigInteger.ONE;
    while (primeCount < input) {
      number = number.add(BigInteger.ONE);
      if (isPrime(number)) {
        ++primeCount;
      }
    }

    return number;
  }

  private boolean isPrime(BigInteger candidate) {
    if (candidate.compareTo(BigInteger.ONE) <= 0) {
      return false;
    }

    BigInteger potentialDivisor = BigInteger.TWO;
    while (!candidate.mod(potentialDivisor).equals(BigInteger.ZERO)) {
      potentialDivisor = potentialDivisor.add(BigInteger.ONE);
    }

    return potentialDivisor.equals(candidate);
  }
}
