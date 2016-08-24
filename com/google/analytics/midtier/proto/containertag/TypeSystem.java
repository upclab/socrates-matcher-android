package com.google.analytics.midtier.proto.containertag;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.tagmanager.protobuf.AbstractParser;
import com.google.tagmanager.protobuf.ByteString;
import com.google.tagmanager.protobuf.CodedInputStream;
import com.google.tagmanager.protobuf.CodedOutputStream;
import com.google.tagmanager.protobuf.ExtensionRegistryLite;
import com.google.tagmanager.protobuf.GeneratedMessageLite;
import com.google.tagmanager.protobuf.GeneratedMessageLite.ExtendableBuilder;
import com.google.tagmanager.protobuf.GeneratedMessageLite.ExtendableMessage;
import com.google.tagmanager.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder;
import com.google.tagmanager.protobuf.Internal;
import com.google.tagmanager.protobuf.Internal.EnumLite;
import com.google.tagmanager.protobuf.Internal.EnumLiteMap;
import com.google.tagmanager.protobuf.InvalidProtocolBufferException;
import com.google.tagmanager.protobuf.MessageLite;
import com.google.tagmanager.protobuf.MutableMessageLite;
import com.google.tagmanager.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.MutableDateTime;

public final class TypeSystem {

    public interface ValueOrBuilder extends ExtendableMessageOrBuilder<Value> {
        boolean getBoolean();

        boolean getContainsReferences();

        Escaping getEscaping(int i);

        int getEscapingCount();

        List<Escaping> getEscapingList();

        String getFunctionId();

        ByteString getFunctionIdBytes();

        long getInteger();

        Value getListItem(int i);

        int getListItemCount();

        List<Value> getListItemList();

        String getMacroReference();

        ByteString getMacroReferenceBytes();

        Value getMapKey(int i);

        int getMapKeyCount();

        List<Value> getMapKeyList();

        Value getMapValue(int i);

        int getMapValueCount();

        List<Value> getMapValueList();

        String getString();

        ByteString getStringBytes();

        Value getTemplateToken(int i);

        int getTemplateTokenCount();

        List<Value> getTemplateTokenList();

        Type getType();

        boolean hasBoolean();

        boolean hasContainsReferences();

        boolean hasFunctionId();

        boolean hasInteger();

        boolean hasMacroReference();

        boolean hasString();

        boolean hasType();
    }

    public static final class Value extends ExtendableMessage<Value> implements ValueOrBuilder {
        public static final int BOOLEAN_FIELD_NUMBER = 12;
        public static final int CONTAINS_REFERENCES_FIELD_NUMBER = 9;
        public static final int ESCAPING_FIELD_NUMBER = 10;
        public static final int FUNCTION_ID_FIELD_NUMBER = 7;
        public static final int INTEGER_FIELD_NUMBER = 8;
        public static final int LIST_ITEM_FIELD_NUMBER = 3;
        public static final int MACRO_REFERENCE_FIELD_NUMBER = 6;
        public static final int MAP_KEY_FIELD_NUMBER = 4;
        public static final int MAP_VALUE_FIELD_NUMBER = 5;
        public static Parser<Value> PARSER = null;
        public static final int STRING_FIELD_NUMBER = 2;
        public static final int TEMPLATE_TOKEN_FIELD_NUMBER = 11;
        public static final int TYPE_FIELD_NUMBER = 1;
        private static final Value defaultInstance;
        private static volatile MutableMessageLite mutableDefault;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean boolean_;
        private boolean containsReferences_;
        private List<Escaping> escaping_;
        private Object functionId_;
        private long integer_;
        private List<Value> listItem_;
        private Object macroReference_;
        private List<Value> mapKey_;
        private List<Value> mapValue_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object string_;
        private List<Value> templateToken_;
        private Type type_;
        private final ByteString unknownFields;

        public enum Escaping implements EnumLite {
            ESCAPE_HTML(0, ESCAPE_HTML_VALUE),
            ESCAPE_HTML_RCDATA(ESCAPE_HTML_VALUE, ESCAPE_HTML_RCDATA_VALUE),
            ESCAPE_HTML_ATTRIBUTE(ESCAPE_HTML_RCDATA_VALUE, ESCAPE_HTML_ATTRIBUTE_VALUE),
            ESCAPE_HTML_ATTRIBUTE_NOSPACE(ESCAPE_HTML_ATTRIBUTE_VALUE, ESCAPE_HTML_ATTRIBUTE_NOSPACE_VALUE),
            FILTER_HTML_ELEMENT_NAME(ESCAPE_HTML_ATTRIBUTE_NOSPACE_VALUE, FILTER_HTML_ELEMENT_NAME_VALUE),
            FILTER_HTML_ATTRIBUTES(FILTER_HTML_ELEMENT_NAME_VALUE, FILTER_HTML_ATTRIBUTES_VALUE),
            ESCAPE_JS_STRING(FILTER_HTML_ATTRIBUTES_VALUE, ESCAPE_JS_STRING_VALUE),
            ESCAPE_JS_VALUE(ESCAPE_JS_STRING_VALUE, ESCAPE_JS_VALUE_VALUE),
            ESCAPE_JS_REGEX(ESCAPE_JS_VALUE_VALUE, ESCAPE_JS_REGEX_VALUE),
            ESCAPE_CSS_STRING(ESCAPE_JS_REGEX_VALUE, ESCAPE_CSS_STRING_VALUE),
            FILTER_CSS_VALUE(ESCAPE_CSS_STRING_VALUE, FILTER_CSS_VALUE_VALUE),
            ESCAPE_URI(FILTER_CSS_VALUE_VALUE, ESCAPE_URI_VALUE),
            NORMALIZE_URI(ESCAPE_URI_VALUE, NORMALIZE_URI_VALUE),
            FILTER_NORMALIZE_URI(NORMALIZE_URI_VALUE, FILTER_NORMALIZE_URI_VALUE),
            NO_AUTOESCAPE(FILTER_NORMALIZE_URI_VALUE, NO_AUTOESCAPE_VALUE),
            TEXT(NO_AUTOESCAPE_VALUE, TEXT_VALUE),
            CONVERT_JS_VALUE_TO_EXPRESSION(CONVERT_JS_VALUE_TO_EXPRESSION_VALUE, CONVERT_JS_VALUE_TO_EXPRESSION_VALUE);
            
            public static final int CONVERT_JS_VALUE_TO_EXPRESSION_VALUE = 16;
            public static final int ESCAPE_CSS_STRING_VALUE = 10;
            public static final int ESCAPE_HTML_ATTRIBUTE_NOSPACE_VALUE = 4;
            public static final int ESCAPE_HTML_ATTRIBUTE_VALUE = 3;
            public static final int ESCAPE_HTML_RCDATA_VALUE = 2;
            public static final int ESCAPE_HTML_VALUE = 1;
            public static final int ESCAPE_JS_REGEX_VALUE = 9;
            public static final int ESCAPE_JS_STRING_VALUE = 7;
            public static final int ESCAPE_JS_VALUE_VALUE = 8;
            public static final int ESCAPE_URI_VALUE = 12;
            public static final int FILTER_CSS_VALUE_VALUE = 11;
            public static final int FILTER_HTML_ATTRIBUTES_VALUE = 6;
            public static final int FILTER_HTML_ELEMENT_NAME_VALUE = 5;
            public static final int FILTER_NORMALIZE_URI_VALUE = 14;
            public static final int NORMALIZE_URI_VALUE = 13;
            public static final int NO_AUTOESCAPE_VALUE = 15;
            public static final int TEXT_VALUE = 17;
            private static EnumLiteMap<Escaping> internalValueMap;
            private final int value;

