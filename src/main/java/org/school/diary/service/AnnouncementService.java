package org.school.diary.service;

import org.school.diary.model.Announcement;
import org.school.diary.model.ClassGroup;

import java.util.List;

public interface AnnouncementService {

    void saveAnnouncement(Announcement announcement);

    public List<Announcement> listAnnouncements();

    public void deleteAnnouncement(int announcementId);

}

