public interface Product{

    @Override
    public String toString();

    public int getPrice();

    public String getSize();
    public void increaseQuantity(int quantity);
    public void decreaseQuantity(int quantity);
}
