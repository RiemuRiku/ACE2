package fxAce.util;

import java.io.PrintStream;
import javafx.scene.input.ClipboardContent;


public class Clipboard
{
  public Clipboard() {}
  
  public static String getClipboard()
  {
    try
    {
      return javafx.scene.input.Clipboard.getSystemClipboard().getString();
    }
    catch (Exception e)
    {
      System.err.println("Virhe:  " + e.getMessage());
    }
    
    return "";
  }
  



  public static void setClipboard(String str)
  {
    try
    {
      ClipboardContent content = new ClipboardContent();
      content.putString(str);
      
      javafx.scene.input.Clipboard.getSystemClipboard().setContent(content);
    }
    catch (Exception e)
    {
      System.err.println("Leikepöydän asetus ei onnistunu: " + e.getMessage());
    }
  }
}
