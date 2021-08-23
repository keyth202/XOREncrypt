package homework5;

/**
 * FileWork.java
 * Created on Nov 21, 2020
 * @author Keith Combs II
 * Purpose: Manipulate files for the choices on the menu
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Scanner;


public class FileWork {
    
    private String dirName;
    private Path path;
    private File file;
    
    
    public void listFiles() {
        try{
        Files.list(new File(dirName).toPath()).limit(20)
                .forEach(path -> {
                    System.out.println(path);
                });
        }catch(IOException ioe){
            System.out.println("Invalid file path");
            ioe.printStackTrace();
        }
    }
    public void listAllFiles() {
        try{
        File file = new File(dirName);
        
        Files.walkFileTree(file.toPath(), Collections.emptySet(), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc){
                System.out.println(dir);
                return FileVisitResult.CONTINUE;
            }
        });        
        }catch(IOException ioe){
            System.out.println("Invalid file path");
            ioe.printStackTrace();
        }
    }
     
    public void deleteDir(){
        
        Scanner scan = new Scanner(System.in);
        System.out.println("You are in "+ this.dirName+". Please enter the file that you want to delete");
        String fileName = scan.next();
        
        Path newPath = Paths.get(path+"/"+fileName);
        try {
   
            if(Files.exists(newPath)){
                System.out.println("File exists, deleting");
                Files.delete(newPath);
                listFiles();
            }else{
              System.out.println("File does not exist");
            }
        
        }catch (IOException x) {
            System.err.println(x);
        } 
        
         
         
     }
     
    public void setDirectory(String dir){
       try{  
         this.path = Paths.get(dir);
        
         if(!Files.notExists(path)){
             this.dirName = dir;
             System.out.println("Your path exists and you have selected this directory: "+ dir);
         }else{
             System.out.println("Your directory does not exist, please enter a directory ");
             HomeWork5.flag = true;
         }
         
       }catch(Exception e){
           System.out.println("Directory does not exist");
           e.printStackTrace();
       }
         
     }

    public String convertFilesToHex() throws IOException{
        
        Scanner scan = new Scanner(System.in);
        System.out.println("You are in "+ this.dirName+". Please enter the file that you want to display");
        String fileName = scan.next();
        this.path =  Paths.get(dirName+"/"+fileName);
        
        
        final String NEW_LINE = System.lineSeparator();
        final String UNKNOWN_CHARACTER = ".";
        
        if (Files.notExists(path)) {
            throw new IllegalArgumentException("File not found! " + path);
        }
        System.out.println(path.toString());
        StringBuilder result = new StringBuilder();
        StringBuilder hex = new StringBuilder();
        StringBuilder input = new StringBuilder();

        int count = 0;
        int value;
        int offset =0;
        
        try (InputStream inputStream = Files.newInputStream(path)) {

            while ((value = inputStream.read()) != -1) {
                offset++;
                
                hex.append(String.format("%02X ", value));

                //If the character is unable to convert, just prints a dot "."
                if (!Character.isISOControl(value)) {
                    input.append((char) value);
                } else {
                    input.append(UNKNOWN_CHARACTER);
                }
                
                // After 24 bytes, reset everything for formatting purpose
                if (count == 23) {
                    result.append(String.format("%-60s | %08x%n", hex, offset));
                    hex.setLength(0);
                    input.setLength(0);
                    count = 0;
                } else {
                    count++;
                }

            }

            // if the count>0, meaning there is remaining content
            if (count > 0) {
                result.append(String.format("%-60s | %s%n", hex, input));
            }
        }
        return result.toString();
        
    }
    
    public void xorEncrypt(){
        

        Scanner scan = new Scanner(System.in);
        
        
        System.out.println("You are in "+ this.dirName+". Please enter the file that you want to encrypt");
        String fileName = scan.next();         
        
        Crypto encrypt = new Crypto();
        encrypt.xorEncrypt(path, dirName, fileName, 0);
        
        
    }
    
    public void xorDecrypt(){
        
        Scanner scan = new Scanner(System.in);
        
        
        System.out.println("You are in "+ this.dirName+". Please enter the file that you want to decrypt");
        String fileName = scan.next();         
        
        Crypto encrypt = new Crypto();
        encrypt.xorEncrypt(path, dirName, fileName, 1);
        
        
    }
}
