
package homework5;

/**
 * Date : 11/19/2020
 * @author Keith Combs
 * Purpose : File input, output and decrypt program 
 */

import java.util.*;

public class HomeWork5 {
    public static boolean flag = true;
    static int choice = 0;
    
    public static void menu(){
        FileWork files = new FileWork();
        Scanner scan = new Scanner(System.in);
        while(flag){
            flag = true;
            System.out.println("************************************\n"+
            "0 – Exit\n" +
            "1 – Select directory\n" +
            "2 – List directory content (first level)\n" +
            "3 – List directory content (all levels)\n" +
            "4 – Delete file\n" +
            "5 – Display file (hexadecimal view)\n" +
            "6 – Encrypt file (XOR with password)\n" +
            "7 – Decrypt file (XOR with password)\n" +
            "Select option:");
            try{
                String selection = scan.next();
                choice = Integer.parseInt(selection);
                switch (choice){
                    case 1:
                        System.out.println("1. Selected. Please Enter the directory");
                        files.setDirectory("C:/"+scan.next());
                        
                        break;
                    case 2:
                        System.out.println("2. Selected. Listing directory content");
                        files.listFiles();
                        
                        break;
                    case 3:
                        System.out.println("3. Selected. Listing directory content");
                        files.listAllFiles();
                        
                        break;
                    case 4: 
                        System.out.println("4. Selected. Delete File");
                        files.deleteDir();
                        break;

                    case 5:
                        System.out.println("5. Selected. Display in Hex");
                        System.out.println(files.convertFilesToHex());
                        break; 
                    case 6:
                        System.out.println("6. Selected. Encryption");
                        files.xorEncrypt();
                        break;
                    case 7:
                        System.out.println("7. Selected. Decryption");
                        files.xorDecrypt();
                        break;
                    case 0:
                        System.out.println("Goodbye");
                        flag = false;
                        break;
                    default:
                        System.out.println("Invalid input, Please select 1-7");
                        flag = true;
                }

            }catch (Exception err){
                flag = true; 
                System.out.println("Invalid Choice");
                //err.printStackTrace();
            }
        }
    }
    
    
    
    public static void main(String[] args) {
        

        menu();
        
    
    }
}
