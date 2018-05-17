package lambda.whyLambda;

import lambda.User;

public class FilterUserBySalary implements MyPredicate<User, Double> {

    @Override
    public boolean test(User user, Double aDouble) {
        return user.getSalary() > aDouble;
    }

}
