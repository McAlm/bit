// Source code is decompiled from a .class file using FernFlower decompiler.
package com.camunda.demo.bit.worker;

import com.camunda.demo.bit.storage.StorageProperties;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

@Component
public class PdfWorker {
   private final Path rootLocation;

   @Autowired
   public PdfWorker(StorageProperties properties) {
      this.rootLocation = Paths.get(properties.getLocation());
   }

   @JobWorker(type = "erzeugeBestellPDF")
   public void createPdf(@VariablesAsType PizzaBestellData data) throws Exception {
      Document document = new Document();
      Path var10003 = this.rootLocation;
      String var10004 = data.getVorname();
      PdfWriter.getInstance(document,
            new FileOutputStream(var10003.resolve(var10004 + "_" + data.getNachname() + ".pdf").toString()));
      document.open();

      // Path path = Paths.get(ResourceUtils.getFile("classpath:static/BITBW_Logo_RGB.jpg").getAbsolutePath());
      // Image img = Image.getInstance(path.toAbsolutePath().toString());
      InputStream resource = new ClassPathResource("classpath:static/BITBW_Logo_RGB.jpg").getInputStream();
      byte[] copyToByteArray = FileCopyUtils.copyToByteArray(resource);
      Image img = Image.getInstance(copyToByteArray);
      img.scaleAbsolute(50, 17);
      document.add(img);

      Paragraph paragraph = new Paragraph();
      paragraph.setAlignment(Element.ALIGN_CENTER);
      Font font = FontFactory.getFont("Helvetica", 16.0F, BaseColor.BLACK);
      Chunk chunk = new Chunk("BESTELLBEST\u00c4TIGUNG ", font);
      paragraph.add(chunk);
      paragraph.add("\n");
      paragraph.add("\n");
      document.add(paragraph);


      paragraph = new Paragraph();
      paragraph.add("\n");
      paragraph.add("\n");
      paragraph.add("\n");
      font = FontFactory.getFont("Helvetica", 11.0F, BaseColor.BLACK);
      chunk = new Chunk("Vielen herzlichen Dank f\u00fcr Ihre Bestellung bei Pizzaexpress 1A! ", font);
      paragraph.add(chunk);
      paragraph.add("\n");
      paragraph.add("\n");
      paragraph.add("\n");
      chunk = new Chunk("Sehr geehrte/r " + data.getAnrede() + " " + data.getNachname() + ",", font);
      paragraph.add(chunk);
      paragraph.add("\n");
      paragraph.add("\n");
      chunk = new Chunk("Sie haben folgende Speisen bei uns bestellt:", font);
      paragraph.add(chunk);
      paragraph.add("\n");
      chunk = new Chunk("Speise:          Menge:", font);
      paragraph.add(chunk);
      paragraph.add("\n");
      chunk = new Chunk("__________________________________", font);
      paragraph.add(chunk);
      paragraph.add("\n");
      chunk = new Chunk(data.getAuswahl_Speise1() + "        " + data.getMenge_Speise1(), font);
      paragraph.add(chunk);
      if (data.getAuswahl_Speise2() != null) {
         paragraph.add("\n");
         chunk = new Chunk(data.getAuswahl_Speise2() + "        " + data.getMenge_Speise2(), font);
         paragraph.add(chunk);
      }

      paragraph.add("\n");
      paragraph.add("\n");
      paragraph.add("\n");
      chunk = new Chunk("Die Lieferung erfolgt an folgende Adresse:\n", font);
      paragraph.add(chunk);
      chunk = new Chunk(data.getAdresse(), font);
      paragraph.add(chunk);
      paragraph.add("\n");
      paragraph.add("\n");
      chunk = new Chunk("Sie haben folgendes Lieferdatum gew\u00e4hlt:\n", font);
      paragraph.add(chunk);
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
      chunk = new Chunk(sdf.format(data.getWunschzeit()) + " Uhr", font);
      paragraph.add(chunk);
      paragraph.add("\n");
      chunk = new Chunk("Wenn Sie kein Lieferdatum gew\u00e4hlt haben, erfolgt die Lieferung sofort.\n", font);
      paragraph.add(chunk);
      paragraph.add("\n");
      chunk = new Chunk("Freundliche Gr\u00fc\u00dfe\nIhr PizzaExpress 1A Team", font);
      paragraph.add(chunk);
      document.add(paragraph);
      document.close();
   }
}
