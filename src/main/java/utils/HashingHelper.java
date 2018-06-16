package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Martijn van der Pol on 16-06-18
 **/
public class HashingHelper {


    public static String generatePasswordHash(String password) {
        try {
            byte[] salt = getSalt();
            int iterations = 2500;

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, 256);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            return iterations + ":" + toHex(salt) + ":" + toHex(hash);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return null;
    }


    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        rand.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }


    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static boolean verifyPassword(String passwordHash, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] hashArray = passwordHash.split(":");
        int iterations = Integer.parseInt(hashArray[0]);
        byte[] salt = fromHex(hashArray[1]);
        byte[] hash = fromHex(hashArray[2]);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] enteredPasswordHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ enteredPasswordHash.length;
        for (int i = 0; i < hash.length && i < enteredPasswordHash.length; i++) {
            diff |= hash[i] ^ enteredPasswordHash[i];
        }

        return diff == 0;
    }

}
