package fxAce.data;

import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;






public class Password
{
  private String master_username;
  private String username;
  private String password;
  private String created_date;
  private String modified_date;
  private String comment;
  private String website;
  
  public Password()
  {
    master_username = "";
    username = "username here";
    password = "password here";
    created_date = createDate();
    modified_date = createDate();
    comment = "no comments";
    website = "nignognag.com";
  }
  




  public Password(String row)
    throws Exception
  {
    parse(row);
  }
  

  public String toString()
  {
    return master_username + "|" + username + "|" + password + "|" + created_date + "|" + modified_date + "|" + comment + "|" + website;
  }
  




  public void parse(String row)
    throws Exception
  {
    String[] cols = row.split("\\|");
    
    if (cols.length != 7) {
      throw new InvalidParameterException("Salasanan tiedot virheellisessä muodossa");
    }
    
    master_username = cols[0];
    username = cols[1];
    password = cols[2];
    created_date = cols[3];
    modified_date = cols[4];
    comment = cols[5];
    website = cols[6];
  }
  




  public void printTxt(PrintStream out)
  {
    out.println(master_username + "  " + username + "  " + password + "  " + created_date + "  " + modified_date + "  " + comment + "  " + website);
  }
  




  public String getMaster()
  {
    return master_username;
  }
  




  public void setMaster(String master)
  {
    master_username = master;
  }
  




  public String getUser()
  {
    return username;
  }
  




  public void setUser(String user)
  {
    username = user;
  }
  




  public String getPassword()
  {
    return password;
  }
  




  public void setPassword(String passu)
  {
    password = passu;
  }
  




  public String getDate()
  {
    String data = createDate();
    return data;
  }
  




  public void setDateCreated(String paiva)
  {
    created_date = paiva;
  }
  




  public String getDateCreated()
  {
    return created_date;
  }
  




  public String getDateModified()
  {
    return modified_date;
  }
  




  public void setDateModified(String paiva)
  {
    modified_date = paiva;
  }
  




  public String getComment()
  {
    return comment;
  }
  




  public void setComment(String Kommentti)
  {
    comment = Kommentti;
  }
  




  public String getWeb()
  {
    return website;
  }
  





  public void setWeb(String spoderman)
  {
    website = spoderman;
  }
  




  private String createDate()
  {
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String data = df.format(new Date());
    return data;
  }
}
