package com.mvp1.whatiread.utils;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
public class Utils {

  // Constants
  public static final String USER_ROLE_NOT_SET = "User role not set";

  // External APIs
  public static final String IMAGE_SEARCH_URL = "https://covers.openlibrary.org/b/isbn/";

  // Internal API Paths
  public static final String AUTH_BASE_PATH = "/api/auth";
  public static final String SIGN_IN_PATH = "/signin";
  public static final String SIGN_UP_PATH = "/signup";
  public static final ModelMapper modelMapper = new ModelMapper();
}
