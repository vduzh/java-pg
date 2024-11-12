package by.duzh.springframework.beans.factory.beans;

public class InjectionsSample {

    private final Foo constructorValue;

    private final int constructorIntValue;

    private Foo propertyValue;

    private String propertyStringValue;

    private String spelString;

    public InjectionsSample(Foo constructorValue, int constructorIntValue) {
        this.constructorValue = constructorValue;
        this.constructorIntValue = constructorIntValue;
    }

    public Foo getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(Foo propertyValue) {
        this.propertyValue = propertyValue;
    }

    public Foo getConstructorValue() {
        return constructorValue;
    }

    public int getConstructorIntValue() {
        return constructorIntValue;
    }

    public String getPropertyStringValue() {
        return propertyStringValue;
    }

    public void setPropertyStringValue(String propertyStringValue) {
        this.propertyStringValue = propertyStringValue;
    }

    public String getSpelString() {
        return spelString;
    }

    public void setSpelString(String spelString) {
        this.spelString = spelString;
    }

    // lookup method
    public Bar getBar() {
        return null;
    }

    // replaced method
    public String getReplacedValue() {
        return "original";
    }
}
