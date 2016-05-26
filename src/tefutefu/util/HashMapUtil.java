package tefutefu.util;

/**
 * Created by alphakai on 2016/05/26.
 */

import java.util.HashMap;
import java.util.ArrayList;

public class HashMapUtil<S, T> {
  public ArrayList<S> getKeys(HashMap<S, T> hash) {
    ArrayList<S> keys = new ArrayList<S>();

    if (hash != null) {
      for (HashMap.Entry<S, T> entry : hash.entrySet()) {
        keys.add(entry.getKey());
      }
    }

    return keys;
  }

  public boolean existValue(HashMap<S, T> hash, S key) {
    return this.getKeys(hash).contains(key);
  }
}
