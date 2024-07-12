package com.mvp1.whatiread.utils;

public enum RequestStatus {
  PENDING("Pending"),
  ACCEPTED("Accepted"),
  REJECTED("Rejected"),
  REMOVE("Remove");

  private final String displayName;

  RequestStatus(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}