            /* renamed from: com.google.analytics.midtier.proto.containertag.TypeSystem.Value.Escaping.1 */
            static class C04601 implements EnumLiteMap<Escaping> {
                C04601() {
                }

                public Escaping findValueByNumber(int number) {
                    return Escaping.valueOf(number);
                }
            }

            static {
                internalValueMap = new C04601();
            }

            public final int getNumber() {
                return this.value;
            }

            public static Escaping valueOf(int value) {
                switch (value) {
                    case ESCAPE_HTML_VALUE:
                        return ESCAPE_HTML;
                    case ESCAPE_HTML_RCDATA_VALUE:
                        return ESCAPE_HTML_RCDATA;
                    case ESCAPE_HTML_ATTRIBUTE_VALUE:
                        return ESCAPE_HTML_ATTRIBUTE;
                    case ESCAPE_HTML_ATTRIBUTE_NOSPACE_VALUE:
                        return ESCAPE_HTML_ATTRIBUTE_NOSPACE;
                    case FILTER_HTML_ELEMENT_NAME_VALUE:
                        return FILTER_HTML_ELEMENT_NAME;
                    case FILTER_HTML_ATTRIBUTES_VALUE:
                        return FILTER_HTML_ATTRIBUTES;
                    case ESCAPE_JS_STRING_VALUE:
                        return ESCAPE_JS_STRING;
                    case ESCAPE_JS_VALUE_VALUE:
                        return ESCAPE_JS_VALUE;
                    case ESCAPE_JS_REGEX_VALUE:
                        return ESCAPE_JS_REGEX;
                    case ESCAPE_CSS_STRING_VALUE:
                        return ESCAPE_CSS_STRING;
                    case FILTER_CSS_VALUE_VALUE:
                        return FILTER_CSS_VALUE;
                    case ESCAPE_URI_VALUE:
                        return ESCAPE_URI;
                    case NORMALIZE_URI_VALUE:
                        return NORMALIZE_URI;
                    case FILTER_NORMALIZE_URI_VALUE:
                        return FILTER_NORMALIZE_URI;
                    case NO_AUTOESCAPE_VALUE:
                        return NO_AUTOESCAPE;
                    case CONVERT_JS_VALUE_TO_EXPRESSION_VALUE:
                        return CONVERT_JS_VALUE_TO_EXPRESSION;
                    case TEXT_VALUE:
                        return TEXT;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<Escaping> internalGetValueMap() {
                return internalValueMap;
            }

            private Escaping(int index, int value) {
                this.value = value;
            }
        }

        public enum Type implements EnumLite {
            STRING(0, STRING_VALUE),
            LIST(STRING_VALUE, LIST_VALUE),
            MAP(LIST_VALUE, MAP_VALUE),
            MACRO_REFERENCE(MAP_VALUE, MACRO_REFERENCE_VALUE),
            FUNCTION_ID(MACRO_REFERENCE_VALUE, FUNCTION_ID_VALUE),
            INTEGER(FUNCTION_ID_VALUE, INTEGER_VALUE),
            TEMPLATE(INTEGER_VALUE, TEMPLATE_VALUE),
            BOOLEAN(TEMPLATE_VALUE, BOOLEAN_VALUE);
            
            public static final int BOOLEAN_VALUE = 8;
            public static final int FUNCTION_ID_VALUE = 5;
            public static final int INTEGER_VALUE = 6;
            public static final int LIST_VALUE = 2;
            public static final int MACRO_REFERENCE_VALUE = 4;
            public static final int MAP_VALUE = 3;
            public static final int STRING_VALUE = 1;
            public static final int TEMPLATE_VALUE = 7;
            private static EnumLiteMap<Type> internalValueMap;
            private final int value;

            /* renamed from: com.google.analytics.midtier.proto.containertag.TypeSystem.Value.Type.1 */
            static class C04611 implements EnumLiteMap<Type> {
                C04611() {
                }

                public Type findValueByNumber(int number) {
                    return Type.valueOf(number);
                }
            }

            static {
                internalValueMap = new C04611();
            }

            public final int getNumber() {
                return this.value;
            }

            public static Type valueOf(int value) {
                switch (value) {
                    case STRING_VALUE:
                        return STRING;
                    case LIST_VALUE:
                        return LIST;
                    case MAP_VALUE:
                        return MAP;
                    case MACRO_REFERENCE_VALUE:
                        return MACRO_REFERENCE;
                    case FUNCTION_ID_VALUE:
                        return FUNCTION_ID;
                    case INTEGER_VALUE:
                        return INTEGER;
                    case TEMPLATE_VALUE:
                        return TEMPLATE;
                    case BOOLEAN_VALUE:
                        return BOOLEAN;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<Type> internalGetValueMap() {
                return internalValueMap;
            }

            private Type(int index, int value) {
                this.value = value;
            }
        }

        /* renamed from: com.google.analytics.midtier.proto.containertag.TypeSystem.Value.1 */
        static class C08801 extends AbstractParser<Value> {
            C08801() {
            }

            public Value parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Value(extensionRegistry, null);
            }
        }

        public static final class Builder extends ExtendableBuilder<Value, Builder> implements ValueOrBuilder {
            private int bitField0_;
            private boolean boolean_;
            private boolean containsReferences_;
            private List<Escaping> escaping_;
            private Object functionId_;
            private long integer_;
            private List<Value> listItem_;
            private Object macroReference_;
            private List<Value> mapKey_;
            private List<Value> mapValue_;
            private Object string_;
            private List<Value> templateToken_;
            private Type type_;

            private Builder() {
                this.type_ = Type.STRING;
                this.string_ = StringUtils.EMPTY;
                this.listItem_ = Collections.emptyList();
                this.mapKey_ = Collections.emptyList();
                this.mapValue_ = Collections.emptyList();
                this.macroReference_ = StringUtils.EMPTY;
                this.functionId_ = StringUtils.EMPTY;
                this.templateToken_ = Collections.emptyList();
                this.escaping_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.type_ = Type.STRING;
                this.bitField0_ &= -2;
                this.string_ = StringUtils.EMPTY;
                this.bitField0_ &= -3;
                this.listItem_ = Collections.emptyList();
                this.bitField0_ &= -5;
                this.mapKey_ = Collections.emptyList();
                this.bitField0_ &= -9;
                this.mapValue_ = Collections.emptyList();
                this.bitField0_ &= -17;
                this.macroReference_ = StringUtils.EMPTY;
                this.bitField0_ &= -33;
                this.functionId_ = StringUtils.EMPTY;
                this.bitField0_ &= -65;
                this.integer_ = 0;
                this.bitField0_ &= -129;
                this.boolean_ = false;
                this.bitField0_ &= -257;
                this.templateToken_ = Collections.emptyList();
                this.bitField0_ &= -513;
                this.escaping_ = Collections.emptyList();
                this.bitField0_ &= -1025;
                this.containsReferences_ = false;
                this.bitField0_ &= -2049;
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Value getDefaultInstanceForType() {
                return Value.getDefaultInstance();
            }

            public Value build() {
                Value result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.tagmanager.protobuf.AbstractMessageLite.Builder.newUninitializedMessageException(result);
            }

            public Value buildPartial() {
                Value result = new Value(null);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & Value.TYPE_FIELD_NUMBER) == Value.TYPE_FIELD_NUMBER) {
                    to_bitField0_ = 0 | Value.TYPE_FIELD_NUMBER;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & Value.STRING_FIELD_NUMBER) == Value.STRING_FIELD_NUMBER) {
                    to_bitField0_ |= Value.STRING_FIELD_NUMBER;
                }
                result.string_ = this.string_;
                if ((this.bitField0_ & Value.MAP_KEY_FIELD_NUMBER) == Value.MAP_KEY_FIELD_NUMBER) {
                    this.listItem_ = Collections.unmodifiableList(this.listItem_);
                    this.bitField0_ &= -5;
                }
                result.listItem_ = this.listItem_;
                if ((this.bitField0_ & Value.INTEGER_FIELD_NUMBER) == Value.INTEGER_FIELD_NUMBER) {
                    this.mapKey_ = Collections.unmodifiableList(this.mapKey_);
                    this.bitField0_ &= -9;
                }
                result.mapKey_ = this.mapKey_;
                if ((this.bitField0_ & 16) == 16) {
                    this.mapValue_ = Collections.unmodifiableList(this.mapValue_);
                    this.bitField0_ &= -17;
                }
                result.mapValue_ = this.mapValue_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= Value.MAP_KEY_FIELD_NUMBER;
                }
                result.macroReference_ = this.macroReference_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= Value.INTEGER_FIELD_NUMBER;
                }
                result.functionId_ = this.functionId_;
                if ((from_bitField0_ & AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER) == AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER) {
                    to_bitField0_ |= 16;
                }
                result.integer_ = this.integer_;
                if ((from_bitField0_ & AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT) == AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT) {
                    to_bitField0_ |= 32;
                }
                result.boolean_ = this.boolean_;
                if ((this.bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START) == AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START) {
                    this.templateToken_ = Collections.unmodifiableList(this.templateToken_);
                    this.bitField0_ &= -513;
                }
                result.templateToken_ = this.templateToken_;
                if ((this.bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) == AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) {
                    this.escaping_ = Collections.unmodifiableList(this.escaping_);
                    this.bitField0_ &= -1025;
                }
                result.escaping_ = this.escaping_;
                if ((from_bitField0_ & AccessibilityEventCompat.TYPE_WINDOW_CONTENT_CHANGED) == AccessibilityEventCompat.TYPE_WINDOW_CONTENT_CHANGED) {
                    to_bitField0_ |= 64;
                }
                result.containsReferences_ = this.containsReferences_;
                result.bitField0_ = to_bitField0_;
                return result;
            }

