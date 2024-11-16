package vn.edu.iuh.fit.miniprojectattt.conponents;

import java.util.Random;
import java.util.Scanner;

public class BaseRSA {
    private long n, d, e;

    // tạo khóa
    public BaseRSA() {
        Random random = new Random();

        // Chọn các số nguyên tố nhỏ
        long p = getSmallPrime();
        long q = getSmallPrime();

//        long p = 7;
//        long q = 11;
//        e = 17;

        n = p * q;  // n = p * q

        long phi = (p - 1) * (q - 1);  // phi = (p - 1) * (q - 1)

        // Chọn e sao cho gcd(e, phi) = 1 (1 < e < phi)
        e = 3;
        while (gcd(e, phi) != 1) {
            e++;
        }

        // Tính d sao cho e * d ≡ 1 (mod phi)
        d = mod(e, phi);
    }

    // Hàm mã hóa
    public long maHoa(long message) {
        return modPow(message, e, n);  // message^e % n
    }

    // Hàm giải mã
    public long giaiMa(long encrypted) {
        return modPow(encrypted, d, n);  // maHoa^d % n
    }

    public long getN() {
        return n;
    }

    public long getE() {
        return e;
    }

    // Hàm tính lũy thừa mod (a^b % mod)
    private long modPow(long a, long b, long n) {
        long result = 1;
        a = a % n;
        while (b > 0) {
            if ((b % 2) == 1) {  // Nếu lẻ
                result = (result * a) % n;
            }
            b = b >> 1;  // b = b / 2
            a = (a * a) % n;
        }
        return result;
    }

    // Hàm tính ước chung lớn nhất
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Hàm tính nghịch đảo của e mod phi
    private long mod(long e, long phi) {
        long m0 = phi, t, q;
        long t1 = 0, t2 = 1;

        if (phi == 1) return 0;

        // Áp dụng thuật toán Euclid mở rộng
        // Bài tập trên lớp
        while (e > 1) {
            q = e / phi;
            t = phi;
            phi = e % phi;
            e = t;
            t = t1;
            t1 = t2 - q * t1;
            t2 = t;
        }

        if (t2 < 0)
            t2 += m0;

        return t2;
    }

    // Hàm lấy số nguyên tố nhỏ
    private long getSmallPrime() {
        long[] smallPrimes = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53};
        Random random = new Random();
        return smallPrimes[random.nextInt(smallPrimes.length)];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BaseRSA rsa = new BaseRSA();

        System.out.println("Nhập số cần mã hóa: ");
        long message = scanner.nextLong();

        // Mã hóa message
        long maHoa = rsa.maHoa(message);
        System.out.println("Số sau khi mã hóa: " + maHoa);

        // Giải mã message
        long giaiMa = rsa.giaiMa(maHoa);
        System.out.println("Số sau khi giải mã: " + giaiMa);

        System.out.println("Khóa công khai: {E, N} = {" + rsa.getE() + ", " + rsa.getN() + "}");
        System.out.println("Khóa riêng: {D, N} = {" + rsa.d + ", " + rsa.n + "}");
        scanner.close();
    }
}

