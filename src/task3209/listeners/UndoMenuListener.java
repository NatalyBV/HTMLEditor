package task3209.listeners;

import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import task3209.View;

public class UndoMenuListener implements MenuListener {

  private View view;
  private JMenuItem undoMenuItem;
  private JMenuItem redoMenuItem;

  public UndoMenuListener(View view, JMenuItem undoMenuItem, JMenuItem redoMenuItem) {
    this.view = view;
    this.undoMenuItem = undoMenuItem;
    this.redoMenuItem = redoMenuItem;
  }

  @Override
  public void menuSelected(MenuEvent e) {
    undoMenuItem.setEnabled(view.canUndo());
    redoMenuItem.setEnabled(view.canRedo());
  }

  @Override
  public void menuDeselected(MenuEvent e) {

  }

  @Override
  public void menuCanceled(MenuEvent e) {

  }
}
