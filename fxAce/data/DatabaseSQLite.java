package fxAce.data;

import fxAce.util.Crypto;
import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class DatabaseSQLite
{
  private Crypto crypto;
  private Users users;
  private Passwords passwords;
  private Connection c = null;
  

  private File f;
  

  public DatabaseSQLite(String filename)
  {
    f = new File(filename);
    try
    {
      c = DriverManager.getConnection("jdbc:sqlite:" + filename);
      
      crypto = new Crypto();
      users = new Users();
      passwords = new Passwords();
    }
    catch (Exception e)
    {
      System.err.println("Virhe: " + e.getMessage());
      System.exit(-1);
    }
    

    if (getSize() == 0) {
      System.out.println("Luodaan " + filename + ".");
      createDatabase();
    }
  }
  



  public void createDatabase()
  {
    String sqlCreateUsers = 
      "CREATE TABLE Users (master_username   TEXT,master_password   TEXT,salt  INTEGER);";
    




    String sqlCreatePasswords = 
      "CREATE TABLE Passwords (master_username   TEXT,username  TEXT,password  TEXT,comment   TEXT,created_date  TEXT,modified_date TEXT,website   TEXT);";
    






    try
    {
      Object localObject1 = null;Object localObject4 = null; Object localObject3; label94: try { stmt = c.createStatement();
      }
      finally
      {
        Statement stmt;
        
        localObject3 = localThrowable; break label94; if (localObject3 != localThrowable) localObject3.addSuppressed(localThrowable);
      }
    } catch (Exception e) {
      System.err.println("Virhe: " + e.getMessage());
      System.exit(-1);
    }
  }
  




  public int getSize()
  {
    return (int)f.length();
  }
  



  public void importUsers()
  {
    String sql = "SELECT master_username, master_password, salt FROM Users;";
    try {
      Object localObject1 = null;Object localObject4 = null; Object localObject3; label220: try { stmt = c.prepareStatement(sql);
      }
      finally
      {
        PreparedStatement stmt;
        
        Object localObject5;
        Object localObject8;
        ResultSet results;
        User user;
        Object localObject7;
        localObject3 = localThrowable1; break label220; if (localObject3 != localThrowable1) localObject3.addSuppressed(localThrowable1);
      }
    } catch (Exception e) {
      System.err.println("Virhe: " + e.getMessage());
    }
  }
  


  public void importPasswords()
  {
    String sql = 
      "SELECT master_username, username, password, comment, created_date, modified_date, website FROM Passwords;";
    





    try
    {
      Object localObject1 = null;Object localObject4 = null; Object localObject3; label276: try { stmt = c.createStatement();
      }
      finally
      {
        Statement stmt;
        

        Object localObject5;
        

        Object localObject8;
        

        ResultSet results;
        
        Password password;
        
        Object localObject7;
        
        localObject3 = localThrowable1; break label276; if (localObject3 != localThrowable1) localObject3.addSuppressed(localThrowable1);
      }
    } catch (Exception e) {
      System.err.println("Virhe: " + e.getMessage());
    }
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
  







  public boolean websiteExists(String master_username, String master_password, String website)
  {
    String sql = "SELECT master_username, website FROM Passwords WHERE master_username LIKE ?;";
    try {
      Object localObject1 = null;Object localObject4 = null; Object localObject3; try { PreparedStatement stmt = c.prepareStatement(sql);
        try { stmt.setString(1, master_username);
          
          Object localObject5 = null;Object localObject8 = null; Object localObject7; try { ResultSet results = stmt.executeQuery();
            try { while (results.next())
              {
                if (website.equals(crypto.decryptWithIv(results.getString("website"), master_password)))
                  return true; }
            } finally {}
          } finally {
            if (localObject6 == null) localObject7 = localThrowable2; else if (localObject7 != localThrowable2) localObject7.addSuppressed(localThrowable2);
          } } finally { if (stmt != null) stmt.close(); } } finally { if (localObject2 == null) localObject3 = localThrowable1; else if (localObject3 != localThrowable1) { localObject3.addSuppressed(localThrowable1);
        }
      }
      


      return false;
    }
    catch (Exception e)
    {
      System.err.println("Virhe: " + e.getMessage());
    }
  }
  










  public ArrayList<Password> getPasswordsWithCreds(String master_username, String master_password, String search_string)
  {
    ArrayList<Password> passwordsList = passwords.getPasswords();
    ArrayList<Password> passwordsSubList = new ArrayList();
    


    String search_regex = search_string.replaceAll("[^a-zA-Z0-9\\.]", "");
    search_regex = search_regex.replaceAll("\\.", "\\\\.");
    

    Pattern p = Pattern.compile(search_regex);
    



    int i = 0; for (int i_end = passwords.getLkm(); i < i_end; i++) {
      Password pw = (Password)passwordsList.get(i);
      
      if (pw.getMaster().equals(master_username))
      {
        if (search_string.isEmpty()) { passwordsSubList.add(pw);
        }
        else {
          Matcher m = p.matcher(crypto.decryptWithIv(pw.getWeb(), master_password));
          

          if (m.find()) { passwordsSubList.add(pw);
          }
        }
      }
    }
    return passwordsSubList;
  }
  




  public void importFromFile()
  {
    users.clear();
    passwords.clear();
    
    importUsers();
    importPasswords();
  }
  




  public void insertPassword(String row)
  {
    String sqlInsertPassword = 
      "INSERT INTO Passwords (master_username,username,password,comment,created_date,modified_date,website) VALUES (?, ?, ?, ?, ?, ?, ?);";
    






    try
    {
      Object localObject1 = null;Object localObject4 = null; Object localObject3; label186: try { sql = c.prepareStatement(sqlInsertPassword);
      }
      finally
      {
        PreparedStatement sql;
        


        Password password;
        

        localObject3 = localThrowable; break label186; if (localObject3 != localThrowable) localObject3.addSuppressed(localThrowable);
      }
    } catch (Exception e) {
      System.err.println("Virhe: " + e.getMessage());
    }
  }
  




  public void deletePassword(String encrypted_website)
  {
    String sql = "DELETE FROM Passwords WHERE website LIKE ?;";
    try {
      Object localObject1 = null;Object localObject4 = null; Object localObject3; label92: try { stmt = c.prepareStatement(sql);
      } finally {
        PreparedStatement stmt;
        localObject3 = localThrowable; break label92; if (localObject3 != localThrowable) localObject3.addSuppressed(localThrowable);
      }
    } catch (Exception e) {
      System.err.println("Virhe: " + e.getMessage());
    }
  }
  




  public void insertUser(String row)
    throws Exception
  {
    String sqlInsertUser = 
      "INSERT INTO Users (master_username,master_password,salt) VALUES (?, ?, ?);";
    


    try
    {
      Object localObject1 = null;Object localObject4 = null; Object localObject3; label141: try { sql = c.prepareStatement(sqlInsertUser);
      }
      finally
      {
        PreparedStatement sql;
        

        User user;
        
        localObject3 = localThrowable; break label141; if (localObject3 != localThrowable) localObject3.addSuppressed(localThrowable);
      }
    } catch (Exception e) {
      System.err.println("Virhe: " + e.getMessage());
    }
  }
  






  public void addMasterUser(String master_username, String master_password)
    throws Exception
  {
    if (users.userExists(master_username)) {
      throw new Exception("Käyttäjä löytyy jo tietokannasta");
    }
    


    Random rng = new Random();
    
    String salt = Integer.toString(rng.nextInt(Integer.MAX_VALUE));
    
    StringBuilder sb = new StringBuilder();
    sb.append(master_username + "|");
    sb.append(crypto.hashString(new StringBuilder(String.valueOf(master_password)).append(salt).toString()) + "|");
    sb.append(salt);
    

    users.addUser(sb.toString());
    

    insertUser(sb.toString());
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
