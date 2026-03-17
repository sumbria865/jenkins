import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.text.BreakIterator;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;

class Encrypt implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent en) {
        if (Frame.file.getText() != "Nothing Selected!"){
            try{
                String password = Frame.pass_wd.getText();
                File inputFile = Menu.store;
                byte[] salt = new byte[8];
                SecureRandom srandom = new SecureRandom();
                srandom.nextBytes(salt);
                SecretKeyFactory factory =
                        SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
                SecretKey tmp = factory.generateSecret(spec);
                SecretKeySpec skey = new SecretKeySpec(tmp.getEncoded(), "AES");
                byte[] iv = new byte[128/8];
                srandom.nextBytes(iv);
                IvParameterSpec ivspec = new IvParameterSpec(iv);
                FileOutputStream out = new FileOutputStream(inputFile + ".enc");
                out.write(salt);
                out.write(iv);
                Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
                ci.init(Cipher.ENCRYPT_MODE, skey, ivspec);
                try (FileInputStream in = new FileInputStream(inputFile)) {
                    processFile(ci, in, out);
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "File Encrypted Successfully!");
                inputFile.delete();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Pls Select a File!");
        }
    }
    static private void processFile(Cipher ci,InputStream in,OutputStream out)
            throws javax.crypto.IllegalBlockSizeException,
            javax.crypto.BadPaddingException,
            java.io.IOException
    {
        byte[] ibuf = new byte[1024];
        int len;
        while ((len = in.read(ibuf)) != -1) {
            byte[] obuf = ci.update(ibuf, 0, len);
            if ( obuf != null ) out.write(obuf);
        }
        byte[] obuf = ci.doFinal();
        if ( obuf != null ) out.write(obuf);
    }
}

class Decrypt implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent dn){
        if(Frame.file.getText() != "Nothing Selected!"){
            try {
                String password = Frame.pass_wd.getText();
                File inputFile = Menu.store;
                FileInputStream in = new FileInputStream(inputFile);
                byte[] salt = new byte[8], iv = new byte[128/8];
                in.read(salt);
                in.read(iv);
                SecretKeyFactory factory =
                        SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
                SecretKey tmp = factory.generateSecret(spec);
                SecretKeySpec skey = new SecretKeySpec(tmp.getEncoded(), "AES");
                Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
                ci.init(Cipher.DECRYPT_MODE, skey, new IvParameterSpec(iv));
                String name = String.valueOf(inputFile);
                name = name.substring(0, name.length() - 4);
                try (FileOutputStream out = new FileOutputStream(name)){
                    processFile(ci, in, out);
                    JOptionPane.showMessageDialog(null, "File Decrypted Successfully!");
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    JOptionPane.showMessageDialog(null,"Wrong Password");
                }

            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Pls Select a File!");
        }
    }
    static private void processFile(Cipher ci,InputStream in,OutputStream out)
            throws javax.crypto.IllegalBlockSizeException,
            javax.crypto.BadPaddingException,
            java.io.IOException
    {
        byte[] ibuf = new byte[1024];
        int len;
        while ((len = in.read(ibuf)) != -1) {
            byte[] obuf = ci.update(ibuf, 0, len);
            if ( obuf != null ) out.write(obuf);
        }
        byte[] obuf = ci.doFinal();
        if ( obuf != null ) out.write(obuf);
    }
}

