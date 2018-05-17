package lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Comparable<User> {

    private Integer id;

    private String name;

    private int age;

    private double salary;

    public int compareTo(User o) {
        return 0;
    }
}