            public Builder mergeFrom(Value other) {
                if (other != Value.getDefaultInstance()) {
                    if (other.hasType()) {
                        setType(other.getType());
                    }
                    if (other.hasString()) {
                        this.bitField0_ |= Value.STRING_FIELD_NUMBER;
                        this.string_ = other.string_;
                    }
                    if (!other.listItem_.isEmpty()) {
                        if (this.listItem_.isEmpty()) {
                            this.listItem_ = other.listItem_;
                            this.bitField0_ &= -5;
                        } else {
                            ensureListItemIsMutable();
                            this.listItem_.addAll(other.listItem_);
                        }
                    }
                    if (!other.mapKey_.isEmpty()) {
                        if (this.mapKey_.isEmpty()) {
                            this.mapKey_ = other.mapKey_;
                            this.bitField0_ &= -9;
                        } else {
                            ensureMapKeyIsMutable();
                            this.mapKey_.addAll(other.mapKey_);
                        }
                    }
                    if (!other.mapValue_.isEmpty()) {
                        if (this.mapValue_.isEmpty()) {
                            this.mapValue_ = other.mapValue_;
                            this.bitField0_ &= -17;
                        } else {
                            ensureMapValueIsMutable();
                            this.mapValue_.addAll(other.mapValue_);
                        }
                    }
                    if (other.hasMacroReference()) {
                        this.bitField0_ |= 32;
                        this.macroReference_ = other.macroReference_;
                    }
                    if (other.hasFunctionId()) {
                        this.bitField0_ |= 64;
                        this.functionId_ = other.functionId_;
                    }
                    if (other.hasInteger()) {
                        setInteger(other.getInteger());
                    }
                    if (other.hasBoolean()) {
                        setBoolean(other.getBoolean());
                    }
                    if (!other.templateToken_.isEmpty()) {
                        if (this.templateToken_.isEmpty()) {
                            this.templateToken_ = other.templateToken_;
                            this.bitField0_ &= -513;
                        } else {
                            ensureTemplateTokenIsMutable();
                            this.templateToken_.addAll(other.templateToken_);
                        }
                    }
                    if (!other.escaping_.isEmpty()) {
                        if (this.escaping_.isEmpty()) {
                            this.escaping_ = other.escaping_;
                            this.bitField0_ &= -1025;
                        } else {
                            ensureEscapingIsMutable();
                            this.escaping_.addAll(other.escaping_);
                        }
                    }
                    if (other.hasContainsReferences()) {
                        setContainsReferences(other.getContainsReferences());
                    }
                    mergeExtensionFields(other);
                    setUnknownFields(getUnknownFields().concat(other.unknownFields));
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasType()) {
                    return false;
                }
                int i;
                for (i = 0; i < getListItemCount(); i += Value.TYPE_FIELD_NUMBER) {
                    if (!getListItem(i).isInitialized()) {
                        return false;
                    }
                }
                for (i = 0; i < getMapKeyCount(); i += Value.TYPE_FIELD_NUMBER) {
                    if (!getMapKey(i).isInitialized()) {
                        return false;
                    }
                }
                for (i = 0; i < getMapValueCount(); i += Value.TYPE_FIELD_NUMBER) {
                    if (!getMapValue(i).isInitialized()) {
                        return false;
                    }
                }
                for (i = 0; i < getTemplateTokenCount(); i += Value.TYPE_FIELD_NUMBER) {
                    if (!getTemplateToken(i).isInitialized()) {
                        return false;
                    }
                }
                if (extensionsAreInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Value parsedMessage = null;
                try {
                    parsedMessage = (Value) Value.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Value) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasType() {
                return (this.bitField0_ & Value.TYPE_FIELD_NUMBER) == Value.TYPE_FIELD_NUMBER;
            }

            public Type getType() {
                return this.type_;
            }

            public Builder setType(Type value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= Value.TYPE_FIELD_NUMBER;
                this.type_ = value;
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -2;
                this.type_ = Type.STRING;
                return this;
            }

            public boolean hasString() {
                return (this.bitField0_ & Value.STRING_FIELD_NUMBER) == Value.STRING_FIELD_NUMBER;
            }

            public String getString() {
                ByteString ref = this.string_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.string_ = s;
                return s;
            }

            public ByteString getStringBytes() {
                Object ref = this.string_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.string_ = b;
                return b;
            }

            public Builder setString(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= Value.STRING_FIELD_NUMBER;
                this.string_ = value;
                return this;
            }

            public Builder clearString() {
                this.bitField0_ &= -3;
                this.string_ = Value.getDefaultInstance().getString();
                return this;
            }

            public Builder setStringBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= Value.STRING_FIELD_NUMBER;
                this.string_ = value;
                return this;
            }

            private void ensureListItemIsMutable() {
                if ((this.bitField0_ & Value.MAP_KEY_FIELD_NUMBER) != Value.MAP_KEY_FIELD_NUMBER) {
                    this.listItem_ = new ArrayList(this.listItem_);
                    this.bitField0_ |= Value.MAP_KEY_FIELD_NUMBER;
                }
            }

            public List<Value> getListItemList() {
                return Collections.unmodifiableList(this.listItem_);
            }

            public int getListItemCount() {
                return this.listItem_.size();
            }

            public Value getListItem(int index) {
                return (Value) this.listItem_.get(index);
            }

            public Builder setListItem(int index, Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureListItemIsMutable();
                this.listItem_.set(index, value);
                return this;
            }

            public Builder setListItem(int index, Builder builderForValue) {
                ensureListItemIsMutable();
                this.listItem_.set(index, builderForValue.build());
                return this;
            }

            public Builder addListItem(Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureListItemIsMutable();
                this.listItem_.add(value);
                return this;
            }

            public Builder addListItem(int index, Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureListItemIsMutable();
                this.listItem_.add(index, value);
                return this;
            }

            public Builder addListItem(Builder builderForValue) {
                ensureListItemIsMutable();
                this.listItem_.add(builderForValue.build());
                return this;
            }

            public Builder addListItem(int index, Builder builderForValue) {
                ensureListItemIsMutable();
                this.listItem_.add(index, builderForValue.build());
                return this;
            }

            public Builder addAllListItem(Iterable<? extends Value> values) {
                ensureListItemIsMutable();
                com.google.tagmanager.protobuf.AbstractMessageLite.Builder.addAll(values, this.listItem_);
                return this;
            }

            public Builder clearListItem() {
                this.listItem_ = Collections.emptyList();
                this.bitField0_ &= -5;
                return this;
            }

            public Builder removeListItem(int index) {
                ensureListItemIsMutable();
                this.listItem_.remove(index);
                return this;
            }

            private void ensureMapKeyIsMutable() {
                if ((this.bitField0_ & Value.INTEGER_FIELD_NUMBER) != Value.INTEGER_FIELD_NUMBER) {
                    this.mapKey_ = new ArrayList(this.mapKey_);
                    this.bitField0_ |= Value.INTEGER_FIELD_NUMBER;
                }
            }

            public List<Value> getMapKeyList() {
                return Collections.unmodifiableList(this.mapKey_);
            }

            public int getMapKeyCount() {
                return this.mapKey_.size();
            }

            public Value getMapKey(int index) {
                return (Value) this.mapKey_.get(index);
            }

            public Builder setMapKey(int index, Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureMapKeyIsMutable();
                this.mapKey_.set(index, value);
                return this;
            }

            public Builder setMapKey(int index, Builder builderForValue) {
                ensureMapKeyIsMutable();
                this.mapKey_.set(index, builderForValue.build());
                return this;
            }

            public Builder addMapKey(Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureMapKeyIsMutable();
                this.mapKey_.add(value);
                return this;
            }

            public Builder addMapKey(int index, Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureMapKeyIsMutable();
                this.mapKey_.add(index, value);
                return this;
            }

            public Builder addMapKey(Builder builderForValue) {
                ensureMapKeyIsMutable();
                this.mapKey_.add(builderForValue.build());
                return this;
            }

            public Builder addMapKey(int index, Builder builderForValue) {
                ensureMapKeyIsMutable();
                this.mapKey_.add(index, builderForValue.build());
                return this;
            }

            public Builder addAllMapKey(Iterable<? extends Value> values) {
                ensureMapKeyIsMutable();
                com.google.tagmanager.protobuf.AbstractMessageLite.Builder.addAll(values, this.mapKey_);
                return this;
            }

            public Builder clearMapKey() {
                this.mapKey_ = Collections.emptyList();
                this.bitField0_ &= -9;
                return this;
            }

            public Builder removeMapKey(int index) {
                ensureMapKeyIsMutable();
                this.mapKey_.remove(index);
                return this;
            }

            private void ensureMapValueIsMutable() {
                if ((this.bitField0_ & 16) != 16) {
                    this.mapValue_ = new ArrayList(this.mapValue_);
                    this.bitField0_ |= 16;
                }
            }

            public List<Value> getMapValueList() {
                return Collections.unmodifiableList(this.mapValue_);
            }

            public int getMapValueCount() {
                return this.mapValue_.size();
            }

            public Value getMapValue(int index) {
                return (Value) this.mapValue_.get(index);
            }

            public Builder setMapValue(int index, Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureMapValueIsMutable();
                this.mapValue_.set(index, value);
                return this;
            }

            public Builder setMapValue(int index, Builder builderForValue) {
                ensureMapValueIsMutable();
                this.mapValue_.set(index, builderForValue.build());
                return this;
            }

            public Builder addMapValue(Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureMapValueIsMutable();
                this.mapValue_.add(value);
                return this;
            }

            public Builder addMapValue(int index, Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureMapValueIsMutable();
                this.mapValue_.add(index, value);
                return this;
            }

            public Builder addMapValue(Builder builderForValue) {
                ensureMapValueIsMutable();
                this.mapValue_.add(builderForValue.build());
                return this;
            }

            public Builder addMapValue(int index, Builder builderForValue) {
                ensureMapValueIsMutable();
                this.mapValue_.add(index, builderForValue.build());
                return this;
            }

            public Builder addAllMapValue(Iterable<? extends Value> values) {
                ensureMapValueIsMutable();
                com.google.tagmanager.protobuf.AbstractMessageLite.Builder.addAll(values, this.mapValue_);
                return this;
            }

            public Builder clearMapValue() {
                this.mapValue_ = Collections.emptyList();
                this.bitField0_ &= -17;
                return this;
            }

            public Builder removeMapValue(int index) {
                ensureMapValueIsMutable();
                this.mapValue_.remove(index);
                return this;
            }

            public boolean hasMacroReference() {
                return (this.bitField0_ & 32) == 32;
            }

            public String getMacroReference() {
                ByteString ref = this.macroReference_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.macroReference_ = s;
                return s;
            }

            public ByteString getMacroReferenceBytes() {
                Object ref = this.macroReference_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.macroReference_ = b;
                return b;
            }

            public Builder setMacroReference(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.macroReference_ = value;
                return this;
            }

            public Builder clearMacroReference() {
                this.bitField0_ &= -33;
                this.macroReference_ = Value.getDefaultInstance().getMacroReference();
                return this;
            }

            public Builder setMacroReferenceBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.macroReference_ = value;
                return this;
            }

