package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.util.HashMap;
import java.util.Map;

public abstract class ViewCreator
{
  private Map<String, ViewController> map;

  public ViewCreator()
  {
    this.map = new HashMap<>();
  }

  /**
   * method for getting view controller with given id
   *
   * @param id id of viewController
   * @return returns the viewcontroller with given id
   */
  public ViewController getViewController(String id)
  {
    ViewController controller = map.get(id);
    if (controller == null)
    {
      controller = loadFromFXML(id);
      map.put(id, controller);
    }
    controller.reset();
    return controller;
  }

  protected abstract void initViewController(ViewController controller,
      Region root);

  /**
   * method for loading fxml files
   *
   * @param fxmlFile fxmlFile to load
   * @return returns viewController
   */
  public ViewController loadFromFXML(String fxmlFile)
  {
    ViewController controller = null;
    try
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource(fxmlFile));
      Region root = loader.load();
      controller = loader.getController();
      initViewController(controller, root);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return controller;
  }
}
