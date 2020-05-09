import android.os.Parcel;
import android.os.Parcelable;

/**
 * project frame
 * Created by dtb on 2020/4/13
 * email: 1750352866@qq.com
 *
 * @author: dtb
 * *
 * @version: 1.0
 * *
 * @description:
 */
public class Student implements Parcelable {

    public String name;
    public String sex;

    public Student() {

    }

    public Student(Parcel in) {
        name = in.readString();
        sex = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(sex);
    }
}
