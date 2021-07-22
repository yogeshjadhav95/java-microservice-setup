package com.prime.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public abstract class EncryptionUtils {

    private EncryptionUtils() {

    }

    private static final String KEY_STRING = "adkj@#$02#@adflkj)(*jlj@#$#@LKjasdjlkj<.,mo@#$@#kljlkdsu343";
    private static final String ALGORITHM = "AES/ECB/PKCS5PADDING";
    private static final String SECRET = "d5VIl1QvAWFhDwP0YKBX8osJAdtmzL9YBF3SNQE2";
    private static final SecretKeySpec SECRET_KEY = getKey();
    private static final Cipher cipherEnc = getEnc();
    private static final Cipher cipherDec = getDec();

    byte[] iv = getRandomNonce(12);

    private static SecretKeySpec getKey() {
        MessageDigest sha = null;
        try {
            if (CollectionUtil.isEmpty(key)) {
                key = SECRET.getBytes(StandardCharsets.UTF_8);
                sha = MessageDigest.getInstance("SHA-1");
                key = sha.digest(key);
                key = Arrays.copyOf(key, 16);
                return new SecretKeySpec(key, "AES");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return SECRET_KEY;
    }


    private static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    private static Cipher getDec() {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
            return cipher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Cipher getEnc() {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
            return cipher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] key;

    public static String[] encryptObject(Object obj) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(stream);
        try {
            // Serialize the object
            out.writeObject(obj);
            byte[] serialized = stream.toByteArray();

            // Setup the cipher and Init Vector
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            byte[] iv = new byte[cipher.getBlockSize()];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Hash the key with SHA-256 and trim the output to 128-bit for the key
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(KEY_STRING.getBytes());
            byte[] key = new byte[16];
            System.arraycopy(digest.digest(), 0, key, 0, key.length);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

            // encrypt
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            // Encrypt & Encode the input
            byte[] encrypted = cipher.doFinal(serialized);
            byte[] base64Encoded = Base64.encodeBase64URLSafe(encrypted);
            String base64String = new String(base64Encoded);
            String urlEncodedData = URLEncoder.encode(base64String, "UTF-8");

            // Encode the Init Vector
            byte[] base64IV = Base64.encodeBase64URLSafe(iv);
            String base64IVString = new String(base64IV);
            String urlEncodedIV = URLEncoder.encode(base64IVString, "UTF-8");

            return new String[]{urlEncodedData, urlEncodedIV};
        } finally {
            stream.close();
            out.close();
        }
    }

    public static Object decryptObject(String base64Data, String base64IV) throws Exception {
        // Decode the data
        byte[] encryptedData = Base64.decodeBase64(base64Data.getBytes());

        // Decode the Init Vector
        byte[] rawIV = Base64.decodeBase64(base64IV.getBytes());

        // Configure the Cipher
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(rawIV);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(KEY_STRING.getBytes());
        byte[] key = new byte[16];
        System.arraycopy(digest.digest(), 0, key, 0, key.length);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // Decrypt the data..
        byte[] decrypted = cipher.doFinal(encryptedData);

        // Deserialize the object
        ByteArrayInputStream stream = new ByteArrayInputStream(decrypted);
        ObjectInput in = new ObjectInputStream(stream);
        Object obj;
        try {
            obj = in.readObject();
        } finally {
            stream.close();
            in.close();
        }
        return obj;
    }


    public static String encrypt(String strToEncrypt) {
        try {
            return Base64.encodeBase64String(cipherEnc.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            return new String(cipherDec.doFinal(Base64.decodeBase64(strToDecrypt)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
