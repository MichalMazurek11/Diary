package org.school.diary.service;


public interface MailService {

    void sendEmail(String to, String subject, String content);

}
