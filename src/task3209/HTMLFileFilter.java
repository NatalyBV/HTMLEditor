package task3209;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class HTMLFileFilter extends FileFilter {

  @Override
  public boolean accept(File f) {
    return f.isDirectory() || f.getName().toLowerCase().endsWith(".html") || f.getName()
        .toLowerCase().endsWith(".htm");
  }

  @Override
  public String getDescription() {
    return "HTML и HTM файлы";
  }
}