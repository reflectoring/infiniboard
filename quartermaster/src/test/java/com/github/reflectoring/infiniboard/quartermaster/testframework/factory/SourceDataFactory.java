package com.github.reflectoring.infiniboard.quartermaster.testframework.factory;

import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceDataFactory {

  public static List<SourceData> sourceDataList() {
    List<SourceData> list = new ArrayList<>();
    list.add(new SourceData("my_little_widget", "source_1", dataMap()));
    list.add(new SourceData("my_little_widget", "source_2", dataMap()));
    list.add(new SourceData("my_little_widget", "source_3", dataMap()));
    return list;
  }

  private static Map<String, Object> dataMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    return map;
  }
}
