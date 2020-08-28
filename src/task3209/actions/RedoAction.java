package task3209.actions;


import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import task3209.View;

public class RedoAction extends AbstractAction {

  private View view;

  public RedoAction(View view) {
    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    view.redo();
  }

  public boolean accept(Object sender) {
    return false;
  }
}