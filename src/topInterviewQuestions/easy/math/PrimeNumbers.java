package topInterviewQuestions.easy.math;

public class PrimeNumbers {
    public static void main(String[] args) {
        boolean[] primes = sieveOfEratosthenes(50);
        for (int i = 2; i <= 50; i++) {
            if (primes[i]) {
                System.out.println(i);
            }
        }
    }

    private static boolean[] sieveOfEratosthenes(int n) {
        boolean[] primes = new boolean[n + 1];
        for (int i = 0; i < n; i++) {
            primes[i] = true;
        }
        for (int p = 2; p * p <= n; p++) {
            if (primes[p]) {
                for (int i = p * p; i <= n; i = i + p) {
                    primes[i] = false;
                }
            }
        }
        return primes;
    }
}
