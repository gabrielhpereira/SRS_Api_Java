package com.api.srs.shared;

public class GenericLog {
  public static String updateEntity(Object newObject, Object oldObject, String completeDescription) {
    return newObject.equals(oldObject)
        ? "" : completeDescription + " changed from " + oldObject + " to " + newObject + "/n";
  }
}
