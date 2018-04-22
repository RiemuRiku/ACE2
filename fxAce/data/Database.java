package fxAce.data;

import fxAce.util.Crypto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;







public class Database
{
  private Crypto crypto;
  private String filename;
  private Users users;
  private Passwords passwords;
  
  public Database()
  {
    this("database.txt");
  }
  







  public int getWebsiteIndex(String master_username, String master_password, String website)
  {
    ArrayList<Password> passwordsList = passwords.getPasswords();
    
    int i = 0; for (int i_end = passwordsList.size(); i < i_end; i++) {
      Password pw = (Password)passwordsList.get(i);
      
      if ((pw.getMaster().equals(master_username)) && 
        (crypto.decryptWithIv(pw.getWeb(), master_password).equals(website))) {
        return i;
      }
    }
    

    return -1;
  }
  





  public Database(String filename)
  {
    crypto = new Crypto();
    this.filename = filename;
    users = new Users();
    passwords = new Passwords();
  }
  
  /* Error */
  public void clearDatabase()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: new 93	java/io/PrintWriter
    //   7: dup
    //   8: aload_0
    //   9: getfield 84	fxAce/data/Database:filename	Ljava/lang/String;
    //   12: invokespecial 95	java/io/PrintWriter:<init>	(Ljava/lang/String;)V
    //   15: astore_3
    //   16: aload_3
    //   17: ifnull +69 -> 86
    //   20: aload_3
    //   21: invokevirtual 96	java/io/PrintWriter:close	()V
    //   24: goto +62 -> 86
    //   27: astore_1
    //   28: aload_3
    //   29: ifnull +7 -> 36
    //   32: aload_3
    //   33: invokevirtual 96	java/io/PrintWriter:close	()V
    //   36: aload_1
    //   37: athrow
    //   38: astore_2
    //   39: aload_1
    //   40: ifnonnull +8 -> 48
    //   43: aload_2
    //   44: astore_1
    //   45: goto +13 -> 58
    //   48: aload_1
    //   49: aload_2
    //   50: if_acmpeq +8 -> 58
    //   53: aload_1
    //   54: aload_2
    //   55: invokevirtual 99	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   58: aload_1
    //   59: athrow
    //   60: astore_1
    //   61: getstatic 105	java/lang/System:err	Ljava/io/PrintStream;
    //   64: new 111	java/lang/StringBuilder
    //   67: dup
    //   68: ldc 113
    //   70: invokespecial 115	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   73: aload_1
    //   74: invokevirtual 116	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   77: invokevirtual 121	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: invokevirtual 125	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   83: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   86: return
    // Line number table:
    //   Java source line #73	-> byte code offset #0
    //   Java source line #75	-> byte code offset #16
    //   Java source line #77	-> byte code offset #60
    //   Java source line #78	-> byte code offset #61
    //   Java source line #80	-> byte code offset #86
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	87	0	this	Database
    //   61	25	1	e	FileNotFoundException
    //   16	20	3	writer	java.io.PrintWriter
    // Exception table:
    //   from	to	target	type
    //   4	38	38	finally
    //   0	60	60	java/io/FileNotFoundException
  }
  
  public void addPassword(String row)
  {
    try
    {
      passwords.addPassword(row);
    }
    catch (Exception e) {
      System.err.println("Ei osattu tallentaa salasanaa, wild problem appeared? " + e.getMessage());
    }
  }
  




  public User[] getUsers()
  {
    return users.getUsers();
  }
  




  public int getPasswordCount()
  {
    return passwords.getLkm();
  }
  




  public int getUserCount()
  {
    return users.getLkm();
  }
  




  public ArrayList<Password> getPasswordList()
  {
    return passwords.getPasswords();
  }
  



  public void saveDatabase()
  {
    clearDatabase();
    try
    {
      Object localObject1 = null;Object localObject4 = null; Object localObject3; label217: try { fo = new PrintStream(new FileOutputStream(filename, true));
      }
      finally
      {
        PrintStream fo;
        

        User[] usersTable;
        
        ArrayList<Password> passwordsList;
        
        int i;
        
        int i;
        
        localObject3 = localThrowable; break label217; if (localObject3 != localThrowable) localObject3.addSuppressed(localThrowable);
      }
    } catch (FileNotFoundException e) {
      System.err.println("Tiedosto ei aukea: " + e.getMessage());
    }
  }
  









  public ArrayList<Password> getPasswordsWithCreds(String master_username)
  {
    ArrayList<Password> passwordsList = passwords.getPasswords();
    ArrayList<Password> passwordsSubList = new ArrayList();
    
    int i = 0; for (int i_end = passwords.getLkm(); i < i_end; i++) {
      if (((Password)passwordsList.get(i)).getMaster().equals(master_username)) {
        passwordsSubList.add((Password)passwordsList.get(i));
      }
    }
    
    return passwordsSubList;
  }
  




  public void importFromFile()
  {
    boolean passwordRow = false;
    boolean userRow = false;
    
    users.clear();
    passwords.clear();
    try {
      Object localObject1 = null;Object localObject4 = null; Object localObject3; label203: try { fi = new Scanner(new FileInputStream(new File(filename)));
      }
      finally
      {
        Scanner fi;
        







        String rivi;
        







        localObject3 = localThrowable; break label203; if (localObject3 != localThrowable) localObject3.addSuppressed(localThrowable);
      }
    } catch (FileNotFoundException ex) {
      System.err.println("Tiedosto ei aukea! Mitä tapahtui: " + ex.getMessage());
    }
    catch (Exception ex)
    {
      System.err.println("Virhe: " + ex.getMessage());
    }
  }
  








  public void addMasterUser(String master_username, String master_password)
    throws Exception
  {
    Random rng = new Random();
    
    String salt = Integer.toString(rng.nextInt(Integer.MAX_VALUE));
    
    StringBuilder sb = new StringBuilder();
    sb.append(master_username + "|");
    sb.append(crypto.hashString(new StringBuilder(String.valueOf(master_password)).append(salt).toString()) + "|");
    sb.append(salt);
    
    users.addUser(sb.toString());
  }
  









  public boolean checkUserCreds(String master_username, String master_password)
  {
    User[] userTable = users.getUsers();
    
    for (int i = 0; i < users.getLkm(); i++)
    {
      if (userTable[i].getMasterUsername().equals(master_username))
      {

        String password_hash = crypto.hashString(master_password + userTable[i].getSalt());
        

        if (userTable[i].getPassword().equals(password_hash)) { return true;
        }
      }
    }
    
    return false;
  }
}
