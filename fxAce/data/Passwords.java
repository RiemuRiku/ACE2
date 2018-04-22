package fxAce.data;

import java.io.PrintStream;
import java.util.ArrayList;





public class Passwords
{
  private ArrayList<Password> passwordsList;
  
  public Passwords()
  {
    passwordsList = new ArrayList();
  }
  




  public int getLkm()
  {
    return passwordsList.size();
  }
  



  public void clear()
  {
    passwordsList.clear();
  }
  




  public ArrayList<Password> getPasswords()
  {
    return passwordsList;
  }
  



  public void addPassword(String row)
    throws Exception
  {
    try
    {
      passwordsList.add(new Password(row));
    }
    catch (Exception e)
    {
      System.err.println("Virhe: " + e.getMessage());
    }
  }
  


  public void addPassword(Password password)
    throws Exception
  {
    try
    {
      passwordsList.add(password);
    }
    catch (Exception e) {
      System.err.println("Virhe: " + e.getMessage());
    }
  }
}
