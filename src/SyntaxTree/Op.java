package SyntaxTree;

public enum Op {
    ;
    private final String name;

    Op(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
