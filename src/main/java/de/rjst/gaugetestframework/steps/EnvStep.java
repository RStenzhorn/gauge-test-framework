package de.rjst.gaugetestframework.steps;

import com.thoughtworks.gauge.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EnvStep {


    @Step("abcasasa")
    public void testEnv() {
        String testhallo = System.getenv("testhallo");

        if (testhallo != null) {
            System.out.println("MY_ENV_VARIABLE: " + testhallo);
        } else {
            System.out.println("MY_ENV_VARIABLE is not set.");
        }

    }

}
