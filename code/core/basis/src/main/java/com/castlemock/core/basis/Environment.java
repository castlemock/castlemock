package com.castlemock.core.basis;

import java.io.InputStream;
import java.io.OutputStream;

public interface Environment {

    boolean copy(InputStream inputStream, OutputStream outputStream);

    String getServerInfo();

    String getServerBuilt();

    String getServerNumber();

}
