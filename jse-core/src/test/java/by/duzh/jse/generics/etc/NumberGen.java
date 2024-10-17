package by.duzh.jse.generics.etc;

public class NumberGen<T extends Number> extends Gen<T> {
     public NumberGen(T value) {
         super(value);
     }
}
