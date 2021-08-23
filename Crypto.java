package homework5;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Crypto.java
 * Created on Nov 21, 2020
 * @author Keith Combs II
 * Purpose: XOR encryption and decryption method
 * Reference: Modified from https://www.programmersought.com/article/52181067177/
 */
public class Crypto {
    
    public void xorEncrypt(Path path, String dirName, String fileName, int eord){
        
        
        Scanner scan = new Scanner(System.in);
        
        path =  Paths.get(dirName+"/"+fileName);
        
        Path newPath = Paths.get(dirName+"/ENC"+fileName);
        
        
        System.out.println("Please Enter a password");
        String password = scan.next();
        
        byte[] byteArr = password.getBytes(StandardCharsets.UTF_8);
        byte[] xorArr = new byte[256];
        
        
        // Allows the password to be repeated for 256 bytes
        int pChar =0;
        for(int i = 0; i<256; i++){
            if(i<(256-byteArr.length) && pChar < byteArr.length){
                xorArr[i] =byteArr[pChar];
                pChar++;
            }else if(pChar==byteArr.length){
                pChar = 0;
            }else if(byteArr.length>256-i){
                xorArr[i] =byteArr[pChar];
                pChar++;
            }else{
                pChar = 0;
            }
            
        }
 
        //System.out.println(Arrays.toString(xorArr));
      
        try{
            
            if(Files.exists(newPath)){
                System.out.println("Decrypted File exists, deleting and recreating");
                Files.delete(newPath);
               
            }else if(Files.notExists(path)){
                throw new IllegalArgumentException("File not found! " + path);
            }
            
            String inputDir = dirName+"/"+fileName;
            if(eord ==0){
                String outputDir = dirName+"/ENC"+fileName;
                File outFile = new File(outputDir);
                File inFile = new File(inputDir);
                encryptFile(inFile,outFile,xorArr);
            }else{
                String outputDir = dirName+"/DEC"+fileName;
                File outFile = new File(outputDir);
                File inFile = new File(inputDir);
                encryptFile(inFile,outFile,xorArr);
            }

            
        }catch(IOException ioe){
            System.out.println("Invalid File");
        }catch(IllegalArgumentException iae){
            System.out.println("File not found! " + path);
        }catch(Exception err){
            err.printStackTrace();
        }
        
    }
    
   


    public static void encryptFile(File inFile, File outFile, byte[] key) throws Exception {
        InputStream in = null;
        OutputStream out = null;

        try {
            
    
            in = new FileInputStream(inFile);
         
            // Limiting (10240 bytes) for speed
            out = new BufferedOutputStream(new FileOutputStream(outFile), 10240);

            int b = -1;
            long i = 0;

            while ((b = in.read()) != -1) {
                // The data is XORed with the key, and then XORed with the lower 8 bits of the loop variable (increased complexity)
                b = (b ^ key[(int) (i % key.length)] ^ (int) (i & 0xFF));
                // Write an encrypted/decrypted byte
                out.write(b);
                // loop variable increment
                i++;
            }
            out.flush();

        } finally {
            close(in);
            close(out);
        }
    }

    private static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
              System.out.println("Can't close file");
            }
        }
    }
}