            public boolean hasFunctionId() {
                return (this.bitField0_ & 64) == 64;
            }

            public String getFunctionId() {
                ByteString ref = this.functionId_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.functionId_ = s;
                return s;
            }

            public ByteString getFunctionIdBytes() {
                Object ref = this.functionId_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.functionId_ = b;
                return b;
            }

            public Builder setFunctionId(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.functionId_ = value;
                return this;
            }

            public Builder clearFunctionId() {
                this.bitField0_ &= -65;
                this.functionId_ = Value.getDefaultInstance().getFunctionId();
                return this;
            }

            public Builder setFunctionIdBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.functionId_ = value;
                return this;
            }

            public boolean hasInteger() {
                return (this.bitField0_ & AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER) == AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER;
            }

            public long getInteger() {
                return this.integer_;
            }

            public Builder setInteger(long value) {
                this.bitField0_ |= AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER;
                this.integer_ = value;
                return this;
            }

            public Builder clearInteger() {
                this.bitField0_ &= -129;
                this.integer_ = 0;
                return this;
            }

            public boolean hasBoolean() {
                return (this.bitField0_ & AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT) == AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT;
            }

            public boolean getBoolean() {
                return this.boolean_;
            }

            public Builder setBoolean(boolean value) {
                this.bitField0_ |= AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT;
                this.boolean_ = value;
                return this;
            }

