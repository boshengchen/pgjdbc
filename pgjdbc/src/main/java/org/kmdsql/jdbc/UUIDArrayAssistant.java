/*
 * Copyright (c) 2004, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.kmdsql.jdbc;

import org.kmdsql.jdbc2.ArrayAssistant;
import org.kmdsql.util.ByteConverter;

import java.util.UUID;

public class UUIDArrayAssistant implements ArrayAssistant {
  @Override
  public Class<?> baseType() {
    return UUID.class;
  }

  @Override
  public Object buildElement(byte[] bytes, int pos, int len) {
    return new UUID(ByteConverter.int8(bytes, pos + 0), ByteConverter.int8(bytes, pos + 8));
  }

  @Override
  public Object buildElement(String literal) {
    return UUID.fromString(literal);
  }
}
