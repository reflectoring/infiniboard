package com.github.reflectoring.infiniboard.quartermaster.info.rest;

import org.springframework.hateoas.ResourceSupport;

public class InfoResource extends ResourceSupport {

  private String version;

  public String getVersion() {
    return version;
  }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean equals(Object that) {
        if (!super.equals(that)) {
            return false;
        }

        InfoResource thatResource = (InfoResource) that;
        if (version.equals(thatResource.getVersion())) {
            return true;
        }
        return false;
    }
}
