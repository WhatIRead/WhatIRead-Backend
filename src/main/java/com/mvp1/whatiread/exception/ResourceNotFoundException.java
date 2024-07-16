package com.mvp1.whatiread.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mvp1.whatiread.dto.ApiResponse;
import java.io.Serial;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;
  private String resourceName;
  private String fieldName;
  private Object fieldValue;
  private transient ApiResponse apiResponse;
  private List<Resource> resources;

  public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
    super();
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
    String message = String.format("%s not found with %s: '%s'", resourceName, fieldName,
        fieldValue);
    apiResponse = new ApiResponse(Boolean.FALSE, message);
  }

  public ResourceNotFoundException(List<Resource> resources) {
    super();
    this.resources = resources;
    String message = String.format("Following resources not found \n%s", resources);
    apiResponse = new ApiResponse(Boolean.FALSE, message);
  }
}
