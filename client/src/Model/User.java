package Model;

import java.time.LocalDate;

public class User
{
  private String username;
  private String password;
  private boolean isAdmin;

  private String email;
  private String address;
  private String name;
  private LocalDate bday;

  public User(String username, String password)
  {
    this.username = username;
    this.password = password;
    this.email = null;
    this.address = null;
    this.name = null;
    this.bday = null;
    if (username.equals("admin") && password.equals("admin"))
    {
      isAdmin = true;
    }
  }

  public User(String username, String password, String email, String address,
      String name, LocalDate bday)
  {
    this.username = username;
    this.password = password;
    this.email = email;
    this.address = address;
    this.name = name;
    this.bday = bday;
    isAdmin = false;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getEmail()
  {
    return email;
  }

  public String getAddress()
  {
    return address;
  }

  public String getName()
  {
    return name;
  }

  public LocalDate getBday()
  {
    return bday;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setBday(LocalDate bday)
  {
    this.bday = bday;
  }

  public void setAdmin(boolean admin)
  {
    isAdmin = admin;
  }

  
}
