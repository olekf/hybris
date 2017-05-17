package com.cucumberintegration.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.springframework.stereotype.Component;

@Component
public class SearchSteps {

    @Given("^I want to buy a wool scarf$")
    public void i_want_to_buy_a_wool_scarf() throws Throwable {
    }

    @When("^I search for items containing 'wool'$")
    public void i_search_for_items_containing_wool() throws Throwable {
    }

    @Then("^I should only see items related to 'wool'$")
    public void i_should_only_see_items_related_to_wool() throws Throwable {
    }
}
