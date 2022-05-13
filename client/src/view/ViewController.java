package view;

import javafx.scene.layout.Region;
import viewmodel.ViewModelFactory;

public abstract class ViewController {
  private Region root;
  private ViewHandler viewHandler;
  private ViewModelFactory viewModelFactory;

  protected abstract void init(); //binding is here

  /**
   * method for initializing all variables
   *
   * @param viewHandler      viewhander to set the viewhandler variable to
   * @param viewModelFactory viewmodelfactory to set teh viewmodelfactory variable to
   * @param root             root to set the root to
   */
  public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Region root) {
    this.root = root;
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    init();
  }

  public void reset() {
    // ?
  }

  /**
   * method for getting the root
   *
   * @return returns root
   */
  public Region getRoot() {
    return root;
  }

  /**
   * method for getting viewmodelfactory
   *
   * @return returns viewModelFactory
   */
  public ViewModelFactory getViewModelFactory() {
    return viewModelFactory;
  }

  /**
   * method for getting viewHandler
   *
   * @return returns ViewHandler
   */
  public ViewHandler getViewHandler() {
    return viewHandler;
  }
}
