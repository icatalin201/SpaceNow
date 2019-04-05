package space.pal.sig.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Glossary implements Parcelable {

    private String name;
    private String definition;

    public Glossary() { }

    public Glossary(String name, String definition) {
        this.name = name;
        this.definition = definition;
    }

    private Glossary(Parcel in) {
        name = in.readString();
        definition = in.readString();
    }

    public static final Creator<Glossary> CREATOR = new Creator<Glossary>() {
        @Override
        public Glossary createFromParcel(Parcel in) {
            return new Glossary(in);
        }

        @Override
        public Glossary[] newArray(int size) {
            return new Glossary[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(definition);
    }
}
