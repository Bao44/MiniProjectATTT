package vn.edu.iuh.fit.miniprojectattt.conponents;

import java.math.BigInteger;

import java.util.Random;
import java.util.Scanner;

public class RSA {
    private BigInteger n, d, e;

    // random key
    public RSA(int bitlen) {
        Random random = new Random();
        BigInteger p = BigInteger.probablePrime(bitlen / 2, random);
        BigInteger q = BigInteger.probablePrime(bitlen / 2, random);
        n = p.multiply(q); // 1024 bit

        System.out.println(p);
        System.out.println(q);

        // phi = (p - 1) * (q - 1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));


        // Chọn e sao cho gcd(e, phi) = 1 (1 < e < phi)
        e = BigInteger.probablePrime(bitlen / 2, random);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e = e.add(BigInteger.ONE);
        }

        // Tính d sao cho e * d ≡ 1 (mod phi)
        d = e.modInverse(phi);
    }

    // Mã hóa
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n); // c = m^e mod n
    }

    // Giải mã
    public BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(d, n); // m = c^d mod n
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RSA rsa = new RSA(1024); // 1024 bit

        System.out.println("Nhập thông tin cần mã hóa:");
        String message = scanner.nextLine();

        // mã hóa message thành số
        BigInteger messageAsNumber = new BigInteger(message.getBytes());
        System.out.println("Chuyển đổi thông tin thành số: " + messageAsNumber);

        // Mã hóa message
        BigInteger encrypted = rsa.encrypt(messageAsNumber);
        System.out.println("Sau khi mã hóa: " + encrypted);

        // Giải mã message
        BigInteger decrypted = rsa.decrypt(encrypted);
        // Chuyển đổi thông tin thành message
        String decryptedMessage = new String(decrypted.toByteArray());
        System.out.println("Sau khi giải mã: " + decryptedMessage);

        System.out.println("N: " + rsa.getN());
        System.out.println("E: " + rsa.getE());
        System.out.println("D: " + rsa.d);
        scanner.close();
    }
}
