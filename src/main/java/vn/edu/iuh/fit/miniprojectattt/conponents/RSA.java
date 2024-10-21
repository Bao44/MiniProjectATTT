package vn.edu.iuh.fit.miniprojectattt.conponents;

import java.math.BigInteger;

import java.util.Random;
import java.util.Scanner;

public class RSA {
    private BigInteger n, d, e;

    // Key generation
    public RSA(int bitlen) {
        Random random = new Random();
        BigInteger p = BigInteger.probablePrime(bitlen / 2, random);
        BigInteger q = BigInteger.probablePrime(bitlen / 2, random);
        n = p.multiply(q); // 1024 bit

        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));


        // Choose e such that 1 < e < phi and gcd(e, phi) = 1 (public exponent)
        e = BigInteger.probablePrime(bitlen / 2, random);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e = e.add(BigInteger.ONE);
        }

        // Compute d such that e * d ≡ 1 (mod phi)
        d = e.modInverse(phi);
    }

    // Encryption
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n); // c = m^e mod n
    }

    // Decryption
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

        System.out.println("Enter a message to encrypt:");
        String message = scanner.nextLine();

        // Convert the message to a number
        BigInteger messageAsNumber = new BigInteger(message.getBytes());

        // Encrypt the message
        BigInteger encrypted = rsa.encrypt(messageAsNumber);
        System.out.println("Encrypted message: " + encrypted);

        // Decrypt the message
        BigInteger decrypted = rsa.decrypt(encrypted);
        String decryptedMessage = new String(decrypted.toByteArray());
        System.out.println("Decrypted message: " + decryptedMessage);

        System.out.println("N: " + rsa.getN());
        System.out.println("E: " + rsa.getE());
        System.out.println("D: " + rsa.d);
        scanner.close();
    }
}
