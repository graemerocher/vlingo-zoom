package io.vlingo.micronaut;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.convert.value.ConvertibleMultiValues;
import io.micronaut.core.convert.value.ConvertibleMultiValuesMap;
import io.micronaut.http.MutableHttpParameters;

import java.util.*;
import java.util.stream.Collectors;

@Internal
public class VlingoHttpParameters implements MutableHttpParameters {

    private final LinkedHashMap<CharSequence, List<String>> valuesMap;
    private final ConvertibleMultiValues<String> values;

    /**
     * @param parameters        The parameters
     * @param conversionService The conversion service
     */
    public VlingoHttpParameters(Map<String, List<String>> parameters, ConversionService conversionService) {
        this.valuesMap = new LinkedHashMap<>(parameters.size());
        this.values = new ConvertibleMultiValuesMap<>(valuesMap, conversionService);
        for (Map.Entry<String, List<String>> entry : parameters.entrySet()) {
            valuesMap.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
        }
    }

    @Override
    public Set<String> names() {
        return values.names();
    }

    @Override
    public Collection<List<String>> values() {
        return values.values();
    }

    @Override
    public List<String> getAll(CharSequence name) {
        return values.getAll(name);
    }

    @Override
    public String get(CharSequence name) {
        return values.get(name);
    }

    @Override
    public <T> Optional<T> get(CharSequence name, ArgumentConversionContext<T> conversionContext) {
        return values.get(name, conversionContext);
    }

    @Override
    public MutableHttpParameters add(CharSequence name, List<CharSequence> values) {
        valuesMap.put(name, Collections.unmodifiableList(
                values.stream().map(v -> v == null ? null : v.toString()).collect(Collectors.toList()))
        );
        return this;
    }
}
