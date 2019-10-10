public class PurchaseModel {
    public int mPurchaseID, mCustomerID, mProductID, mQuantity;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(mPurchaseID).append(",");
        sb.append(mCustomerID).append(",");
        sb.append(mProductID).append(",");
        sb.append(mQuantity).append(")");
        return sb.toString();
    }
}