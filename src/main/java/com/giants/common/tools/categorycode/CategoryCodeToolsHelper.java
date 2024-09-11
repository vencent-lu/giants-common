package com.giants.common.tools.categorycode;

import com.giants.common.tools.CategoryCodeTools;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * CategoryCodeToolsHelper TODO
 * date time: 2024/9/9 11:13
 * Copyright 2024 github.com/vencent-lu. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.3.0
 */
public class CategoryCodeToolsHelper {
    private static Map<CategoryCodeType, CategoryCodeTools> categoryCodeToolsMap = null;

    private static CategoryCodeTools createCategoryCodeTools(CategoryCodeType categoryCodeType) {
        CategoryCodeTools categoryCodeTools;
        if (StringUtils.isNotEmpty(categoryCodeType.getCodeFormat())) {
            categoryCodeTools = new CategoryCodeTools(categoryCodeType.getCodeFormat());
        } else {
            categoryCodeTools = new CategoryCodeTools();
        }
        return categoryCodeTools;
    }

    public static CategoryCodeTools getCategoryCodeTools(CategoryCodeType categoryCodeType) {
        if (categoryCodeToolsMap == null) {
            categoryCodeToolsMap = new HashMap<>();
            CategoryCodeTools categoryCodeTools = createCategoryCodeTools(categoryCodeType);
            categoryCodeToolsMap.put(categoryCodeType, categoryCodeTools);
            return categoryCodeTools;
        }
        CategoryCodeTools categoryCodeTools = categoryCodeToolsMap.get(categoryCodeType);
        if (categoryCodeTools != null) {
            return categoryCodeTools;
        }
        categoryCodeTools = createCategoryCodeTools(categoryCodeType);
        categoryCodeToolsMap.put(categoryCodeType, categoryCodeTools);
        return categoryCodeTools;
    }
}
