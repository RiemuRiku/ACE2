package fxAce.data;



public class User
{
  private String master_username;
  
  private String password;
  
  private int salt;
  

  public User()
  {
    master_username = "";
    password = "password";
    salt = 666;
  }
  




  public User(String row)
    throws Exception
  {
    parse(row);
  }
  





  public void parse(String userdata)
    throws Exception
  {
    String[] cols = userdata.split("\\|");
    
    if (cols.length != 3) {
      throw new Exception("Pääkäyttäjätunnuksen tiedot virheellisessä muodossa");
    }
    
    master_username = cols[0];
    password = cols[1];
    salt = Integer.parseInt(cols[2]);
  }
  

  public String toString()
  {
    return master_username + "|" + password + "|" + salt;
  }
  




  public String getMasterUsername()
  {
    return master_username;
  }
  




  public void setMasterUsername(String str)
  {
    master_username = str;
  }
  




  public String getPassword()
  {
    return password;
  }
  




  public void setPassword(String str)
  {
    password = str;
  }
  




  public int getSalt()
  {
    return salt;
  }
  




  public void setSalt(int salt)
  {
    this.salt = salt;
  }
}