            public Builder clearBoolean() {
                this.bitField0_ &= -257;
                this.boolean_ = false;
                return this;
            }

            private void ensureTemplateTokenIsMutable() {
                if ((this.bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START) != AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START) {
                    this.templateToken_ = new ArrayList(this.templateToken_);
                    this.bitField0_ |= AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START;
                }
            }

            public List<Value> getTemplateTokenList() {
                return Collections.unmodifiableList(this.templateToken_);
            }

            public int getTemplateTokenCount() {
                return this.templateToken_.size();
            }

            public Value getTemplateToken(int index) {
                return (Value) this.templateToken_.get(index);
            }

            public Builder setTemplateToken(int index, Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureTemplateTokenIsMutable();
                this.templateToken_.set(index, value);
                return this;
            }

            public Builder setTemplateToken(int index, Builder builderForValue) {
                ensureTemplateTokenIsMutable();
                this.templateToken_.set(index, builderForValue.build());
                return this;
            }

            public Builder addTemplateToken(Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureTemplateTokenIsMutable();
                this.templateToken_.add(value);
                return this;
            }

            public Builder addTemplateToken(int index, Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureTemplateTokenIsMutable();
                this.templateToken_.add(index, value);
                return this;
            }

            public Builder addTemplateToken(Builder builderForValue) {
                ensureTemplateTokenIsMutable();
                this.templateToken_.add(builderForValue.build());
                return this;
            }

            public Builder addTemplateToken(int index, Builder builderForValue) {
                ensureTemplateTokenIsMutable();
                this.templateToken_.add(index, builderForValue.build());
                return this;
            }

            public Builder addAllTemplateToken(Iterable<? extends Value> values) {
                ensureTemplateTokenIsMutable();
                com.google.tagmanager.protobuf.AbstractMessageLite.Builder.addAll(values, this.templateToken_);
                return this;
            }

            public Builder clearTemplateToken() {
                this.templateToken_ = Collections.emptyList();
                this.bitField0_ &= -513;
                return this;
            }

            public Builder removeTemplateToken(int index) {
                ensureTemplateTokenIsMutable();
                this.templateToken_.remove(index);
                return this;
            }

            private void ensureEscapingIsMutable() {
                if ((this.bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) != AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) {
                    this.escaping_ = new ArrayList(this.escaping_);
                    this.bitField0_ |= AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END;
                }
            }

            public List<Escaping> getEscapingList() {
                return Collections.unmodifiableList(this.escaping_);
            }

            public int getEscapingCount() {
                return this.escaping_.size();
            }

            public Escaping getEscaping(int index) {
                return (Escaping) this.escaping_.get(index);
            }

            public Builder setEscaping(int index, Escaping value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureEscapingIsMutable();
                this.escaping_.set(index, value);
                return this;
            }

            public Builder addEscaping(Escaping value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureEscapingIsMutable();
                this.escaping_.add(value);
                return this;
            }

            public Builder addAllEscaping(Iterable<? extends Escaping> values) {
                ensureEscapingIsMutable();
                com.google.tagmanager.protobuf.AbstractMessageLite.Builder.addAll(values, this.escaping_);
                return this;
            }

            public Builder clearEscaping() {
                this.escaping_ = Collections.emptyList();
                this.bitField0_ &= -1025;
                return this;
            }

            public boolean hasContainsReferences() {
                return (this.bitField0_ & AccessibilityEventCompat.TYPE_WINDOW_CONTENT_CHANGED) == AccessibilityEventCompat.TYPE_WINDOW_CONTENT_CHANGED;
            }

            public boolean getContainsReferences() {
                return this.containsReferences_;
            }

            public Builder setContainsReferences(boolean value) {
                this.bitField0_ |= AccessibilityEventCompat.TYPE_WINDOW_CONTENT_CHANGED;
                this.containsReferences_ = value;
                return this;
            }

