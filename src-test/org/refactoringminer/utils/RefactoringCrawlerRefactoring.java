package org.refactoringminer.utils;

import java.util.HashMap;
import java.util.Map;

import org.refactoringminer.api.RefactoringType_RENAMED;

public class RefactoringCrawlerRefactoring {

  private static final Map<String, RefactoringType_RENAMED> TYPE_MAP;
  static {
    TYPE_MAP = new HashMap<>();
    TYPE_MAP.put("RenamedClasses", RefactoringType_RENAMED.RENAME_CLASS);
    TYPE_MAP.put("RenamedMethods", RefactoringType_RENAMED.RENAME_METHOD);
    TYPE_MAP.put("PulledUpMethods", RefactoringType_RENAMED.PULL_UP_OPERATION);
    TYPE_MAP.put("PushedDownMethods", RefactoringType_RENAMED.PUSH_DOWN_OPERATION);
    TYPE_MAP.put("MovedMethods", RefactoringType_RENAMED.MOVE_OPERATION);
    TYPE_MAP.put("ChangedMethodSignatures", RefactoringType_RENAMED.CHANGE_METHOD_SIGNATURE);
    TYPE_MAP.put("RenamedPackages", RefactoringType_RENAMED.RENAME_PACKAGE);
  }

  private String type;
  private String newElement;
  private String oldElement;
  
  public RefactoringCrawlerRefactoring(String type, String newElement, String oldElement) {
    this.type = type.trim();
    this.newElement = newElement.trim();
    this.oldElement = oldElement.trim();
  }

  public String getType() {
    return type;
  }

  public String getNewElement() {
    return newElement;
  }

  public String getOldElement() {
    return oldElement;
  }
  
  @Override
  public String toString() {
    return type + '\t' + oldElement + '\t' + newElement;
  }

  RefactoringRelationship toRefactoringRelationship() {
    RefactoringType_RENAMED refType = TYPE_MAP.get(type);
    if (refType == null) {
      throw new RuntimeException("unkown refactoring type " + type);
    }
    return new RefactoringRelationship(refType, oldElement, newElement);
  }
}
