package runner;

import org.junit.runner.Runner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.BeforeSuite;

public class PersonalizedRunner extends Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalizedRunner.class);
    private Class<CucumberWithSerenity> classValue;
    private CucumberWithSerenity cucumber;

    public PersonalizedRunner(Class<CucumberWithSerenity> classValue) throws IOException,
        InitializationError {
        this.classValue = classValue;
        cucumber = new CucumberWithSerenity(classValue);
    }

    @Override
    public Description getDescription() {
        return cucumber.getDescription();
    }

    private void runAnnotatedMethods(Class<?> annotation) throws InvocationTargetException,
        IllegalAccessException {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methods = this.classValue.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    method.invoke(null);
                    break;
                }
            }
        }
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            runAnnotatedMethods(BeforeSuite.class);
            cucumber = new CucumberWithSerenity(classValue);
        } catch (Exception e) {
            LOGGER.error("Failed trying to initialize the runner " + e);
        }
        cucumber.run(notifier);
    }
}
