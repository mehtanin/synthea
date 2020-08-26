package org.mitre.synthea.world.concepts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Questionnaire {
    public String id;
    public String category;
    public String subCategory;
    public String item;
    public List<String> scales;
    public String value;

    /**
     * Constructor for Questionnaires HealthRecord Entry.
     */
    public Questionnaire() {
        this.scales = new ArrayList<String>();
    }

    public void addScales(String value) {
        this.scales.add(value);
    }
}