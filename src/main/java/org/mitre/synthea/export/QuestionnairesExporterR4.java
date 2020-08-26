package org.mitre.synthea.export;

import ca.uhn.fhir.context.FhirContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.mitre.synthea.helpers.Config;
import org.mitre.synthea.world.agents.Provider;

public abstract class QuestionnairesExporterR4 {

  private static final FhirContext FHIR_CTX = FhirContext.forR4();

  private static final String SYNTHEA_URI = "http://synthetichealth.github.io/synthea/";

  /**
   * Export the quistionnaires in FHIR R4 format.
   */
  public static void export(long stop) {
    if (Boolean.parseBoolean(Config.get("exporter.hospital.fhir.export"))) {

      Bundle bundle = new Bundle();
      if (Boolean.parseBoolean(Config.get("exporter.fhir.transaction_bundle"))) {
        bundle.setType(BundleType.TRANSACTION);
      } else {
        bundle.setType(BundleType.COLLECTION);
      }
      BundleEntryComponent entry = FhirR4.questionnaire(bundle);
      String bundleJson = FHIR_CTX.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle);

      // get output folder
      List<String> folders = new ArrayList<>();
      folders.add("fhir");
      String baseDirectory = Config.get("exporter.baseDirectory");
      File f = Paths.get(baseDirectory, folders.toArray(new String[0])).toFile();
      f.mkdirs();
      Path outFilePath = f.toPath().resolve("questionnaires" + stop + ".json");

      try {
        Files.write(outFilePath, Collections.singleton(bundleJson), StandardOpenOption.CREATE_NEW);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}