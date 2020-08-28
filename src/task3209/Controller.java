package task3209;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import javax.swing.JFileChooser;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import task3209.listeners.UndoListener;

public class Controller {

  private View view;
  private HTMLDocument document;
  private File currentFile;

  public Controller(View view) {
    this.view = view;
  }

  public HTMLDocument getDocument() {
    return document;
  }

  public void init() {
    createNewDocument();
  }

  public void exit() {
    System.exit(0);
  }

  public void resetDocument() {
    UndoListener listener = view.getUndoListener();
    if (document != null) {
      document.removeUndoableEditListener(listener);
    }
    document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
    document.addUndoableEditListener(listener);
    view.update();
  }

  public static void main(String[] args) {
    View view = new View();
    Controller controller = new Controller(view);
    view.setController(controller);
    view.init();
    controller.init();
  }

  public void setPlainText(String text) {
    resetDocument();
    try {
      new HTMLEditorKit().read(new StringReader(text), document, document.getLength());
    } catch (Exception e) {
      ExceptionHandler.log(e);
    }
  }

  public String getPlainText() {
    StringWriter stringWriter = new StringWriter();
    try {
      new HTMLEditorKit().write(stringWriter, document, 0, document.getLength());
    } catch (Exception e) {
      ExceptionHandler.log(e);
    }
    return stringWriter.toString();
  }

  public void createNewDocument() {
    view.selectHtmlTab();
    resetDocument();
    view.setTitle("HTML редактор");
    view.resetUndo();
    currentFile = null;
  }

  public void openDocument() {
    try {
      view.selectHtmlTab();
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(new HTMLFileFilter());
      fileChooser.setDialogTitle("Open File");
      int result = fileChooser.showOpenDialog(view);

      if (result == 0) {

        currentFile = fileChooser.getSelectedFile();
        resetDocument();
        view.setTitle(currentFile.getName());

        FileReader reader = new FileReader(currentFile);
        new HTMLEditorKit().read(reader, document, 0);
        view.resetUndo();
      }
    } catch (Exception e) {
      ExceptionHandler.log(e);
    }
  }

  public void saveDocument() {
    view.selectHtmlTab();
    if (currentFile == null) {
      saveDocumentAs();
    } else {
      try {
        view.setTitle(currentFile.getName());
        FileWriter writer = new FileWriter(currentFile);
        new HTMLEditorKit().write(writer, document, 0, document.getLength());
      } catch (Exception e) {
        ExceptionHandler.log(e);
      }
    }
  }

  public void saveDocumentAs() {
    try {
      view.selectHtmlTab();
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(new HTMLFileFilter());
      fileChooser.setDialogTitle("Save File");
      int result = fileChooser.showSaveDialog(view);

      if (result == 0) {

        currentFile = fileChooser.getSelectedFile();
        view.setTitle(currentFile.getName());

        FileWriter writer = new FileWriter(currentFile);
        new HTMLEditorKit().write(writer, document, 0, document.getLength());
      }
    } catch (Exception e) {
      ExceptionHandler.log(e);
    }
  }
}
