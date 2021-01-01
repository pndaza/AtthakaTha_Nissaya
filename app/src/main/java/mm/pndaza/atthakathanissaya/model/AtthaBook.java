package mm.pndaza.atthakathanissaya.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AtthaBook implements Parcelable {
    private String id;
    private String name;
    private int firstPage;
    private int lastPage;


    public AtthaBook(String id, String name, int firstPage, int lastPage) {
        this.id = id;
        this.name = name;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
    }

    protected AtthaBook(Parcel in) {
        id = in.readString();
        name = in.readString();
        firstPage = in.readInt();
        lastPage = in.readInt();
    }

    public static final Creator<AtthaBook> CREATOR = new Creator<AtthaBook>() {
        @Override
        public AtthaBook createFromParcel(Parcel in) {
            return new AtthaBook(in);
        }

        @Override
        public AtthaBook[] newArray(int size) {
            return new AtthaBook[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(firstPage);
        dest.writeInt(lastPage);
    }
}
