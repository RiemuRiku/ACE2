package fxAce.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;




public class Date
{
  public Date() {}
  
  public static String createDate()
  {
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    return df.format(new java.util.Date());
  }
}
