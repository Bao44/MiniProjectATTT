package vn.edu.iuh.fit.miniprojectattt.conponents;

import java.util.Random;
import java.util.Scanner;

public class SimpleRSA {
    private long n, d, e;

    // Key generation
    public SimpleRSA() {
        Random random = new Random();

        // Chọn các số nguyên tố nhỏ
        long p = getSmallPrime();
        long q = getSmallPrime();

        n = p * q;  // n = p * q

        long phi = (p - 1) * (q - 1);  // phi = (p - 1) * (q - 1)

        // Chọn e sao cho gcd(e, phi) = 1 (1 < e < phi)
        e = 3;
        while (gcd(e, phi) != 1) {
            e++;
        }

        // Tính d sao cho e * d ≡ 1 (mod phi)
        d = modInverse(e, phi);
    }

    // Hàm mã hóa
    public long encrypt(long message) {
        return modPow(message, e, n);  // message^e % n
    }

    // Hàm giải mã
    public long decrypt(long encrypted) {
        return modPow(encrypted, d, n);  // encrypted^d % n
    }

    public long getN() {
        return n;
    }

    public long getE() {
        return e;
    }

    // Hàm tính lũy thừa mod (a^b % mod)
    private long modPow(long base, long exp, long mod) {
        long result = 1;
        base = base % mod;
        while (exp > 0) {
            if ((exp % 2) == 1) {  // Nếu lẻ
                result = (result * base) % mod;
            }
            exp = exp >> 1;  // Chia exp cho 2
            base = (base * base) % mod;
        }
        return result;
    }

    // Hàm tính gcd
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Hàm tính nghịch đảo modular của e mod phi
    private long modInverse(long e, long phi) {
        long m0 = phi, t, q;
        long x0 = 0, x1 = 1;

        if (phi == 1) return 0;

        // Áp dụng Extended Euclidean Algorithm
        while (e > 1) {
            q = e / phi;
            t = phi;
            phi = e % phi;
            e = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0)
            x1 += m0;

        return x1;
    }

    // Hàm lấy số nguyên tố nhỏ
    private long getSmallPrime() {
        long[] smallPrimes = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53};
        Random random = new Random();
        return smallPrimes[random.nextInt(smallPrimes.length)];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimpleRSA rsa = new SimpleRSA();

        System.out.println("Enter a message (number) to encrypt:");
        long message = scanner.nextLong();

        // Mã hóa message
        long encrypted = rsa.encrypt(message);
        System.out.println("Encrypted message: " + encrypted);

        // Giải mã message
        long decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypted message: " + decrypted);

        System.out.println("N: " + rsa.getN());
        System.out.println("E: " + rsa.getE());
        System.out.println("D: " + rsa.d);
        scanner.close();
    }
}

