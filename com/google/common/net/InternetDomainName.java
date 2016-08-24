package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ClassUtils;

@GwtCompatible
@Beta
public final class InternetDomainName {
    private static final CharMatcher DASH_MATCHER;
    private static final CharMatcher DOTS_MATCHER;
    private static final Joiner DOT_JOINER;
    private static final String DOT_REGEX = "\\.";
    private static final Splitter DOT_SPLITTER;
    private static final int MAX_DOMAIN_PART_LENGTH = 63;
    private static final int MAX_LENGTH = 253;
    private static final int MAX_PARTS = 127;
    private static final int NO_PUBLIC_SUFFIX_FOUND = -1;
    private static final CharMatcher PART_CHAR_MATCHER;
    private final String name;
    private final ImmutableList<String> parts;
    private final int publicSuffixIndex;

    static {
        DOTS_MATCHER = CharMatcher.anyOf(".\u3002\uff0e\uff61");
        DOT_SPLITTER = Splitter.on((char) ClassUtils.PACKAGE_SEPARATOR_CHAR);
        DOT_JOINER = Joiner.on((char) ClassUtils.PACKAGE_SEPARATOR_CHAR);
        DASH_MATCHER = CharMatcher.anyOf("-_");
        PART_CHAR_MATCHER = CharMatcher.JAVA_LETTER_OR_DIGIT.or(DASH_MATCHER);
    }

    InternetDomainName(String name) {
        boolean z;
        name = Ascii.toLowerCase(DOTS_MATCHER.replaceFrom((CharSequence) name, (char) ClassUtils.PACKAGE_SEPARATOR_CHAR));
        if (name.endsWith(".")) {
            name = name.substring(0, name.length() + NO_PUBLIC_SUFFIX_FOUND);
        }
        Preconditions.checkArgument(name.length() <= MAX_LENGTH, "Domain name too long: '%s':", name);
        this.name = name;
        this.parts = ImmutableList.copyOf(DOT_SPLITTER.split(name));
        if (this.parts.size() <= MAX_PARTS) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Domain has too many parts: '%s'", name);
        Preconditions.checkArgument(validateSyntax(this.parts), "Not a valid domain name: '%s'", name);
        this.publicSuffixIndex = findPublicSuffix();
    }

    private int findPublicSuffix() {
        int partsSize = this.parts.size();
        for (int i = 0; i < partsSize; i++) {
            String ancestorName = DOT_JOINER.join(this.parts.subList(i, partsSize));
            if (PublicSuffixPatterns.EXACT.containsKey(ancestorName)) {
                return i;
            }
            if (PublicSuffixPatterns.EXCLUDED.containsKey(ancestorName)) {
                return i + 1;
            }
            if (matchesWildcardPublicSuffix(ancestorName)) {
                return i;
            }
        }
        return NO_PUBLIC_SUFFIX_FOUND;
    }

    public static InternetDomainName from(String domain) {
        return new InternetDomainName((String) Preconditions.checkNotNull(domain));
    }

    private static boolean validateSyntax(List<String> parts) {
        int lastIndex = parts.size() + NO_PUBLIC_SUFFIX_FOUND;
        if (!validatePart((String) parts.get(lastIndex), true)) {
            return false;
        }
        for (int i = 0; i < lastIndex; i++) {
            if (!validatePart((String) parts.get(i), false)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validatePart(String part, boolean isFinalPart) {
        if (part.length() < 1 || part.length() > MAX_DOMAIN_PART_LENGTH) {
            return false;
        }
        if (!PART_CHAR_MATCHER.matchesAllOf(CharMatcher.ASCII.retainFrom(part)) || DASH_MATCHER.matches(part.charAt(0)) || DASH_MATCHER.matches(part.charAt(part.length() + NO_PUBLIC_SUFFIX_FOUND))) {
            return false;
        }
        if (isFinalPart && CharMatcher.DIGIT.matches(part.charAt(0))) {
            return false;
        }
        return true;
    }

    public ImmutableList<String> parts() {
        return this.parts;
    }

    public boolean isPublicSuffix() {
        return this.publicSuffixIndex == 0;
    }

    public boolean hasPublicSuffix() {
        return this.publicSuffixIndex != NO_PUBLIC_SUFFIX_FOUND;
    }

    public InternetDomainName publicSuffix() {
        return hasPublicSuffix() ? ancestor(this.publicSuffixIndex) : null;
    }

    public boolean isUnderPublicSuffix() {
        return this.publicSuffixIndex > 0;
    }

    public boolean isTopPrivateDomain() {
        return this.publicSuffixIndex == 1;
    }

    public InternetDomainName topPrivateDomain() {
        if (isTopPrivateDomain()) {
            return this;
        }
        Preconditions.checkState(isUnderPublicSuffix(), "Not under a public suffix: %s", this.name);
        return ancestor(this.publicSuffixIndex + NO_PUBLIC_SUFFIX_FOUND);
    }

    public boolean hasParent() {
        return this.parts.size() > 1;
    }

    public InternetDomainName parent() {
        Preconditions.checkState(hasParent(), "Domain '%s' has no parent", this.name);
        return ancestor(1);
    }

    private InternetDomainName ancestor(int levels) {
        return from(DOT_JOINER.join(this.parts.subList(levels, this.parts.size())));
    }

    public InternetDomainName child(String leftParts) {
        return from(((String) Preconditions.checkNotNull(leftParts)) + "." + this.name);
    }

    public static boolean isValid(String name) {
        try {
            from(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static boolean matchesWildcardPublicSuffix(String domain) {
        String[] pieces = domain.split(DOT_REGEX, 2);
        if (pieces.length == 2 && PublicSuffixPatterns.UNDER.containsKey(pieces[1])) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof InternetDomainName)) {
            return false;
        }
        return this.name.equals(((InternetDomainName) object).name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}
