package fxAce.util;

import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto
{
  private final String CIPHER_ALGORITHM = "AES";
  private final String DIGEST_ALGORITHM = "SHA-256";
  
  private final Base64.Encoder encoder;
  
  private final Base64.Decoder decoder;
  private SecureRandom prng;
  private Cipher cipher;
  private MessageDigest md;
  
  public Crypto()
  {
    encoder = java.util.Base64.getEncoder();
    decoder = java.util.Base64.getDecoder();
    try
    {
      prng = SecureRandom.getInstance("SHA1PRNG");
      cipher = Cipher.getInstance("AES");
      md = MessageDigest.getInstance("SHA-256");
    }
    catch (Exception e)
    {
      System.err.println("Virhe kryptausluokkaa muodostaessa: " + e.getMessage());
    }
  }
  






  public byte[] hash(String plaintext, int len)
  {
    md.update(plaintext.getBytes());
    byte[] digest = md.digest();
    
    return java.util.Arrays.copyOf(digest, len);
  }
  




  public String hashString(String plaintext)
  {
    return bytesToHex(hash(plaintext, 32));
  }
  






  public String encrypt(String plaintext, String key)
  {
    javax.crypto.SecretKey byteKey = new SecretKeySpec(hash(key, 32), 0, 16, "AES");
    
    byte[] encrypted = new byte[0];
    try
    {
      cipher.init(1, byteKey);
      encrypted = cipher.doFinal(plaintext.getBytes());
    }
    catch (Exception e)
    {
      System.err.println("Virhe kryptausluokkaa muodostaessa: " + e.getMessage());
    }
    
    return encoder.encodeToString(encrypted);
  }
  




  public String encryptWithIv(String plaintext, String key)
  {
    try
    {
      Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
      
      byte[] iv = new byte[16];
      prng.nextBytes(iv);
      
      SecretKeySpec keySpec = new SecretKeySpec(hash(key, 16), "AES");
      IvParameterSpec ivSpec = new IvParameterSpec(iv);
      c.init(1, keySpec, ivSpec);
      byte[] encrypted = c.doFinal(plaintext.getBytes());
      



      byte[] merge = new byte[iv.length + encrypted.length];
      System.arraycopy(iv, 0, merge, 0, iv.length);
      System.arraycopy(encrypted, 0, merge, iv.length, encrypted.length);
      
      return encoder.encodeToString(merge);
    }
    catch (Exception e)
    {
      System.err.println("Virhe encryptauksessa: " + e.getMessage());
    }
    
    return "";
  }
  




  public String bytesToHex(byte[] data)
  {
    char[] hex = {
      '0', '1', '2', '3', '4', '5', '6', '7', 
      '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    

    StringBuilder sb = new StringBuilder();
    
    for (byte b : data) {
      sb.append(hex[(b >> 4 & 0xF)] + hex[(b & 0xF)]);
    }
    
    return sb.toString();
  }
  




  public String decrypt(String encrypted, String key)
  {
    try
    {
      javax.crypto.SecretKey byteKey = new SecretKeySpec(hash(key, 32), 0, 16, "AES");
      
      byte[] encryptedBytes = decoder.decode(encrypted);
      
      cipher.init(2, byteKey);
      byte[] decrypted = cipher.doFinal(encryptedBytes);
      
      return new String(decrypted);
    }
    catch (Exception e)
    {
      System.err.println("Decryptaus ei onnisu, mitä tapahtui? " + e.getMessage());
    }
    
    return "";
  }
  





  public String decryptWithIv(String encrypted, String key)
  {
    try
    {
      Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
      

      byte[] blob = decoder.decode(encrypted);
      


      byte[] iv = new byte[16];
      System.arraycopy(blob, 0, iv, 0, 16);
      
      byte[] encryptedBytes = new byte[blob.length - 16];
      System.arraycopy(blob, 16, encryptedBytes, 0, encryptedBytes.length);
      
      SecretKeySpec keySpec = new SecretKeySpec(hash(key, 16), "AES");
      IvParameterSpec ivSpec = new IvParameterSpec(iv);
      c.init(2, keySpec, ivSpec);
      byte[] decrypted = c.doFinal(encryptedBytes);
      
      return new String(decrypted);
    }
    catch (Exception e)
    {
      System.err.println("Decryptaus IV:llä ei onnisu, mitä tapahtui? " + e.getMessage());
    }
    
    return "";
  }
}
