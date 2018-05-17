package lambda.whyLambda;

import lambda.User;

public class FilterUserByAge implements MyPredicate<User, Integer> {

    @Override
    public boolean test(User user, Integer age) {
        return user.getAge() > age;
    }

}
