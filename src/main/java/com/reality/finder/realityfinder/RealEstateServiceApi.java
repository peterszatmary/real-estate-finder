package com.reality.finder.realityfinder;

import java.util.List;

public interface RealEstateServiceApi {
    List<String> search(final Integer filePostfix, final Integer from, final Integer to);
}
