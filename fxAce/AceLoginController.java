package fxAce;

import fi.jyu.mit.fxgui.Dialogs;
import fxAce.data.DatabaseSQLite;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;





public class AceLoginController
{
  @FXML
  private PasswordField passwordField;
  @FXML
  private TextField usernameField;
  @FXML
  private Button loginButton;
  @FXML
  private Button registerButton;
  private DatabaseSQLite db;
  private Scene gui_scene;
  private AceGUIController gui_ctrl;
  private int wrong_attempts;
  
  public AceLoginController() { wrong_attempts = 0; }
  
  @FXML
  void handleLogin() throws Exception {
    String username = usernameField.getText();
    String password = passwordField.getText();
    

    boolean credsOk = db.checkUserCreds(username, password);
    


    if (!credsOk) {
      wrong_attempts += 1;
      Dialogs.showMessageDialog("Väärä käyttäjätunnus tai salasana (" + wrong_attempts + ". kerta)");
      
      return;
    }
    
    wrong_attempts = 0;
    setMasterCreds();
    

    Stage stage = (Stage)loginButton.getScene().getWindow();
    stage.setScene(gui_scene);
  }
  

  @FXML
  void handleRegister()
  {
    String username = usernameField.getText().trim();
    String password = passwordField.getText().trim();
    
    if ((username.isEmpty()) || (password.isEmpty())) {
      Dialogs.showMessageDialog("Täytä molemmat kentät");
      return;
    }
    try
    {
      db.addMasterUser(username, password);
      Dialogs.showMessageDialog("Käyttäjätunnus \"" + username + "\" luotu");
    }
    catch (Exception e)
    {
      Dialogs.showMessageDialog(e.getMessage());
    }
  }
  




  public void setDatabase(DatabaseSQLite db)
  {
    this.db = db;
  }
  




  public void setGUIScene(Scene gui_scene)
  {
    this.gui_scene = gui_scene;
  }
  





  public void setGUICtrl(AceGUIController gui_ctrl)
  {
    this.gui_ctrl = gui_ctrl;
  }
  




  public void setMasterCreds()
  {
    gui_ctrl.setMasterCreds(usernameField.getText(), passwordField.getText());
  }
}
