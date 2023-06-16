package org.apache.hadoop.fs.qiniu.kodo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.hadoop.fs.RemoteIterator;

public final class RemoteIteratorUtils {
  public static <T> List<T> toList(RemoteIterator<T> it) throws IOException {
    List<T> list = new ArrayList<>();
    while (it.hasNext()) {
      list.add(it.next());
    }
    return list;
  }
  public static <T> T[] toArray(RemoteIterator<T> it, T[] array) throws IOException {
    return toList(it).toArray(array);
  }

  public static <T> RemoteIterator<T> remoteIteratorFromSingleton(T e) {
    return new RemoteIterator<T>() {
      private boolean hasNext = true;

      @Override
      public boolean hasNext() {
        return hasNext;
      }

      @Override
      public T next() {
        if (hasNext) {
          hasNext = false;
          return e;
        } else {
          throw new NoSuchElementException();
        }
      }
    };
  }

  public interface MappingFunction<I, O> {
    O apply(I input);
  }

  public static <I, O> RemoteIterator<O> mappingRemoteIterator(RemoteIterator<I> it, MappingFunction<I, O> mappingFunction) {
    return new RemoteIterator<O>() {
      @Override
      public boolean hasNext() throws IOException {
        return it.hasNext();
      }

      @Override
      public O next() throws IOException {
        return mappingFunction.apply(it.next());
      }
    };
  }

  public static <T> RemoteIterator<T> remoteIteratorFromIterable(Iterable<T> iterable) {
    return new RemoteIterator<T>() {
      private final Iterator<T> iterator = iterable.iterator();

      @Override
      public boolean hasNext() throws IOException {
        return iterator.hasNext();
      }

      @Override
      public T next() throws IOException {
        return iterator.next();
      }
    };
  }
}
