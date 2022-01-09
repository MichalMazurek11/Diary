package org.school.diary.service;

import org.school.diary.model.Announcement;

import java.util.List;

public interface AnnouncementService {

    void saveAnnouncement(Announcement announcement);

    List<Announcement> listAnnouncements();

    void deleteAnnouncement(int announcementId);

}

