package com.github.yjgbg.jpa.plus.entitySupport;

import com.github.yjgbg.jpa.plus.JpaPlusSupport;

/**
 * 支持删除和保存
 * @author weicl
 * @param <Self>
 */
public interface ActiveEntity<Self> {

  /**
   * 保存该实体
   * @return 返回该实体，如果是新建，那么返回对象包含有自生成ID
   */
  @SuppressWarnings("unchecked")
  default Self save() {
    return (Self) JpaPlusSupport.SELF.save(this);
  }

  /**
   * 删除目标该实体
   */
  default void remove() {
    JpaPlusSupport.SELF.remove(this);
  }
}
