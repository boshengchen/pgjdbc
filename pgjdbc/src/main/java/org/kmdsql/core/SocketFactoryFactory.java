/*
 * Copyright (c) 2003, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.kmdsql.core;

import org.kmdsql.PGProperty;
import org.kmdsql.ssl.LibPQFactory;
import org.kmdsql.util.GT;
import org.kmdsql.util.ObjectFactory;
import org.kmdsql.util.PSQLException;
import org.kmdsql.util.PSQLState;

import java.util.Properties;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

/**
 * Instantiates {@link SocketFactory} based on the {@link PGProperty#SOCKET_FACTORY}.
 */
public class SocketFactoryFactory {

  /**
   * Instantiates {@link SocketFactory} based on the {@link PGProperty#SOCKET_FACTORY}.
   *
   * @param info connection properties
   * @return socket factory
   * @throws PSQLException if something goes wrong
   */
  public static SocketFactory getSocketFactory(Properties info) throws PSQLException {
    // Socket factory
    String socketFactoryClassName = PGProperty.SOCKET_FACTORY.get(info);
    if (socketFactoryClassName == null) {
      return SocketFactory.getDefault();
    }
    try {
      return (SocketFactory) ObjectFactory.instantiate(socketFactoryClassName, info, true,
          PGProperty.SOCKET_FACTORY_ARG.get(info));
    } catch (Exception e) {
      throw new PSQLException(
          GT.tr("The SocketFactory class provided {0} could not be instantiated.",
              socketFactoryClassName),
          PSQLState.CONNECTION_FAILURE, e);
    }
  }

  /**
   * Instantiates {@link SSLSocketFactory} based on the {@link PGProperty#SSL_FACTORY}.
   *
   * @param info connection properties
   * @return SSL socket factory
   * @throws PSQLException if something goes wrong
   */
  public static SSLSocketFactory getSslSocketFactory(Properties info) throws PSQLException {
    String classname = PGProperty.SSL_FACTORY.get(info);
    if (classname == null
        || "org.kmdsql.ssl.jdbc4.LibPQFactory".equals(classname)
        || "org.kmdsql.ssl.LibPQFactory".equals(classname)) {
      return new LibPQFactory(info);
    }
    try {
      return (SSLSocketFactory) ObjectFactory.instantiate(classname, info, true,
          PGProperty.SSL_FACTORY_ARG.get(info));
    } catch (Exception e) {
      throw new PSQLException(
          GT.tr("The SSLSocketFactory class provided {0} could not be instantiated.", classname),
          PSQLState.CONNECTION_FAILURE, e);
    }
  }

}
