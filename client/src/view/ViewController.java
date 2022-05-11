package view;

import javafx.scene.layout.Region;
import viewmodel.ViewModelFactory;

public abstract class ViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private ViewModelFactory viewModelFactory;

  protected abstract void init(); //binding is here

  public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Region root) {
    this.root = root;
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    init();
  }

  public void reset() {
    // ?
  }

  public Region getRoot() {
    return root;
  }

  public ViewModelFactory getViewModelFactory() {
    return viewModelFactory;
  }

  public ViewHandler getViewHandler() {
    return viewHandler;
  }
}
