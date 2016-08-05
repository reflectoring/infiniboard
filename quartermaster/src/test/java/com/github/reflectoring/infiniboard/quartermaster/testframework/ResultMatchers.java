package com.github.reflectoring.infiniboard.quartermaster.testframework;

import org.springframework.hateoas.PagedResources;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Collection of ResultMatchers to be used in MockMvc's fluent API.
 */
public class ResultMatchers {

    private ResultMatchers() {

    }

    /**
     * Tests if the response contains the JSON representation of an object of the given type.
     *
     * @param clazz
     *         the class of the expected object.
     * @param <T>
     *         the type of the expected object.
     *
     * @return ResultMatcher that performs the test described above.
     */
    public static <T> ResultMatcher containsResource(Class<T> clazz) {
        return result -> {
            String json = result.getResponse().getContentAsString();
            try {
                T object = JsonHelper.fromJson(json, clazz);
                assertThat(object).isNotNull();
            } catch (Exception e) {
                throw new RuntimeException(
                        String.format("expected JSON representation of class %s but found '%s'", clazz, json), e);
            }
        };
    }

    /**
     * Tests if the response contains the JSON representation of a paging result with items of the given type.
     *
     * @param clazz
     *         the class of the expected paged objects.
     * @param <T>
     *         the type of the expected paged objects.
     *
     * @return ResultMatcher that performs the test described above.
     */
    public static <T> ResultMatcher containsPagedResources(Class<T> clazz) {
        return result -> {
            String json = result.getResponse().getContentAsString();
            try {
                PagedResources<T> pagedResources = JsonHelper.fromPagedResourceJson(json, clazz);
                assertThat(pagedResources).isNotNull();
            } catch (Exception e) {
                throw new RuntimeException(
                        String.format("expected JSON representation of class %s but found '%s'", clazz, json), e);
            }
        };
    }

}
