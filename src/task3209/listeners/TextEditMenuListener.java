package task3209.listeners;

import java.awt.Component;
import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import task3209.View;

public class TextEditMenuListener implements MenuListener {

  private View view;

  public TextEditMenuListener(View view) {
    this.view = view;
  }


  @Override
  public void menuSelected(MenuEvent menuEvent) {
    JMenu jMenu = (JMenu) menuEvent.getSource();
    for (Component c : jMenu.getMenuComponents()) {
      c.setEnabled(view.isHtmlTabSelected());
    }
  }

  @Override
  public void menuDeselected(MenuEvent menuEvent) {

  }

  @Override
  public void menuCanceled(MenuEvent e) {

  }
}
