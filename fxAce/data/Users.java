package fxAce.data;



public class Users
{
  private int lkm;
  

  private int capacity;
  
  private User[] users;
  

  public Users()
  {
    lkm = 0;
    capacity = 8;
    users = new User[capacity];
  }
  




  public User[] getUsers()
  {
    return users;
  }
  




  public int getLkm()
  {
    return lkm;
  }
  




  public void clear()
  {
    lkm = 0;
  }
  





  public boolean userExists(String username)
  {
    for (int i = 0; i < lkm; i++) {
      if (users[i].getMasterUsername().equals(username)) {
        return true;
      }
    }
    
    return false;
  }
  




  public void addUser(String row)
    throws Exception
  {
    User new_user = new User(row);
    
    if (lkm >= capacity) {
      capacity *= 2;
      

      User[] temp_users = new User[capacity];
      

      for (int i = 0; i < users.length; i++) {
        temp_users[i] = users[i];
      }
      

      users = temp_users;
    }
    
    users[lkm] = new_user;
    lkm += 1;
  }
  




  public void addUser(User user)
    throws Exception
  {
    if (lkm >= capacity) {
      capacity *= 2;
      

      User[] temp_users = new User[capacity];
      

      for (int i = 0; i < users.length; i++) {
        temp_users[i] = users[i];
      }
      

      users = temp_users;
    }
    
    users[lkm] = user;
    lkm += 1;
  }
}
