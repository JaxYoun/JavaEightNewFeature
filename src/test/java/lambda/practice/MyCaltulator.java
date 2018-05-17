package lambda.practice;

@FunctionalInterface
public interface MyCaltulator<T, R> {

    R calculate(T arg0, T arg1);

}
