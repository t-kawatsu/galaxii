package com.galaxii.common.service.dig_i;

import static java.lang.String.format;

import org.apache.commons.lang3.StringUtils;

public final class Parameter {
  /**
   * Parameter name.
   */
  public final String name;

  /**
   * Parameter value.
   */
  public final String value;

  private Parameter(String name, Object value) {
    if (name == null || "".equals(name) || value == null)
      throw new IllegalArgumentException(Parameter.class + " instances must have a non-blank name and non-null value.");

    this.name = StringUtils.trim(name).toLowerCase();

    this.value = (value).toString();
  }

  public static Parameter with(String name, Object value) {
    return new Parameter(name, value);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!getClass().equals(obj.getClass()))
      return false;

    Parameter other = (Parameter) obj;

    if (this.name != other.name && (!this.name.equals(other.name)))
      return false;
    if (this.value != other.value && (!this.value.equals(other.value)))
      return false;

    return true;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + this.name.hashCode();
    hash = 41 * hash + this.value.hashCode();
    return hash;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return format("Parameter[%s=%s]", name, value);
  }
}