package task3209;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.undo.UndoManager;
import task3209.listeners.FrameListener;
import task3209.listeners.TabbedPaneChangeListener;
import task3209.listeners.UndoListener;

public class View extends JFrame implements ActionListener {

  private JTabbedPane tabbedPane = new JTabbedPane(); // панель с двумя вкладками
  private JTextPane htmlTextPane = new JTextPane(); // компонент для визуального редактирования html
  private JEditorPane plainTextPane = new JEditorPane(); // компонент для редактирования html в виде текста, отображает код html
  private UndoManager undoManager = new UndoManager();
  private UndoListener undoListener = new UndoListener(undoManager);

  public UndoListener getUndoListener() {
    return undoListener;
  }

  public View() throws HeadlessException {

    try {
      UIManager.setLookAndFeel(UIManager.getLookAndFeel());
    } catch (Exception e) {
      ExceptionHandler.log(e);
    }
  }

  public View(GraphicsConfiguration gc, JTabbedPane tabbedPane,
      JTextPane htmlTextPane, JEditorPane plainTextPane,
      Controller controller) {
    super(gc);
    this.tabbedPane = tabbedPane;
    this.htmlTextPane = htmlTextPane;
    this.plainTextPane = plainTextPane;
    this.controller = controller;
  }

  public View(String title, JTabbedPane tabbedPane, JTextPane htmlTextPane,
      JEditorPane plainTextPane, Controller controller) throws HeadlessException {
    super(title);
    this.tabbedPane = tabbedPane;
    this.htmlTextPane = htmlTextPane;
    this.plainTextPane = plainTextPane;
    this.controller = controller;
  }

  public View(String title, GraphicsConfiguration gc, JTabbedPane tabbedPane,
      JTextPane htmlTextPane, JEditorPane plainTextPane,
      Controller controller) {
    super(title, gc);
    this.tabbedPane = tabbedPane;
    this.htmlTextPane = htmlTextPane;
    this.plainTextPane = plainTextPane;
    this.controller = controller;
  }

  private Controller controller;

  public Controller getController() {
    return controller;
  }

  public void setController(Controller controller) {
    this.controller = controller;
  }

  public void init() {
    initGui();
    FrameListener listener = new FrameListener(this);
    addWindowListener(listener); // добавление подписчика
    setVisible(true); // показывать окно
  }

  public void exit() {
    controller.exit();
  }

  public void initMenuBar() {
    JMenuBar jMenuBar = new JMenuBar();
    MenuHelper.initFileMenu(this, jMenuBar);
    MenuHelper.initEditMenu(this, jMenuBar);
    MenuHelper.initStyleMenu(this, jMenuBar);
    MenuHelper.initAlignMenu(this, jMenuBar);
    MenuHelper.initColorMenu(this, jMenuBar);
    MenuHelper.initFontMenu(this, jMenuBar);
    MenuHelper.initHelpMenu(this, jMenuBar);

    getContentPane().add(jMenuBar, BorderLayout.NORTH);
  }

  // метод инициализации панелей
  public void initEditor() {
    htmlTextPane.setContentType("text/html");
    JScrollPane jScrollPane = new JScrollPane(htmlTextPane);
    tabbedPane.addTab("HTML", jScrollPane);
    JScrollPane jScrollPane1 = new JScrollPane(plainTextPane);
    tabbedPane.addTab("Текст", jScrollPane1);
    tabbedPane.setPreferredSize(new Dimension(1000, 1000));
    TabbedPaneChangeListener tabbedPaneChangeListener = new TabbedPaneChangeListener(this);
    tabbedPane.addChangeListener(tabbedPaneChangeListener);
    getContentPane().add(tabbedPane, BorderLayout.CENTER);
  }

  public void initGui() {
    initMenuBar();
    initEditor();
    pack();
  }

  public boolean canUndo() {
    return undoManager.canUndo();
  }

  public boolean canRedo() {
    return undoManager.canRedo();
  }

  public void showAbout() {
    JOptionPane.showMessageDialog(tabbedPane.getSelectedComponent(), "Версия 1.0", "О программе",
        JOptionPane.INFORMATION_MESSAGE);
  }

  public void update() {
    htmlTextPane.setDocument(controller.getDocument());
  }

  public void selectHtmlTab() {
    tabbedPane.setSelectedIndex(0);
    resetUndo();
  }


  public boolean isHtmlTabSelected() {
    return tabbedPane.getSelectedIndex() == 0;
  }

  public void undo() {
    try {
      undoManager.undo();
    } catch (Exception e) {
      ExceptionHandler.log(e);
    }
  }

  public void redo() {
    try {
      undoManager.redo();
    } catch (Exception e) {
      ExceptionHandler.log(e);
    }
  }

  public void selectedTabChanged() {
    if (tabbedPane.getSelectedIndex() == 0) {
      controller.setPlainText(plainTextPane.getText());
    } else if (tabbedPane.getSelectedIndex() == 1) {
      plainTextPane.setText(controller.getPlainText());
    }
    this.resetUndo();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String item = e.getActionCommand();
    switch (item) {
      case "Новый":
        controller.createNewDocument();
        break;
      case "Открыть":
        controller.openDocument();
        break;
      case "Сохранить":
        controller.saveDocument();
        break;
      case "Сохранить как...":
        controller.saveDocumentAs();
        break;
      case "Выход":
        controller.exit();
        break;
      case "О программе":
        showAbout();
        break;
    }
  }

  public void resetUndo() {
    undoManager.discardAllEdits();
  }
}

