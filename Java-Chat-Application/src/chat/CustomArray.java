package chat;

public class CustomArray {
    private int size;
    private byte[] data;

    public CustomArray(int capacity) {
        size = 0;
        data = new byte[capacity];
    }

    public void add(byte[] items) {
        for (byte item : items) {
            if (size == data.length) {
                // Resize the array if it's full
                byte[] newData = new byte[data.length * 2];
                System.arraycopy(data, 0, newData, 0, data.length);
                data = newData;
            }
            data[size] = item;
            size++;
        }
    }

    public byte get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return data[index];
    }

    public int size() {
        return size;
    }
}
