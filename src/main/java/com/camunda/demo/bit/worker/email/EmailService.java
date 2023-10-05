// Source code is decompiled from a .class file using FernFlower decompiler.
package com.camunda.demo.bit.worker.email;

public interface EmailService {
   String sendSimpleMail(EmailDetails details);

   String sendMailWithAttachment(EmailDetails details);
}
