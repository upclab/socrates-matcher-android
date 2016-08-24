package com.google.tagmanager;

import com.google.analytics.containertag.common.Key;
import com.google.analytics.containertag.proto.Serving.FunctionCall;
import com.google.analytics.containertag.proto.Serving.Property;
import com.google.analytics.containertag.proto.Serving.Resource;
import com.google.analytics.containertag.proto.Serving.Rule;
import com.google.analytics.containertag.proto.Serving.ServingValue;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value.Builder;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

class ResourceUtil {

    /* renamed from: com.google.tagmanager.ResourceUtil.1 */
    static /* synthetic */ class C03351 {
        static final /* synthetic */ int[] f49xa390a19;

        static {
            f49xa390a19 = new int[Type.values().length];
            try {
                f49xa390a19[Type.LIST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f49xa390a19[Type.MAP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f49xa390a19[Type.MACRO_REFERENCE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f49xa390a19[Type.TEMPLATE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f49xa390a19[Type.STRING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f49xa390a19[Type.FUNCTION_ID.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f49xa390a19[Type.INTEGER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f49xa390a19[Type.BOOLEAN.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public static class ExpandedFunctionCall {
        private final Map<String, Value> mPropertiesMap;

        private ExpandedFunctionCall(Map<String, Value> propertiesMap) {
            this.mPropertiesMap = propertiesMap;
        }

        public static ExpandedFunctionCallBuilder newBuilder() {
            return new ExpandedFunctionCallBuilder();
        }

        public void updateCacheableProperty(String key, Value v) {
            this.mPropertiesMap.put(key, v);
        }

        public Map<String, Value> getProperties() {
            return Collections.unmodifiableMap(this.mPropertiesMap);
        }

        public String toString() {
            return "Properties: " + getProperties();
        }
    }

    public static class ExpandedFunctionCallBuilder {
        private final Map<String, Value> mPropertiesMap;

        private ExpandedFunctionCallBuilder() {
            this.mPropertiesMap = new HashMap();
        }

        public ExpandedFunctionCallBuilder addProperty(String key, Value value) {
            this.mPropertiesMap.put(key, value);
            return this;
        }

        public ExpandedFunctionCall build() {
            return new ExpandedFunctionCall(null);
        }
    }

    public static class ExpandedResource {
        private final Map<String, List<ExpandedFunctionCall>> mMacros;
        private final int mResourceFormatVersion;
        private final List<ExpandedRule> mRules;
        private final String mVersion;

        private ExpandedResource(List<ExpandedRule> rules, Map<String, List<ExpandedFunctionCall>> macros, String version, int resourceFormatVersion) {
            this.mRules = Collections.unmodifiableList(rules);
            this.mMacros = Collections.unmodifiableMap(macros);
            this.mVersion = version;
            this.mResourceFormatVersion = resourceFormatVersion;
        }

        public static ExpandedResourceBuilder newBuilder() {
            return new ExpandedResourceBuilder();
        }

        public List<ExpandedRule> getRules() {
            return this.mRules;
        }

        public String getVersion() {
            return this.mVersion;
        }

        public int getResourceFormatVersion() {
            return this.mResourceFormatVersion;
        }

        public List<ExpandedFunctionCall> getMacros(String name) {
            return (List) this.mMacros.get(name);
        }

        public Map<String, List<ExpandedFunctionCall>> getAllMacros() {
            return this.mMacros;
        }

        public String toString() {
            return "Rules: " + getRules() + "  Macros: " + this.mMacros;
        }
    }

    public static class ExpandedResourceBuilder {
        private final Map<String, List<ExpandedFunctionCall>> mMacros;
        private final List<ExpandedFunctionCall> mPredicates;
        private int mResourceFormatVersion;
        private final List<ExpandedRule> mRules;
        private final List<ExpandedFunctionCall> mTags;
        private String mVersion;

        private ExpandedResourceBuilder() {
            this.mRules = new ArrayList();
            this.mTags = new ArrayList();
            this.mPredicates = new ArrayList();
            this.mMacros = new HashMap();
            this.mVersion = StringUtils.EMPTY;
            this.mResourceFormatVersion = 0;
        }

        public ExpandedResourceBuilder addRule(ExpandedRule r) {
            this.mRules.add(r);
            return this;
        }

        public ExpandedResourceBuilder addMacro(ExpandedFunctionCall f) {
            String macroName = Types.valueToString((Value) f.getProperties().get(Key.INSTANCE_NAME.toString()));
            List<ExpandedFunctionCall> macroList = (List) this.mMacros.get(macroName);
            if (macroList == null) {
                macroList = new ArrayList();
                this.mMacros.put(macroName, macroList);
            }
            macroList.add(f);
            return this;
        }

        public ExpandedResourceBuilder setVersion(String version) {
            this.mVersion = version;
            return this;
        }

        public ExpandedResourceBuilder setResourceFormatVersion(int resourceFormatVersion) {
            this.mResourceFormatVersion = resourceFormatVersion;
            return this;
        }

        public ExpandedResource build() {
            return new ExpandedResource(this.mMacros, this.mVersion, this.mResourceFormatVersion, null);
        }
    }

    public static class ExpandedRule {
        private final List<String> mAddMacroRuleNames;
        private final List<ExpandedFunctionCall> mAddMacros;
        private final List<String> mAddTagRuleNames;
        private final List<ExpandedFunctionCall> mAddTags;
        private final List<ExpandedFunctionCall> mNegativePredicates;
        private final List<ExpandedFunctionCall> mPositivePredicates;
        private final List<String> mRemoveMacroRuleNames;
        private final List<ExpandedFunctionCall> mRemoveMacros;
        private final List<String> mRemoveTagRuleNames;
        private final List<ExpandedFunctionCall> mRemoveTags;

        private ExpandedRule(List<ExpandedFunctionCall> postivePredicates, List<ExpandedFunctionCall> negativePredicates, List<ExpandedFunctionCall> addTags, List<ExpandedFunctionCall> removeTags, List<ExpandedFunctionCall> addMacros, List<ExpandedFunctionCall> removeMacros, List<String> addMacroRuleNames, List<String> removeMacroRuleNames, List<String> addTagRuleNames, List<String> removeTagRuleNames) {
            this.mPositivePredicates = Collections.unmodifiableList(postivePredicates);
            this.mNegativePredicates = Collections.unmodifiableList(negativePredicates);
            this.mAddTags = Collections.unmodifiableList(addTags);
            this.mRemoveTags = Collections.unmodifiableList(removeTags);
            this.mAddMacros = Collections.unmodifiableList(addMacros);
            this.mRemoveMacros = Collections.unmodifiableList(removeMacros);
            this.mAddMacroRuleNames = Collections.unmodifiableList(addMacroRuleNames);
            this.mRemoveMacroRuleNames = Collections.unmodifiableList(removeMacroRuleNames);
            this.mAddTagRuleNames = Collections.unmodifiableList(addTagRuleNames);
            this.mRemoveTagRuleNames = Collections.unmodifiableList(removeTagRuleNames);
        }

        public static ExpandedRuleBuilder newBuilder() {
            return new ExpandedRuleBuilder();
        }

        public List<ExpandedFunctionCall> getPositivePredicates() {
            return this.mPositivePredicates;
        }

        public List<ExpandedFunctionCall> getNegativePredicates() {
            return this.mNegativePredicates;
        }

        public List<ExpandedFunctionCall> getAddTags() {
            return this.mAddTags;
        }

        public List<ExpandedFunctionCall> getRemoveTags() {
            return this.mRemoveTags;
        }

        public List<ExpandedFunctionCall> getAddMacros() {
            return this.mAddMacros;
        }

        public List<String> getAddMacroRuleNames() {
            return this.mAddMacroRuleNames;
        }

        public List<String> getRemoveMacroRuleNames() {
            return this.mRemoveMacroRuleNames;
        }

        public List<String> getAddTagRuleNames() {
            return this.mAddTagRuleNames;
        }

        public List<String> getRemoveTagRuleNames() {
            return this.mRemoveTagRuleNames;
        }

        public List<ExpandedFunctionCall> getRemoveMacros() {
            return this.mRemoveMacros;
        }

        public String toString() {
            return "Positive predicates: " + getPositivePredicates() + "  Negative predicates: " + getNegativePredicates() + "  Add tags: " + getAddTags() + "  Remove tags: " + getRemoveTags() + "  Add macros: " + getAddMacros() + "  Remove macros: " + getRemoveMacros();
        }
    }

    public static class ExpandedRuleBuilder {
        private final List<String> mAddMacroRuleNames;
        private final List<ExpandedFunctionCall> mAddMacros;
        private final List<String> mAddTagRuleNames;
        private final List<ExpandedFunctionCall> mAddTags;
        private final List<ExpandedFunctionCall> mNegativePredicates;
        private final List<ExpandedFunctionCall> mPositivePredicates;
        private final List<String> mRemoveMacroRuleNames;
        private final List<ExpandedFunctionCall> mRemoveMacros;
        private final List<String> mRemoveTagRuleNames;
        private final List<ExpandedFunctionCall> mRemoveTags;

        private ExpandedRuleBuilder() {
            this.mPositivePredicates = new ArrayList();
            this.mNegativePredicates = new ArrayList();
            this.mAddTags = new ArrayList();
            this.mRemoveTags = new ArrayList();
            this.mAddMacros = new ArrayList();
            this.mRemoveMacros = new ArrayList();
            this.mAddMacroRuleNames = new ArrayList();
            this.mRemoveMacroRuleNames = new ArrayList();
            this.mAddTagRuleNames = new ArrayList();
            this.mRemoveTagRuleNames = new ArrayList();
        }

        public ExpandedRuleBuilder addPositivePredicate(ExpandedFunctionCall f) {
            this.mPositivePredicates.add(f);
            return this;
        }

        public ExpandedRuleBuilder addNegativePredicate(ExpandedFunctionCall f) {
            this.mNegativePredicates.add(f);
            return this;
        }

        public ExpandedRuleBuilder addAddTag(ExpandedFunctionCall f) {
            this.mAddTags.add(f);
            return this;
        }

        public ExpandedRuleBuilder addAddTagRuleName(String ruleName) {
            this.mAddTagRuleNames.add(ruleName);
            return this;
        }

        public ExpandedRuleBuilder addRemoveTag(ExpandedFunctionCall f) {
            this.mRemoveTags.add(f);
            return this;
        }

        public ExpandedRuleBuilder addRemoveTagRuleName(String ruleName) {
            this.mRemoveTagRuleNames.add(ruleName);
            return this;
        }

        public ExpandedRuleBuilder addAddMacro(ExpandedFunctionCall f) {
            this.mAddMacros.add(f);
            return this;
        }

        public ExpandedRuleBuilder addAddMacroRuleName(String ruleName) {
            this.mAddMacroRuleNames.add(ruleName);
            return this;
        }

        public ExpandedRuleBuilder addRemoveMacro(ExpandedFunctionCall f) {
            this.mRemoveMacros.add(f);
            return this;
        }

        public ExpandedRuleBuilder addRemoveMacroRuleName(String ruleName) {
            this.mRemoveMacroRuleNames.add(ruleName);
            return this;
        }

        public ExpandedRule build() {
            return new ExpandedRule(this.mNegativePredicates, this.mAddTags, this.mRemoveTags, this.mAddMacros, this.mRemoveMacros, this.mAddMacroRuleNames, this.mRemoveMacroRuleNames, this.mAddTagRuleNames, this.mRemoveTagRuleNames, null);
        }
    }

    public static class InvalidResourceException extends Exception {
        public InvalidResourceException(String s) {
            super(s);
        }
    }

    private ResourceUtil() {
    }

    public static ExpandedResource getExpandedResource(Resource resource) throws InvalidResourceException {
        int i;
        Value[] expandedValues = new Value[resource.getValueCount()];
        for (i = 0; i < resource.getValueCount(); i++) {
            expandValue(i, resource, expandedValues, new HashSet(0));
        }
        ExpandedResourceBuilder builder = ExpandedResource.newBuilder();
        List<ExpandedFunctionCall> tags = new ArrayList();
        for (i = 0; i < resource.getTagCount(); i++) {
            tags.add(expandFunctionCall(resource.getTag(i), resource, expandedValues, i));
        }
        List<ExpandedFunctionCall> predicates = new ArrayList();
        for (i = 0; i < resource.getPredicateCount(); i++) {
            predicates.add(expandFunctionCall(resource.getPredicate(i), resource, expandedValues, i));
        }
        List<ExpandedFunctionCall> macros = new ArrayList();
        for (i = 0; i < resource.getMacroCount(); i++) {
            ExpandedFunctionCall thisMacro = expandFunctionCall(resource.getMacro(i), resource, expandedValues, i);
            builder.addMacro(thisMacro);
            macros.add(thisMacro);
        }
        for (Rule r : resource.getRuleList()) {
            builder.addRule(expandRule(r, tags, macros, predicates, resource));
        }
        builder.setVersion(resource.getVersion());
        builder.setResourceFormatVersion(resource.getResourceFormatVersion());
        return builder.build();
    }

    public static Builder newValueBuilderBasedOnValue(Value v) {
        Builder result = Value.newBuilder().setType(v.getType()).addAllEscaping(v.getEscapingList());
        if (v.getContainsReferences()) {
            result.setContainsReferences(true);
        }
        return result;
    }

    private static Value expandValue(int i, Resource resource, Value[] expandedValues, Set<Integer> pendingExpansions) throws InvalidResourceException {
        if (pendingExpansions.contains(Integer.valueOf(i))) {
            logAndThrow("Value cycle detected.  Current value reference: " + i + "." + "  Previous value references: " + pendingExpansions + ".");
        }
        Value value = (Value) getWithBoundsCheck(resource.getValueList(), i, "values");
        if (expandedValues[i] != null) {
            return expandedValues[i];
        }
        Value toAdd = null;
        pendingExpansions.add(Integer.valueOf(i));
        Builder builder;
        ServingValue servingValue;
        switch (C03351.f49xa390a19[value.getType().ordinal()]) {
            case MutableTypeSystem.Value.TYPE_FIELD_NUMBER /*1*/:
                builder = newValueBuilderBasedOnValue(value);
                for (Integer listIndex : getServingValue(value).getListItemList()) {
                    builder.addListItem(expandValue(listIndex.intValue(), resource, expandedValues, pendingExpansions));
                }
                toAdd = builder.build();
                break;
            case MutableTypeSystem.Value.STRING_FIELD_NUMBER /*2*/:
                builder = newValueBuilderBasedOnValue(value);
                servingValue = getServingValue(value);
                if (servingValue.getMapKeyCount() != servingValue.getMapValueCount()) {
                    logAndThrow("Uneven map keys (" + servingValue.getMapKeyCount() + ") and map values (" + servingValue.getMapValueCount() + ")");
                }
                for (Integer keyIndex : servingValue.getMapKeyList()) {
                    builder.addMapKey(expandValue(keyIndex.intValue(), resource, expandedValues, pendingExpansions));
                }
                for (Integer valueIndex : servingValue.getMapValueList()) {
                    builder.addMapValue(expandValue(valueIndex.intValue(), resource, expandedValues, pendingExpansions));
                }
                toAdd = builder.build();
                break;
            case MutableTypeSystem.Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                String macroName;
                builder = newValueBuilderBasedOnValue(value);
                servingValue = getServingValue(value);
                if (servingValue.hasMacroNameReference()) {
                    macroName = Types.valueToString(expandValue(servingValue.getMacroNameReference(), resource, expandedValues, pendingExpansions));
                } else {
                    logAndThrow("Missing macro name reference");
                    macroName = StringUtils.EMPTY;
                }
                builder.setMacroReference(macroName);
                toAdd = builder.build();
                break;
            case MutableTypeSystem.Value.MAP_KEY_FIELD_NUMBER /*4*/:
                builder = newValueBuilderBasedOnValue(value);
                for (Integer templateIndex : getServingValue(value).getTemplateTokenList()) {
                    builder.addTemplateToken(expandValue(templateIndex.intValue(), resource, expandedValues, pendingExpansions));
                }
                toAdd = builder.build();
                break;
            case MutableTypeSystem.Value.MAP_VALUE_FIELD_NUMBER /*5*/:
            case MutableTypeSystem.Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
            case MutableTypeSystem.Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
            case MutableTypeSystem.Value.INTEGER_FIELD_NUMBER /*8*/:
                toAdd = value;
                break;
        }
        if (toAdd == null) {
            logAndThrow("Invalid value: " + value);
        }
        expandedValues[i] = toAdd;
        pendingExpansions.remove(Integer.valueOf(i));
        return toAdd;
    }

    private static ServingValue getServingValue(Value value) throws InvalidResourceException {
        if (!value.hasExtension(ServingValue.ext)) {
            logAndThrow("Expected a ServingValue and didn't get one. Value is: " + value);
        }
        return (ServingValue) value.getExtension(ServingValue.ext);
    }

    private static void logAndThrow(String error) throws InvalidResourceException {
        Log.m606e(error);
        throw new InvalidResourceException(error);
    }

    private static <T> T getWithBoundsCheck(T[] array, int idx, String listName) throws InvalidResourceException {
        if (idx < 0 || idx >= array.length) {
            logAndThrow("Index out of bounds detected: " + idx + " in " + listName);
        }
        return array[idx];
    }

    private static <T> T getWithBoundsCheck(List<T> list, int idx, String listName) throws InvalidResourceException {
        if (idx < 0 || idx >= list.size()) {
            logAndThrow("Index out of bounds detected: " + idx + " in " + listName);
        }
        return list.get(idx);
    }

    private static ExpandedFunctionCall expandFunctionCall(FunctionCall functionCall, Resource resource, Value[] expandedValues, int idx) throws InvalidResourceException {
        ExpandedFunctionCallBuilder builder = ExpandedFunctionCall.newBuilder();
        for (Integer i : functionCall.getPropertyList()) {
            Property p = (Property) getWithBoundsCheck(resource.getPropertyList(), i.intValue(), "properties");
            builder.addProperty((String) getWithBoundsCheck(resource.getKeyList(), p.getKey(), "keys"), (Value) getWithBoundsCheck((Object[]) expandedValues, p.getValue(), "values"));
        }
        return builder.build();
    }

    private static ExpandedRule expandRule(Rule rule, List<ExpandedFunctionCall> tags, List<ExpandedFunctionCall> macros, List<ExpandedFunctionCall> predicates, Resource resource) {
        ExpandedRuleBuilder ruleBuilder = ExpandedRule.newBuilder();
        for (Integer i : rule.getPositivePredicateList()) {
            ruleBuilder.addPositivePredicate((ExpandedFunctionCall) predicates.get(i.intValue()));
        }
        for (Integer i2 : rule.getNegativePredicateList()) {
            ruleBuilder.addNegativePredicate((ExpandedFunctionCall) predicates.get(i2.intValue()));
        }
        for (Integer tagIndex : rule.getAddTagList()) {
            ruleBuilder.addAddTag((ExpandedFunctionCall) tags.get(tagIndex.intValue()));
        }
        for (Integer ruleNameIndex : rule.getAddTagRuleNameList()) {
            ruleBuilder.addAddTagRuleName(resource.getValue(ruleNameIndex.intValue()).getString());
        }
        for (Integer tagIndex2 : rule.getRemoveTagList()) {
            ruleBuilder.addRemoveTag((ExpandedFunctionCall) tags.get(tagIndex2.intValue()));
        }
        for (Integer ruleNameIndex2 : rule.getRemoveTagRuleNameList()) {
            ruleBuilder.addRemoveTagRuleName(resource.getValue(ruleNameIndex2.intValue()).getString());
        }
        for (Integer macroIndex : rule.getAddMacroList()) {
            ruleBuilder.addAddMacro((ExpandedFunctionCall) macros.get(macroIndex.intValue()));
        }
        for (Integer ruleNameIndex22 : rule.getAddMacroRuleNameList()) {
            ruleBuilder.addAddMacroRuleName(resource.getValue(ruleNameIndex22.intValue()).getString());
        }
        for (Integer macroIndex2 : rule.getRemoveMacroList()) {
            ruleBuilder.addRemoveMacro((ExpandedFunctionCall) macros.get(macroIndex2.intValue()));
        }
        for (Integer ruleNameIndex222 : rule.getRemoveMacroRuleNameList()) {
            ruleBuilder.addRemoveMacroRuleName(resource.getValue(ruleNameIndex222.intValue()).getString());
        }
        return ruleBuilder.build();
    }
}
