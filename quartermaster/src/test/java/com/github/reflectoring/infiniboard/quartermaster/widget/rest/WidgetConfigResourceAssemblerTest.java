package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.quartermaster.testframework.ControllerTestTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class WidgetConfigResourceAssemblerTest extends ControllerTestTemplate {

  @Test
  public void toResourceMasksUsername() {
    WidgetConfigResourceAssembler assembler = createAssembler();
    WidgetConfigResource resource = assembler.toResource(createWidgetWithConfiguredUsername());

    String username = extractConfigDataKey(resource, "username", String.class);

    assertThat(username).isEqualTo("**********");
  }

  private WidgetConfigResourceAssembler createAssembler() {
    return new WidgetConfigResourceAssembler("test-dashboard");
  }

  private WidgetConfig createWidgetWithConfiguredUsername() {
    Map<String, Object> configData = createConfigData();
    configData = addUsernameToConfigData(configData, "infiniboard");

    return createWidgetWithConfigData(configData);
  }

  private Map<String, Object> createConfigData() {
    HashMap<String, Object> configData = new HashMap<>();
    configData.put("url", "https://ci.jenkins.io/job/Infrastructure/api/json");
    return configData;
  }

  private Map<String, Object> addUsernameToConfigData(
      Map<String, Object> configData, String username) {
    HashMap<String, Object> newConfigData = new HashMap<>(configData);

    newConfigData.put("username", username);
    return newConfigData;
  }

  private WidgetConfig createWidgetWithConfigData(Map<String, Object> configData) {
    WidgetConfig widgetConfig = createWidget();
    List<SourceConfig> sourceConfigs = createSourceConfigs();
    SourceConfig sourceConfig = createSourceConfig();

    sourceConfig.setConfigData(configData);

    sourceConfigs.add(sourceConfig);
    widgetConfig.setSourceConfigs(sourceConfigs);

    return widgetConfig;
  }

  private WidgetConfig createWidget() {
    WidgetConfig widgetConfig = new WidgetConfig("jenkins prod");
    widgetConfig.setId("http://widget");
    widgetConfig.setType("jenkins");
    return widgetConfig;
  }

  private List<SourceConfig> createSourceConfigs() {
    return new ArrayList<>();
  }

  private SourceConfig createSourceConfig() {
    SourceConfig sourceConfig = new SourceConfig();
    sourceConfig.setType("urlSource");
    sourceConfig.setInterval(10000);

    return sourceConfig;
  }

  private <T> T extractConfigDataKey(WidgetConfigResource resource, String key, Class<T> clazz) {
    Map<String, Object> configData = resource.getSourceConfigs().get(0).getConfigData();

    Object o = configData.get(key);
    return clazz.cast(o);
  }

  @Test
  public void toResourceMasksPasswords() {

    WidgetConfigResourceAssembler assembler = createAssembler();
    WidgetConfigResource resource = assembler.toResource(createWidgetWithConfiguredPassword());

    String password = extractConfigDataKey(resource, "password", String.class);

    assertThat(password).isEqualTo("**********");
  }

  private WidgetConfig createWidgetWithConfiguredPassword() {
    Map<String, Object> configData = createConfigData();
    configData = addPasswordToConfigData(configData, "secret");

    return createWidgetWithConfigData(configData);
  }

  private Map<String, Object> addPasswordToConfigData(
      Map<String, Object> configData, String password) {
    HashMap<String, Object> newConfigData = new HashMap<>(configData);

    newConfigData.put("password", password);
    return newConfigData;
  }

  @Test
  public void toResourceMasksUsernameAndPassword() {

    WidgetConfigResourceAssembler assembler = createAssembler();
    WidgetConfigResource resource =
        assembler.toResource(createWidgetWithConfiguredUsernameAndPassword());

    String username = extractConfigDataKey(resource, "username", String.class);
    String password = extractConfigDataKey(resource, "password", String.class);

    assertThat(username).isEqualTo("**********");
    assertThat(password).isEqualTo("**********");
  }

  private WidgetConfig createWidgetWithConfiguredUsernameAndPassword() {
    Map<String, Object> configData = createConfigData();
    configData = addUsernameToConfigData(configData, "infiniboard");
    configData = addPasswordToConfigData(configData, "secret");

    return createWidgetWithConfigData(configData);
  }
}
