package io.nethermind.Utils.enums;

public enum FileTypes {
  PDF("application/pdf"),
  CSV("text/csv"),
  JSON("application/json"),
  MP4("video/mp4"),
  JPEG("image/jpeg"),
  XML("application/xml"),
  PNG("image/png"),
  HTML("text/html"),
  TXT("text/plain"),
  JPG("image/jpg");
  private String value;

  private FileTypes(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}


