package Client.DBClass;

public class Delivery {
    private String idDelivery;
    private String idTransaction;
    private String DeliveryAdress;
    private String DeliveryStatus;

    public Delivery(String idDelivery, String idTransaction, String deliveryAdress, String deliveryStatus) {
        this.idDelivery = idDelivery;
        this.idTransaction = idTransaction;
        DeliveryAdress = deliveryAdress;
        DeliveryStatus = deliveryStatus;
    }

    public Delivery(){}

    public String getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(String idDelivery) {
        this.idDelivery = idDelivery;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getDeliveryAdress() {
        return DeliveryAdress;
    }

    public void setDeliveryAdress(String deliveryAdress) {
        DeliveryAdress = deliveryAdress;
    }

    public String getDeliveryStatus() {
        return DeliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        DeliveryStatus = deliveryStatus;
    }
}
