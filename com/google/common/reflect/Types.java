package com.google.common.reflect;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Iterables;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ClassUtils;

final class Types {
    private static final Joiner COMMA_JOINER;
    private static final Function<Type, String> TYPE_TO_STRING;

    private enum ClassOwnership {
        OWNED_BY_ENCLOSING_CLASS {
            @Nullable
            Class<?> getOwnerType(Class<?> rawType) {
                return rawType.getEnclosingClass();
            }
        },
        LOCAL_CLASS_HAS_NO_OWNER {
            @Nullable
            Class<?> getOwnerType(Class<?> rawType) {
                if (rawType.isLocalClass()) {
                    return null;
                }
                return rawType.getEnclosingClass();
            }
        };
        
        static final ClassOwnership JVM_BEHAVIOR;

        /* renamed from: com.google.common.reflect.Types.ClassOwnership.3 */
        static class C07973 extends AnonymousClass1LocalClass<String> {
            C07973() {
            }
        }

        @Nullable
        abstract Class<?> getOwnerType(Class<?> cls);

        static {
            JVM_BEHAVIOR = detectJvmBehavior();
        }

        private static ClassOwnership detectJvmBehavior() {
            ParameterizedType parameterizedType = (ParameterizedType) new C07973().getClass().getGenericSuperclass();
            for (ClassOwnership behavior : values()) {
                if (behavior.getOwnerType(AnonymousClass1LocalClass.class) == parameterizedType.getOwnerType()) {
                    return behavior;
                }
            }
            throw new AssertionError();
        }
    }

    private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
        private static final long serialVersionUID = 0;
        private final Type componentType;

        GenericArrayTypeImpl(Type componentType) {
            this.componentType = JavaVersion.CURRENT.usedInGenericType(componentType);
        }

        public Type getGenericComponentType() {
            return this.componentType;
        }

