// Source code is decompiled from a .class file using FernFlower decompiler.
package com.camunda.demo.bit.worker;

import com.camunda.demo.bit.storage.StorageProperties;
import com.camunda.demo.bit.storage.StorageService;
import com.camunda.demo.bit.worker.email.EmailDetails;
import com.camunda.demo.bit.worker.email.EmailService;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import io.grpc.Context.Storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendBestellBestaetigungWorker {
   @Autowired
   private EmailService emailService;

   private final Path rootLocation;

   @Autowired
   public SendBestellBestaetigungWorker(StorageProperties properties) {
      this.rootLocation = Paths.get(properties.getLocation());
   }

   @JobWorker(type = "sendeBestellbestaetigung", fetchAllVariables = true)
   public void sendBestellBestaetigung(@VariablesAsType PizzaBestellData data) throws Exception {
      EmailDetails details = new EmailDetails();
      details.setRecipient(data.getEmail());
      String vorname = data.getVorname();
      details.setAttachment(this.rootLocation.resolve(vorname + "_" + data.getNachname() + ".pdf").toString());
      details.setSubject("Bestellbest\u00e4tigung");
      details.setMsgBody("Sehr geehrte(r) " + data.getAnrede() + " " + data.getNachname() + ",\n\n"
            + "Vielen Dank für Ihre Bestellung. Ihre Bestellbestätigung finden Sie im Anhang.\n\n"
            + "Mit freundlichen Grüßen\n\n"
            + "Ihr PizzaExpress A1 Team");
      this.emailService.sendMailWithAttachment(details);
      System.out.println("Bestellbest\u00e4tigung an " + data.getEmail() + " gesendet");
   }
}
