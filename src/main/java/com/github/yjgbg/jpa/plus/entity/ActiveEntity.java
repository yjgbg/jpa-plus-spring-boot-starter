package com.github.yjgbg.jpa.plus.entity;

import com.github.yjgbg.jpa.plus.config.JpaPlusConfiguration;

public interface ActiveEntity<A extends ActiveEntity<A>> {
  @SuppressWarnings("unchecked")
  default A save() {
    return (A) JpaPlusConfiguration.self().save(this);
  }

  default void remove() {
    JpaPlusConfiguration.self().remove(this);
  }
}
