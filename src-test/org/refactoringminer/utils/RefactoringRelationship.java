package org.refactoringminer.utils;

import java.util.EnumSet;

import org.refactoringminer.api.RefactoringType_RENAMED;

public class RefactoringRelationship implements Comparable<RefactoringRelationship> {

  private static final EnumSet<RefactoringType_RENAMED> typesWithMainEntityAfter = EnumSet.of(
    RefactoringType_RENAMED.EXTRACT_AND_MOVE_OPERATION,
    RefactoringType_RENAMED.EXTRACT_INTERFACE,
    RefactoringType_RENAMED.EXTRACT_OPERATION,
    RefactoringType_RENAMED.EXTRACT_SUPERCLASS,
    RefactoringType_RENAMED.PULL_UP_ATTRIBUTE,
    RefactoringType_RENAMED.PULL_UP_OPERATION
  );
  
  private final RefactoringType_RENAMED refactoringType;
  private final String entityBefore;
  private final String entityAfter;

  public RefactoringRelationship(RefactoringType_RENAMED refactoringType, String entityBefore, String entityAfter) {
    if (refactoringType == null || entityBefore == null || entityAfter == null) {
      throw new IllegalArgumentException("arguments should not be null");
    }
    this.refactoringType = refactoringType;
    this.entityBefore = normalize(entityBefore).trim();
    this.entityAfter = normalize(entityAfter).trim();
  }

  public RefactoringType_RENAMED getRefactoringType() {
    return refactoringType;
  }

  public String getEntityBefore() {
    return entityBefore;
  }

  public String getEntityAfter() {
    return entityAfter;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof RefactoringRelationship) {
      RefactoringRelationship other = (RefactoringRelationship) obj;
      return other.refactoringType.equals(this.refactoringType) && other.entityBefore.equals(this.entityBefore) && other.entityAfter.equals(this.entityAfter);
    }
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + entityAfter.hashCode();
    result = prime * result + entityBefore.hashCode();
    result = prime * result + refactoringType.hashCode();
    return result;
  }
  
  @Override
  public String toString() {
    return String.format("%s\t%s\t%s", refactoringType.getDisplayName(), entityBefore, entityAfter);
  }

  public static String normalize(String entity) {
    return normalizeParameters(stripTypeArguments(entity).replace('#', '.')).replace(" ", "");
  }

  public static String parentOf(String entity) {
      return entity.substring(0, entity.lastIndexOf('.'));
  }

  private static String normalizeParameters(String r) {
    int indexOfPar = r.indexOf('(');
    if (indexOfPar != -1) {
      String paramsS = r.substring(indexOfPar + 1, r.indexOf(')'));
      String[] paramsA = paramsS.split("\\s*,\\s*");
      if (paramsA.length == 0 || paramsA[0].isEmpty()) {
        return r;
      }
      for (int i = 0; i < paramsA.length; i++) {
        if (paramsA[i].indexOf(' ') != -1) {
          // strip parameter name
          paramsA[i] = paramsA[i].substring(0, paramsA[i].indexOf(' '));
        }
        if (paramsA[i].lastIndexOf('.') != -1) {
          // strip qualified type name
          paramsA[i] = paramsA[i].substring(paramsA[i].lastIndexOf('.') + 1);
        }
        paramsA[i] = paramsA[i].substring(Math.max(paramsA[i].lastIndexOf('.') + 1, 0));
      }
      r = r.substring(0, indexOfPar) + "(" + String.join(",", paramsA) + ")";
    }
    return r;
  }

  private static String stripTypeArguments(String entity) {
    StringBuilder sb = new StringBuilder();
    int openGenerics = 0;
    for (int i = 0; i < entity.length(); i++) {
      char c = entity.charAt(i);
      if (c == '<') {
        openGenerics++;
      }
      if (openGenerics == 0) {
        sb.append(c);
      }
      if (c == '>') {
        openGenerics--;
      }
    }
    return sb.toString();
  }

  public String getMainEntity() {
    if (typesWithMainEntityAfter.contains(refactoringType)) {
      return entityAfter;
    }
    return entityBefore;
  }

  public String getSecondaryEntity() {
    if (typesWithMainEntityAfter.contains(refactoringType)) {
      return entityBefore;
    }
    return entityAfter;
  }
  
  @Override
  public int compareTo(RefactoringRelationship o) {
    int rt = getRefactoringType().compareTo(o.getRefactoringType());
    int cm = getMainEntity().compareTo(o.getMainEntity());
    int cs = getSecondaryEntity().compareTo(o.getSecondaryEntity());
    int ct = refactoringType.compareTo(o.refactoringType);
    return rt != 0 ? rt : cm != 0 ? cm : cs != 0 ? cs : ct;
  }

  public GroupKey getGroupKey() {
    return new GroupKey(refactoringType, getMainEntity());
  }

  public static class GroupKey implements Comparable<GroupKey> {
    private final RefactoringType_RENAMED refactoringType;
    private final String mainEntity;

    public GroupKey(RefactoringType_RENAMED refactoringType, String mainEntity) {
      this.refactoringType = refactoringType;
      this.mainEntity = mainEntity;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof GroupKey) {
        GroupKey other = (GroupKey) obj;
        return other.refactoringType.equals(this.refactoringType) && other.mainEntity.equals(this.mainEntity);
      }
      return false;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + mainEntity.hashCode();
      result = prime * result + refactoringType.hashCode();
      return result;
    }

    @Override
    public int compareTo(GroupKey o) {
      int rt = refactoringType.compareTo(o.refactoringType);
      int cm = mainEntity.compareTo(o.mainEntity);
      int ct = refactoringType.compareTo(o.refactoringType);
      return rt != 0 ? rt : cm != 0 ? cm : ct;
    }
    
    @Override
    public String toString() {
      return String.format("%s\t%s", refactoringType.getDisplayName(), mainEntity);
    }
  }
}
