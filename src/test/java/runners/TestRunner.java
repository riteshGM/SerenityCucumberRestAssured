package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;


import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features= {"src/test/resources/features"},
glue = {"com.serenity.tests.features.steps"},
tags = "@Dev"
)

public class TestRunner {
	
}