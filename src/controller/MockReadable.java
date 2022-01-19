package controller;

import java.io.IOException;
import java.nio.CharBuffer;

/**
 * An implementation of Readable that always throws an IOException.
 */
public class MockReadable implements Readable {
  /**
   * Throws an IOException unconditionally to mock a read failure.
   *
   * @param cb              the buffer where the characters will be read
   * @return                nothing because IOException is thrown
   * @throws IOException    always
   */
  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException("executing controller.MockReadable.read()");
  }
}
