
public class CustomerModel {
    public int mCustomerID;
    public String mName, mAddress;
    public int mPhone;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(mCustomerID).append(",");
        sb.append("\"").append(mName).append("\"").append(",");
        sb.append(mPhone).append(",");
        sb.append("\"").append(mAddress).append("\"").append(")");;
        return sb.toString();
    }
}
