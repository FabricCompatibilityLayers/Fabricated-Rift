package org.dimdev.accesstransform;

import fr.catcore.fabricatedrift.RemapUtils;

import java.util.Objects;

public class ElementReference {
    public enum Kind {
        CLASS,
        METHOD,
        FIELD
    }

    public final Kind kind;
    public final String owner;
    public final String name;
    public final String desc;

    public ElementReference(Kind kind, String owner, String name, String desc) {
        this.kind = kind;
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }

    public static ElementReference fromString(String string) {
        String[] split = string.split(" ");

        switch (split[0]) {
            case "class": {
                return new ElementReference(Kind.CLASS, null, RemapUtils.remapClass(split[1]).replace("/", "."), null);
            }

            case "method": {
                split = RemapUtils.remapMethod(split[1], split[2], split[3]);
                return new ElementReference(Kind.METHOD, split[0].replace("/", "."), split[1], split[2]);
            }

            case "field": {
                split = RemapUtils.remapField(split[1], split[2], split[3]);
                return new ElementReference(Kind.FIELD, split[0].replace("/", "."), split[1], split[2]);
            }

            default: {
                throw new RuntimeException("Unknown element type '" + split[0] + "'");
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
               obj.getClass() == ElementReference.class &&
               ((ElementReference) obj).kind == kind &&
               Objects.equals(((ElementReference) obj).owner, owner) &&
               Objects.equals(((ElementReference) obj).name, name) &&
               Objects.equals(((ElementReference) obj).desc, desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, owner, name, desc);
    }
}