            public Builder clearContainsReferences() {
                this.bitField0_ &= -2049;
                this.containsReferences_ = false;
                return this;
            }
        }

        private Value(ExtendableBuilder<Value, ?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Value(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static Value getDefaultInstance() {
            return defaultInstance;
        }

        public Value getDefaultInstanceForType() {
            return defaultInstance;
        }

        private Value(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            OutputStream unknownFieldsOutput = ByteString.newOutput();
            CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.newInstance(unknownFieldsOutput);
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    int rawValue;
                    ByteString bs;
                    Escaping value;
                    switch (tag) {
                        case MutableDateTime.ROUND_NONE /*0*/:
                            done = true;
                            break;
                        case INTEGER_FIELD_NUMBER /*8*/:
                            rawValue = input.readEnum();
                            Type value2 = Type.valueOf(rawValue);
                            if (value2 != null) {
                                this.bitField0_ |= TYPE_FIELD_NUMBER;
                                this.type_ = value2;
                                break;
                            }
                            unknownFieldsCodedOutput.writeRawVarint32(tag);
                            unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                            break;
                        case Resource.ENABLE_AUTO_EVENT_TRACKING_FIELD_NUMBER /*18*/:
                            bs = input.readBytes();
                            this.bitField0_ |= STRING_FIELD_NUMBER;
                            this.string_ = bs;
                            break;
                        case 26:
                            if ((mutable_bitField0_ & MAP_KEY_FIELD_NUMBER) != MAP_KEY_FIELD_NUMBER) {
                                this.listItem_ = new ArrayList();
                                mutable_bitField0_ |= MAP_KEY_FIELD_NUMBER;
                            }
                            this.listItem_.add(input.readMessage(PARSER, extensionRegistry));
                            break;
                        case 34:
                            if ((mutable_bitField0_ & INTEGER_FIELD_NUMBER) != INTEGER_FIELD_NUMBER) {
                                this.mapKey_ = new ArrayList();
                                mutable_bitField0_ |= INTEGER_FIELD_NUMBER;
                            }
                            this.mapKey_.add(input.readMessage(PARSER, extensionRegistry));
                            break;
                        case 42:
                            if ((mutable_bitField0_ & 16) != 16) {
                                this.mapValue_ = new ArrayList();
                                mutable_bitField0_ |= 16;
                            }
                            this.mapValue_.add(input.readMessage(PARSER, extensionRegistry));
                            break;
                        case 50:
                            bs = input.readBytes();
                            this.bitField0_ |= MAP_KEY_FIELD_NUMBER;
                            this.macroReference_ = bs;
                            break;
                        case 58:
                            bs = input.readBytes();
                            this.bitField0_ |= INTEGER_FIELD_NUMBER;
                            this.functionId_ = bs;
                            break;
                        case 64:
                            this.bitField0_ |= 16;
                            this.integer_ = input.readInt64();
                            break;
                        case 72:
                            this.bitField0_ |= 64;
                            this.containsReferences_ = input.readBool();
                            break;
                        case 80:
                            rawValue = input.readEnum();
                            value = Escaping.valueOf(rawValue);
                            if (value != null) {
                                if ((mutable_bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) != AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) {
                                    this.escaping_ = new ArrayList();
                                    mutable_bitField0_ |= AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END;
                                }
                                this.escaping_.add(value);
                                break;
                            }
                            unknownFieldsCodedOutput.writeRawVarint32(tag);
                            unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                            break;
                        case 82:
                            int oldLimit = input.pushLimit(input.readRawVarint32());
                            while (input.getBytesUntilLimit() > 0) {
                                rawValue = input.readEnum();
                                value = Escaping.valueOf(rawValue);
                                if (value == null) {
                                    unknownFieldsCodedOutput.writeRawVarint32(tag);
                                    unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                                } else {
                                    if ((mutable_bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) != AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) {
                                        this.escaping_ = new ArrayList();
                                        mutable_bitField0_ |= AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END;
                                    }
                                    this.escaping_.add(value);
                                }
                            }
                            input.popLimit(oldLimit);
                            break;
                        case 90:
                            if ((mutable_bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START) != AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START) {
                                this.templateToken_ = new ArrayList();
                                mutable_bitField0_ |= AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START;
                            }
                            this.templateToken_.add(input.readMessage(PARSER, extensionRegistry));
                            break;
                        case 96:
                            this.bitField0_ |= 32;
                            this.boolean_ = input.readBool();
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & MAP_KEY_FIELD_NUMBER) == MAP_KEY_FIELD_NUMBER) {
                        this.listItem_ = Collections.unmodifiableList(this.listItem_);
                    }
                    if ((mutable_bitField0_ & INTEGER_FIELD_NUMBER) == INTEGER_FIELD_NUMBER) {
                        this.mapKey_ = Collections.unmodifiableList(this.mapKey_);
                    }
                    if ((mutable_bitField0_ & 16) == 16) {
                        this.mapValue_ = Collections.unmodifiableList(this.mapValue_);
                    }
                    if ((mutable_bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) == 1024) {
                        this.escaping_ = Collections.unmodifiableList(this.escaping_);
                    }
                    if ((mutable_bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START) == 512) {
                        this.templateToken_ = Collections.unmodifiableList(this.templateToken_);
                    }
                    try {
                        unknownFieldsCodedOutput.flush();
                    } catch (IOException e3) {
                        makeExtensionsImmutable();
                    } finally {
                        this.unknownFields = unknownFieldsOutput.toByteString();
                    }
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & MAP_KEY_FIELD_NUMBER) == MAP_KEY_FIELD_NUMBER) {
                this.listItem_ = Collections.unmodifiableList(this.listItem_);
            }
            if ((mutable_bitField0_ & INTEGER_FIELD_NUMBER) == INTEGER_FIELD_NUMBER) {
                this.mapKey_ = Collections.unmodifiableList(this.mapKey_);
            }
            if ((mutable_bitField0_ & 16) == 16) {
                this.mapValue_ = Collections.unmodifiableList(this.mapValue_);
            }
            if ((mutable_bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) == AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END) {
                this.escaping_ = Collections.unmodifiableList(this.escaping_);
            }
            if ((mutable_bitField0_ & AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START) == AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START) {
                this.templateToken_ = Collections.unmodifiableList(this.templateToken_);
            }
            try {
                unknownFieldsCodedOutput.flush();
            } catch (IOException e4) {
            } finally {
                this.unknownFields = unknownFieldsOutput.toByteString();
            }
            makeExtensionsImmutable();
        }

        static {
            PARSER = new C08801();
            mutableDefault = null;
            defaultInstance = new Value(true);
            defaultInstance.initFields();
        }

        public Parser<Value> getParserForType() {
            return PARSER;
        }

        public boolean hasType() {
            return (this.bitField0_ & TYPE_FIELD_NUMBER) == TYPE_FIELD_NUMBER;
        }

        public Type getType() {
            return this.type_;
        }

        public boolean hasString() {
            return (this.bitField0_ & STRING_FIELD_NUMBER) == STRING_FIELD_NUMBER;
        }

        public String getString() {
            ByteString ref = this.string_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.string_ = s;
            }
            return s;
        }

        public ByteString getStringBytes() {
            Object ref = this.string_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.string_ = b;
            return b;
        }

        public List<Value> getListItemList() {
            return this.listItem_;
        }

        public List<? extends ValueOrBuilder> getListItemOrBuilderList() {
            return this.listItem_;
        }

        public int getListItemCount() {
            return this.listItem_.size();
        }

        public Value getListItem(int index) {
            return (Value) this.listItem_.get(index);
        }

        public ValueOrBuilder getListItemOrBuilder(int index) {
            return (ValueOrBuilder) this.listItem_.get(index);
        }

        public List<Value> getMapKeyList() {
            return this.mapKey_;
        }

        public List<? extends ValueOrBuilder> getMapKeyOrBuilderList() {
            return this.mapKey_;
        }

        public int getMapKeyCount() {
            return this.mapKey_.size();
        }

        public Value getMapKey(int index) {
            return (Value) this.mapKey_.get(index);
        }

        public ValueOrBuilder getMapKeyOrBuilder(int index) {
            return (ValueOrBuilder) this.mapKey_.get(index);
        }

        public List<Value> getMapValueList() {
            return this.mapValue_;
        }

        public List<? extends ValueOrBuilder> getMapValueOrBuilderList() {
            return this.mapValue_;
        }

        public int getMapValueCount() {
            return this.mapValue_.size();
        }

        public Value getMapValue(int index) {
            return (Value) this.mapValue_.get(index);
        }

        public ValueOrBuilder getMapValueOrBuilder(int index) {
            return (ValueOrBuilder) this.mapValue_.get(index);
        }

        public boolean hasMacroReference() {
            return (this.bitField0_ & MAP_KEY_FIELD_NUMBER) == MAP_KEY_FIELD_NUMBER;
        }

        public String getMacroReference() {
            ByteString ref = this.macroReference_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.macroReference_ = s;
            }
            return s;
        }

        public ByteString getMacroReferenceBytes() {
            Object ref = this.macroReference_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.macroReference_ = b;
            return b;
        }

        public boolean hasFunctionId() {
            return (this.bitField0_ & INTEGER_FIELD_NUMBER) == INTEGER_FIELD_NUMBER;
        }

        public String getFunctionId() {
            ByteString ref = this.functionId_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.functionId_ = s;
            }
            return s;
        }

        public ByteString getFunctionIdBytes() {
            Object ref = this.functionId_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.functionId_ = b;
            return b;
        }

        public boolean hasInteger() {
            return (this.bitField0_ & 16) == 16;
        }

        public long getInteger() {
            return this.integer_;
        }

        public boolean hasBoolean() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean getBoolean() {
            return this.boolean_;
        }

        public List<Value> getTemplateTokenList() {
            return this.templateToken_;
        }

        public List<? extends ValueOrBuilder> getTemplateTokenOrBuilderList() {
            return this.templateToken_;
        }

        public int getTemplateTokenCount() {
            return this.templateToken_.size();
        }

        public Value getTemplateToken(int index) {
            return (Value) this.templateToken_.get(index);
        }

        public ValueOrBuilder getTemplateTokenOrBuilder(int index) {
            return (ValueOrBuilder) this.templateToken_.get(index);
        }

        public List<Escaping> getEscapingList() {
            return this.escaping_;
        }

        public int getEscapingCount() {
            return this.escaping_.size();
        }

        public Escaping getEscaping(int index) {
            return (Escaping) this.escaping_.get(index);
        }

        public boolean hasContainsReferences() {
            return (this.bitField0_ & 64) == 64;
        }

        public boolean getContainsReferences() {
            return this.containsReferences_;
        }

        private void initFields() {
            this.type_ = Type.STRING;
            this.string_ = StringUtils.EMPTY;
            this.listItem_ = Collections.emptyList();
            this.mapKey_ = Collections.emptyList();
            this.mapValue_ = Collections.emptyList();
            this.macroReference_ = StringUtils.EMPTY;
            this.functionId_ = StringUtils.EMPTY;
            this.integer_ = 0;
            this.boolean_ = false;
            this.templateToken_ = Collections.emptyList();
            this.escaping_ = Collections.emptyList();
            this.containsReferences_ = false;
        }

        public final boolean isInitialized() {
            boolean z = true;
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized != -1) {
                if (isInitialized != (byte) 1) {
                    z = false;
                }
                return z;
            } else if (hasType()) {
                int i = 0;
                while (i < getListItemCount()) {
                    if (getListItem(i).isInitialized()) {
                        i += TYPE_FIELD_NUMBER;
                    } else {
                        this.memoizedIsInitialized = (byte) 0;
                        return false;
                    }
                }
                i = 0;
                while (i < getMapKeyCount()) {
                    if (getMapKey(i).isInitialized()) {
                        i += TYPE_FIELD_NUMBER;
                    } else {
                        this.memoizedIsInitialized = (byte) 0;
                        return false;
                    }
                }
                i = 0;
                while (i < getMapValueCount()) {
                    if (getMapValue(i).isInitialized()) {
                        i += TYPE_FIELD_NUMBER;
                    } else {
                        this.memoizedIsInitialized = (byte) 0;
                        return false;
                    }
                }
                i = 0;
                while (i < getTemplateTokenCount()) {
                    if (getTemplateToken(i).isInitialized()) {
                        i += TYPE_FIELD_NUMBER;
                    } else {
                        this.memoizedIsInitialized = (byte) 0;
                        return false;
                    }
                }
                if (extensionsAreInitialized()) {
                    this.memoizedIsInitialized = (byte) 1;
                    return true;
                }
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            getSerializedSize();
            ExtensionWriter extensionWriter = newExtensionWriter();
            if ((this.bitField0_ & TYPE_FIELD_NUMBER) == TYPE_FIELD_NUMBER) {
                output.writeEnum(TYPE_FIELD_NUMBER, this.type_.getNumber());
            }
            if ((this.bitField0_ & STRING_FIELD_NUMBER) == STRING_FIELD_NUMBER) {
                output.writeBytes(STRING_FIELD_NUMBER, getStringBytes());
            }
            for (i = 0; i < this.listItem_.size(); i += TYPE_FIELD_NUMBER) {
                output.writeMessage(LIST_ITEM_FIELD_NUMBER, (MessageLite) this.listItem_.get(i));
            }
            for (i = 0; i < this.mapKey_.size(); i += TYPE_FIELD_NUMBER) {
                output.writeMessage(MAP_KEY_FIELD_NUMBER, (MessageLite) this.mapKey_.get(i));
            }
            for (i = 0; i < this.mapValue_.size(); i += TYPE_FIELD_NUMBER) {
                output.writeMessage(MAP_VALUE_FIELD_NUMBER, (MessageLite) this.mapValue_.get(i));
            }
            if ((this.bitField0_ & MAP_KEY_FIELD_NUMBER) == MAP_KEY_FIELD_NUMBER) {
                output.writeBytes(MACRO_REFERENCE_FIELD_NUMBER, getMacroReferenceBytes());
            }
            if ((this.bitField0_ & INTEGER_FIELD_NUMBER) == INTEGER_FIELD_NUMBER) {
                output.writeBytes(FUNCTION_ID_FIELD_NUMBER, getFunctionIdBytes());
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeInt64(INTEGER_FIELD_NUMBER, this.integer_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeBool(CONTAINS_REFERENCES_FIELD_NUMBER, this.containsReferences_);
            }
            for (i = 0; i < this.escaping_.size(); i += TYPE_FIELD_NUMBER) {
                output.writeEnum(ESCAPING_FIELD_NUMBER, ((Escaping) this.escaping_.get(i)).getNumber());
            }
            for (i = 0; i < this.templateToken_.size(); i += TYPE_FIELD_NUMBER) {
                output.writeMessage(TEMPLATE_TOKEN_FIELD_NUMBER, (MessageLite) this.templateToken_.get(i));
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBool(BOOLEAN_FIELD_NUMBER, this.boolean_);
            }
            extensionWriter.writeUntil(536870912, output);
            output.writeRawBytes(this.unknownFields);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            int i;
            size = 0;
            if ((this.bitField0_ & TYPE_FIELD_NUMBER) == TYPE_FIELD_NUMBER) {
                size = 0 + CodedOutputStream.computeEnumSize(TYPE_FIELD_NUMBER, this.type_.getNumber());
            }
            if ((this.bitField0_ & STRING_FIELD_NUMBER) == STRING_FIELD_NUMBER) {
                size += CodedOutputStream.computeBytesSize(STRING_FIELD_NUMBER, getStringBytes());
            }
            for (i = 0; i < this.listItem_.size(); i += TYPE_FIELD_NUMBER) {
                size += CodedOutputStream.computeMessageSize(LIST_ITEM_FIELD_NUMBER, (MessageLite) this.listItem_.get(i));
            }
            for (i = 0; i < this.mapKey_.size(); i += TYPE_FIELD_NUMBER) {
                size += CodedOutputStream.computeMessageSize(MAP_KEY_FIELD_NUMBER, (MessageLite) this.mapKey_.get(i));
            }
            for (i = 0; i < this.mapValue_.size(); i += TYPE_FIELD_NUMBER) {
                size += CodedOutputStream.computeMessageSize(MAP_VALUE_FIELD_NUMBER, (MessageLite) this.mapValue_.get(i));
            }
            if ((this.bitField0_ & MAP_KEY_FIELD_NUMBER) == MAP_KEY_FIELD_NUMBER) {
                size += CodedOutputStream.computeBytesSize(MACRO_REFERENCE_FIELD_NUMBER, getMacroReferenceBytes());
            }
            if ((this.bitField0_ & INTEGER_FIELD_NUMBER) == INTEGER_FIELD_NUMBER) {
                size += CodedOutputStream.computeBytesSize(FUNCTION_ID_FIELD_NUMBER, getFunctionIdBytes());
            }
            if ((this.bitField0_ & 16) == 16) {
                size += CodedOutputStream.computeInt64Size(INTEGER_FIELD_NUMBER, this.integer_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size += CodedOutputStream.computeBoolSize(CONTAINS_REFERENCES_FIELD_NUMBER, this.containsReferences_);
            }
            int dataSize = 0;
            for (i = 0; i < this.escaping_.size(); i += TYPE_FIELD_NUMBER) {
                dataSize += CodedOutputStream.computeEnumSizeNoTag(((Escaping) this.escaping_.get(i)).getNumber());
            }
            size = (size + dataSize) + (this.escaping_.size() * TYPE_FIELD_NUMBER);
            for (i = 0; i < this.templateToken_.size(); i += TYPE_FIELD_NUMBER) {
                size += CodedOutputStream.computeMessageSize(TEMPLATE_TOKEN_FIELD_NUMBER, (MessageLite) this.templateToken_.get(i));
            }
            if ((this.bitField0_ & 32) == 32) {
                size += CodedOutputStream.computeBoolSize(BOOLEAN_FIELD_NUMBER, this.boolean_);
            }
            size = (size + extensionsSerializedSize()) + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Value)) {
                return super.equals(obj);
            }
            Value other = (Value) obj;
            boolean result = true && hasType() == other.hasType();
            if (hasType()) {
                result = result && getType() == other.getType();
            }
            if (result && hasString() == other.hasString()) {
                result = true;
            } else {
                result = false;
            }
            if (hasString()) {
                if (result && getString().equals(other.getString())) {
                    result = true;
                } else {
                    result = false;
                }
            }
            if (result && getListItemList().equals(other.getListItemList())) {
                result = true;
            } else {
                result = false;
            }
            if (result && getMapKeyList().equals(other.getMapKeyList())) {
                result = true;
            } else {
                result = false;
            }
            if (result && getMapValueList().equals(other.getMapValueList())) {
                result = true;
            } else {
                result = false;
            }
            if (result && hasMacroReference() == other.hasMacroReference()) {
                result = true;
            } else {
                result = false;
            }
            if (hasMacroReference()) {
                if (result && getMacroReference().equals(other.getMacroReference())) {
                    result = true;
                } else {
                    result = false;
                }
            }
            if (result && hasFunctionId() == other.hasFunctionId()) {
                result = true;
            } else {
                result = false;
            }
            if (hasFunctionId()) {
                if (result && getFunctionId().equals(other.getFunctionId())) {
                    result = true;
                } else {
                    result = false;
                }
            }
            if (result && hasInteger() == other.hasInteger()) {
                result = true;
            } else {
                result = false;
            }
            if (hasInteger()) {
                if (result && getInteger() == other.getInteger()) {
                    result = true;
                } else {
                    result = false;
                }
            }
            if (result && hasBoolean() == other.hasBoolean()) {
                result = true;
            } else {
                result = false;
            }
            if (hasBoolean()) {
                if (result && getBoolean() == other.getBoolean()) {
                    result = true;
                } else {
                    result = false;
                }
            }
            if (result && getTemplateTokenList().equals(other.getTemplateTokenList())) {
                result = true;
            } else {
                result = false;
            }
            if (result && getEscapingList().equals(other.getEscapingList())) {
                result = true;
            } else {
                result = false;
            }
            if (result && hasContainsReferences() == other.hasContainsReferences()) {
                result = true;
            } else {
                result = false;
            }
            if (hasContainsReferences()) {
                if (result && getContainsReferences() == other.getContainsReferences()) {
                    result = true;
                } else {
                    result = false;
                }
            }
            return result;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = Value.class.hashCode() + 779;
            if (hasType()) {
                hash = (((hash * 37) + TYPE_FIELD_NUMBER) * 53) + Internal.hashEnum(getType());
            }
            if (hasString()) {
                hash = (((hash * 37) + STRING_FIELD_NUMBER) * 53) + getString().hashCode();
            }
            if (getListItemCount() > 0) {
                hash = (((hash * 37) + LIST_ITEM_FIELD_NUMBER) * 53) + getListItemList().hashCode();
            }
            if (getMapKeyCount() > 0) {
                hash = (((hash * 37) + MAP_KEY_FIELD_NUMBER) * 53) + getMapKeyList().hashCode();
            }
            if (getMapValueCount() > 0) {
                hash = (((hash * 37) + MAP_VALUE_FIELD_NUMBER) * 53) + getMapValueList().hashCode();
            }
            if (hasMacroReference()) {
                hash = (((hash * 37) + MACRO_REFERENCE_FIELD_NUMBER) * 53) + getMacroReference().hashCode();
            }
            if (hasFunctionId()) {
                hash = (((hash * 37) + FUNCTION_ID_FIELD_NUMBER) * 53) + getFunctionId().hashCode();
            }
            if (hasInteger()) {
                hash = (((hash * 37) + INTEGER_FIELD_NUMBER) * 53) + Internal.hashLong(getInteger());
            }
            if (hasBoolean()) {
                hash = (((hash * 37) + BOOLEAN_FIELD_NUMBER) * 53) + Internal.hashBoolean(getBoolean());
            }
            if (getTemplateTokenCount() > 0) {
                hash = (((hash * 37) + TEMPLATE_TOKEN_FIELD_NUMBER) * 53) + getTemplateTokenList().hashCode();
            }
            if (getEscapingCount() > 0) {
                hash = (((hash * 37) + ESCAPING_FIELD_NUMBER) * 53) + Internal.hashEnumList(getEscapingList());
            }
            if (hasContainsReferences()) {
                hash = (((hash * 37) + CONTAINS_REFERENCES_FIELD_NUMBER) * 53) + Internal.hashBoolean(getContainsReferences());
            }
            hash = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash;
            return hash;
        }

        protected MutableMessageLite internalMutableDefault() {
            if (mutableDefault == null) {
                mutableDefault = GeneratedMessageLite.internalMutableDefault("com.google.analytics.midtier.proto.containertag.MutableTypeSystem$Value");
            }
            return mutableDefault;
        }

        public static Value parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Value) PARSER.parseFrom(data);
        }

        public static Value parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Value) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Value parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Value) PARSER.parseFrom(data);
        }

        public static Value parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Value) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Value parseFrom(InputStream input) throws IOException {
            return (Value) PARSER.parseFrom(input);
        }

        public static Value parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Value) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Value parseDelimitedFrom(InputStream input) throws IOException {
            return (Value) PARSER.parseDelimitedFrom(input);
        }

        public static Value parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Value) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static Value parseFrom(CodedInputStream input) throws IOException {
            return (Value) PARSER.parseFrom(input);
        }

        public static Value parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Value) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(Value prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }
    }

    private TypeSystem() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    static {
    }
}
