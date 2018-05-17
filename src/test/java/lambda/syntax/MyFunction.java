package lambda.syntax;

@FunctionalInterface
public interface MyFunction<T> {

    T calculate(T arg0, T arg1);

}
