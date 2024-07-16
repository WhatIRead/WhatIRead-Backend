package com.mvp1.whatiread.exception;

import org.json.JSONObject;

public class Resource {

  String resourceName;
  String fieldName;
  Object fieldValue;

  public Resource(String resourceName, String fieldName, Object fieldValue) {
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public String toString() {
    JSONObject res = new JSONObject();
    res.put("resourceName", resourceName);
    res.put("fieldName", fieldName);
    res.put("fieldValue", fieldValue);
    return res.toString();
  }
}
