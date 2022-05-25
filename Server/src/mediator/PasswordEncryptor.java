package mediator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordEncryptor {

    // Get a encrypted password using PBKDF2 hash algorithm
    public static String getEncryptedPassword(String password, String salt) throws Exception {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160; // for SHA1
        int iterations = 20000; // NIST specifies 10000

        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

        byte[] encBytes = f.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(encBytes);
    }

    // Returns base64 encoded salt
    public static String getNewSalt(){
        // Don't use Random!
        SecureRandom random = null;
        try
        {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        // NIST recommends minimum 4 bytes. We use 8.
        byte[] salt = new byte[8];
        if (random != null)
        {
            random.nextBytes(salt);
        }
        return Base64.getEncoder().encodeToString(salt);
    }


}