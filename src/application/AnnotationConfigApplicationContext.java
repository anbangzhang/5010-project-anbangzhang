package application;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import application.annotation.Autowired;
import application.annotation.Component;
import application.annotation.Qualifier;
import application.exception.CustomException;

/**
 * AnnotationConfigApplicationContext.
 * 
 * @author anbang
 * @date 2023-03-19 18:09
 */
public class AnnotationConfigApplicationContext implements ApplicationContext {
  /**
   * Project path.
   */
  private String projectPath = this.getClass().getResource("/").getPath();
  /**
   * All classes.
   */
  private List<Class> clazzList;
  /**
   * File paths.
   */
  private List<String> filePaths;
  /**
   * All component annotated classes.
   */
  private Map<String, Class> existComponentClassMap;
  /**
   * All qualifier annotated classes' values.
   */
  private Map<String, Class> existComponentValueClassMap;

  /**
   * Constructor.
   * 
   * @param packages packages
   */
  public AnnotationConfigApplicationContext(String... packages) {
    clazzList = new ArrayList<>();
    existComponentClassMap = new HashMap<>();
    existComponentValueClassMap = new HashMap<>();
    for (String tempPackageName : packages) {
      filePaths = getFileName(projectPath + (tempPackageName.replaceAll("[.]", "/")));
      try {
        clazzList.addAll(getFileClass(filePaths));
        isComponent();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public Object getBean(Class clazz) {
    try {
      if (existComponentClassMap.get(clazz.getName()) != null) {
        return isAutowired(clazz);
      } else {
        throw new CustomException("not found " + clazz.getName() + " mapping class");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private void isComponent() {
    for (Class tempClass : clazzList) {
      if (tempClass.isAnnotationPresent(Component.class)) {
        existComponentClassMap.put(tempClass.getName(), tempClass);
        Component component = (Component) tempClass.getAnnotation(Component.class);
        String componentValue = component.value();
        if (componentValue.length() > 0) {
          existComponentValueClassMap.put(componentValue, tempClass);
        }
      }
    }
  }

  private Object isAutowired(Class clazz) throws Exception {
    Object object = clazz.getDeclaredConstructor().newInstance();
    Field fields[] = clazz.getDeclaredFields();

    for (Field field : fields) {
      if (field.isAnnotationPresent(Autowired.class)) {
        if (field.isAnnotationPresent(Qualifier.class)) {
          Qualifier qualifier = field.getAnnotation(Qualifier.class);
          Class Tempclazz = existComponentValueClassMap.get(qualifier.value());
          if (Tempclazz != null) {
            field.setAccessible(true);
            field.set(object, isAutowired(Tempclazz));
          } else {
            throw new CustomException("not found " + qualifier.value() + " mapping class");
          }
        } else {
          Class fieldType = field.getType();
          Class Tempclazz = existComponentClassMap.get(fieldType.getName());
          if (Tempclazz != null) {
            field.setAccessible(true);
            field.set(object, isAutowired(Tempclazz));
          } else {
            throw new CustomException("not found " + fieldType.getName() + " mapping class");
          }
        }
      }
    }
    return object;
  }

  private List<String> getFileName(String packagePath) {
    List<String> filePaths = new ArrayList<>();
    String filePath = packagePath;
    File file = new File(filePath);
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (File file2 : files) {
        if (file2.isDirectory()) {
          filePaths.addAll(getFileName(file2.getPath()));
        } else {
          if (file2.getName().substring(file2.getName().lastIndexOf(".") + 1).equals("class")) {
            filePaths.add(file2.getPath());
          }
        }
      }
    }
    return filePaths;
  }

  private List<Class> getFileClass(List<String> filePath) throws ClassNotFoundException {
    List<Class> list = new ArrayList<Class>();
    for (String tempFileName : filePath) {
      String tempClassName = tempFileName.substring(projectPath.length() - 1);
      tempClassName = tempClassName.replaceAll("\\\\", ".");
      list.add(Class.forName(tempClassName.substring(0, tempClassName.lastIndexOf("."))));
    }
    return list;
  }

}
