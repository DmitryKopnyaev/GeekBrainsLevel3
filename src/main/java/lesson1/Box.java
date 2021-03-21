package lesson1;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {
    private List<Fruit> list;

    public Box() {
        this.list = new ArrayList<>();
    }

    private List<Fruit> getList() {
        return list;
    }

    public boolean add(T fruit){
        return list.add(fruit);
    }

    private void addAll(Box<T> boxDonor){
        this.list.addAll(boxDonor.getList());
    }

    public void getAll(Box<T> boxAkceptor){
        boxAkceptor.addAll(this);
        this.list.clear();
    }

    public double getWeight(){
        if(this.list.size() < 1) return 0;
        return this.list.size()*this.list.get(0).getWeight();
    }

    public boolean compare(Box<?> box2){
        return Double.compare(this.getWeight(), box2.getWeight())==0;
    }
}
