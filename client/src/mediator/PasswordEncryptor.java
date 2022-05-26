package mediator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Class used to transform a plaintext password to a secure one using PBKDF2 algorithm
 *
 */
public class PasswordEncryptor {

    //code idea from here: https://www.quickprogrammingtips.com/java/how-to-securely-store-passwords-in-java.html

    //https://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-132.pdf NITS Security standards
    /**
     * Creates an encrypted password, using plaintext and a Salt
     * @param password plaintext password
     * @param salt salt used for encoded
     * @return encoded password
     * @throws Exception Algorithm errors
     */
    public static String getEncryptedPassword(String password, String salt) throws Exception {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160; // for SHA1
        int iterations = 20000; // NIST specifies  at least 10000

        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

        byte[] encBytes = f.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(encBytes);
    }

    /**
     * Created new salt
     * @return Base64 encoded salt
     */
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
        // NIST recommends minimum 16 bytes.
        byte[] salt = new byte[16];
        if (random != null)
        {
            random.nextBytes(salt);
        }
        return Base64.getEncoder().encodeToString(salt);
    }


}