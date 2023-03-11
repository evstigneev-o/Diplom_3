package Utils;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.RegisterRq;

public class UserGenerator {
    public static RegisterRq generateUser() {
        return new RegisterRq(
                RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru",
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10)
        );
    }
}