        public String toString() {
            return Types.toString(this.componentType) + "[]";
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof GenericArrayType)) {
                return false;
            }
            return Objects.equal(getGenericComponentType(), ((GenericArrayType) obj).getGenericComponentType());
        }
    }

    enum JavaVersion {
        JAVA6 {
            GenericArrayType newArrayType(Type componentType) {
                return new GenericArrayTypeImpl(componentType);
            }

            Type usedInGenericType(Type type) {
                Preconditions.checkNotNull(type);
                if (!(type instanceof Class)) {
                    return type;
                }
                Class<?> cls = (Class) type;
                if (cls.isArray()) {
                    return new GenericArrayTypeImpl(cls.getComponentType());
                }
                return type;
            }
        },
        JAVA7 {
            Type newArrayType(Type componentType) {
                if (componentType instanceof Class) {
                    return Types.getArrayClass((Class) componentType);
                }
                return new GenericArrayTypeImpl(componentType);
            }

            Type usedInGenericType(Type type) {
                return (Type) Preconditions.checkNotNull(type);
            }
        };
        
        static final JavaVersion CURRENT;

        /* renamed from: com.google.common.reflect.Types.JavaVersion.1 */
        static class C07981 extends TypeCapture<int[]> {
            C07981() {
            }
        }

        abstract Type newArrayType(Type type);

        abstract Type usedInGenericType(Type type);

        final ImmutableList<Type> usedInGenericType(Type[] types) {
            Builder<Type> builder = ImmutableList.builder();
            for (Type type : types) {
                builder.add(usedInGenericType(type));
            }
            return builder.build();
        }
    }

    static final class NativeTypeVariableEquals<X> {
        static final boolean NATIVE_TYPE_VARIABLE_ONLY;

        NativeTypeVariableEquals() {
        }

        static {
            boolean z = false;
            if (!NativeTypeVariableEquals.class.getTypeParameters()[0].equals(Types.newArtificialTypeVariable(NativeTypeVariableEquals.class, "X", new Type[0]))) {
                z = true;
            }
            NATIVE_TYPE_VARIABLE_ONLY = z;
        }
    }

    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
        private static final long serialVersionUID = 0;
        private final ImmutableList<Type> argumentsList;
        private final Type ownerType;
        private final Class<?> rawType;

        ParameterizedTypeImpl(@Nullable Type ownerType, Class<?> rawType, Type[] typeArguments) {
            Preconditions.checkNotNull(rawType);
            Preconditions.checkArgument(typeArguments.length == rawType.getTypeParameters().length);
            Types.disallowPrimitiveType(typeArguments, "type parameter");
            this.ownerType = ownerType;
            this.rawType = rawType;
            this.argumentsList = JavaVersion.CURRENT.usedInGenericType(typeArguments);
        }

        public Type[] getActualTypeArguments() {
            return Types.toArray(this.argumentsList);
        }

        public Type getRawType() {
            return this.rawType;
        }

        public Type getOwnerType() {
            return this.ownerType;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (this.ownerType != null) {
                builder.append(Types.toString(this.ownerType)).append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
            }
            builder.append(this.rawType.getName()).append('<').append(Types.COMMA_JOINER.join(Iterables.transform(this.argumentsList, Types.TYPE_TO_STRING))).append('>');
            return builder.toString();
        }

        public int hashCode() {
            return ((this.ownerType == null ? 0 : this.ownerType.hashCode()) ^ this.argumentsList.hashCode()) ^ this.rawType.hashCode();
        }

        public boolean equals(Object other) {
            if (!(other instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType that = (ParameterizedType) other;
            if (getRawType().equals(that.getRawType()) && Objects.equal(getOwnerType(), that.getOwnerType()) && Arrays.equals(getActualTypeArguments(), that.getActualTypeArguments())) {
                return true;
            }
            return false;
        }
    }

    private static final class TypeVariableImpl<D extends GenericDeclaration> implements TypeVariable<D> {
        private final ImmutableList<Type> bounds;
        private final D genericDeclaration;
        private final String name;

        TypeVariableImpl(D genericDeclaration, String name, Type[] bounds) {
            Types.disallowPrimitiveType(bounds, "bound for type variable");
            this.genericDeclaration = (GenericDeclaration) Preconditions.checkNotNull(genericDeclaration);
            this.name = (String) Preconditions.checkNotNull(name);
            this.bounds = ImmutableList.copyOf((Object[]) bounds);
        }

        public Type[] getBounds() {
            return Types.toArray(this.bounds);
        }

        public D getGenericDeclaration() {
            return this.genericDeclaration;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        public int hashCode() {
            return this.genericDeclaration.hashCode() ^ this.name.hashCode();
        }

        public boolean equals(Object obj) {
            if (NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY) {
                if (!(obj instanceof TypeVariableImpl)) {
                    return false;
                }
                TypeVariableImpl<?> that = (TypeVariableImpl) obj;
                if (this.name.equals(that.getName()) && this.genericDeclaration.equals(that.getGenericDeclaration()) && this.bounds.equals(that.bounds)) {
                    return true;
                }
                return false;
            } else if (!(obj instanceof TypeVariable)) {
                return false;
            } else {
                TypeVariable<?> that2 = (TypeVariable) obj;
                if (this.name.equals(that2.getName()) && this.genericDeclaration.equals(that2.getGenericDeclaration())) {
                    return true;
                }
                return false;
            }
        }
    }

    static final class WildcardTypeImpl implements WildcardType, Serializable {
        private static final long serialVersionUID = 0;
        private final ImmutableList<Type> lowerBounds;
        private final ImmutableList<Type> upperBounds;

        WildcardTypeImpl(Type[] lowerBounds, Type[] upperBounds) {
            Types.disallowPrimitiveType(lowerBounds, "lower bound for wildcard");
            Types.disallowPrimitiveType(upperBounds, "upper bound for wildcard");
            this.lowerBounds = JavaVersion.CURRENT.usedInGenericType(lowerBounds);
            this.upperBounds = JavaVersion.CURRENT.usedInGenericType(upperBounds);
        }

        public Type[] getLowerBounds() {
            return Types.toArray(this.lowerBounds);
        }

        public Type[] getUpperBounds() {
            return Types.toArray(this.upperBounds);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof WildcardType)) {
                return false;
            }
            WildcardType that = (WildcardType) obj;
            if (this.lowerBounds.equals(Arrays.asList(that.getLowerBounds())) && this.upperBounds.equals(Arrays.asList(that.getUpperBounds()))) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
        }

        public String toString() {
            StringBuilder builder = new StringBuilder("?");
            Iterator i$ = this.lowerBounds.iterator();
            while (i$.hasNext()) {
                builder.append(" super ").append(Types.toString((Type) i$.next()));
            }
            for (Type upperBound : Types.filterUpperBounds(this.upperBounds)) {
                builder.append(" extends ").append(Types.toString(upperBound));
            }
            return builder.toString();
        }
    }

    /* renamed from: com.google.common.reflect.Types.1 */
    static class C07931 implements Function<Type, String> {
        C07931() {
        }

        public String apply(Type from) {
            return Types.toString(from);
        }
    }

    /* renamed from: com.google.common.reflect.Types.2 */
    static class C07942 extends TypeVisitor {
        final /* synthetic */ AtomicReference val$result;

        C07942(AtomicReference atomicReference) {
            this.val$result = atomicReference;
        }

        void visitTypeVariable(TypeVariable<?> t) {
            this.val$result.set(Types.subtypeOfComponentType(t.getBounds()));
        }

        void visitWildcardType(WildcardType t) {
            this.val$result.set(Types.subtypeOfComponentType(t.getUpperBounds()));
        }

        void visitGenericArrayType(GenericArrayType t) {
            this.val$result.set(t.getGenericComponentType());
        }

        void visitClass(Class<?> t) {
            this.val$result.set(t.getComponentType());
        }
    }

    static {
        TYPE_TO_STRING = new C07931();
        COMMA_JOINER = Joiner.on(", ").useForNull("null");
    }

    static Type newArrayType(Type componentType) {
        boolean z = true;
        if (!(componentType instanceof WildcardType)) {
            return JavaVersion.CURRENT.newArrayType(componentType);
        }
        boolean z2;
        WildcardType wildcard = (WildcardType) componentType;
        Type[] lowerBounds = wildcard.getLowerBounds();
        if (lowerBounds.length <= 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "Wildcard cannot have more than one lower bounds.");
        if (lowerBounds.length == 1) {
            return supertypeOf(newArrayType(lowerBounds[0]));
        }
        Type[] upperBounds = wildcard.getUpperBounds();
        if (upperBounds.length != 1) {
            z = false;
        }
        Preconditions.checkArgument(z, "Wildcard should have only one upper bound.");
        return subtypeOf(newArrayType(upperBounds[0]));
    }

    static ParameterizedType newParameterizedTypeWithOwner(@Nullable Type ownerType, Class<?> rawType, Type... arguments) {
        if (ownerType == null) {
            return newParameterizedType(rawType, arguments);
        }
        boolean z;
        Preconditions.checkNotNull(arguments);
        if (rawType.getEnclosingClass() != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Owner type for unenclosed %s", rawType);
        return new ParameterizedTypeImpl(ownerType, rawType, arguments);
    }

    static ParameterizedType newParameterizedType(Class<?> rawType, Type... arguments) {
        return new ParameterizedTypeImpl(ClassOwnership.JVM_BEHAVIOR.getOwnerType(rawType), rawType, arguments);
    }

    static <D extends GenericDeclaration> TypeVariable<D> newArtificialTypeVariable(D declaration, String name, Type... bounds) {
        if (bounds.length == 0) {
            bounds = new Type[]{Object.class};
        }
        return new TypeVariableImpl(declaration, name, bounds);
    }

    @VisibleForTesting
    static WildcardType subtypeOf(Type upperBound) {
        return new WildcardTypeImpl(new Type[0], new Type[]{upperBound});
    }

    @VisibleForTesting
    static WildcardType supertypeOf(Type lowerBound) {
        return new WildcardTypeImpl(new Type[]{lowerBound}, new Type[]{Object.class});
    }

    static String toString(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    @Nullable
    static Type getComponentType(Type type) {
        Preconditions.checkNotNull(type);
        AtomicReference<Type> result = new AtomicReference();
        new C07942(result).visit(type);
        return (Type) result.get();
    }

    @Nullable
    private static Type subtypeOfComponentType(Type[] bounds) {
        for (Type bound : bounds) {
            Type componentType = getComponentType(bound);
            if (componentType != null) {
                if (componentType instanceof Class) {
                    Class<?> componentClass = (Class) componentType;
                    if (componentClass.isPrimitive()) {
                        return componentClass;
                    }
                }
                return subtypeOf(componentType);
            }
        }
        return null;
    }

    private static Type[] toArray(Collection<Type> types) {
        return (Type[]) types.toArray(new Type[types.size()]);
    }

    private static Iterable<Type> filterUpperBounds(Iterable<Type> bounds) {
        return Iterables.filter((Iterable) bounds, Predicates.not(Predicates.equalTo(Object.class)));
    }

    private static void disallowPrimitiveType(Type[] types, String usedAs) {
        for (Type type : types) {
            if (type instanceof Class) {
                boolean z;
                if (((Class) type).isPrimitive()) {
                    z = false;
                } else {
                    z = true;
                }
                Preconditions.checkArgument(z, "Primitive type '%s' used as %s", (Class) type, usedAs);
            }
        }
    }

    static Class<?> getArrayClass(Class<?> componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }

    private Types() {
    }
}
