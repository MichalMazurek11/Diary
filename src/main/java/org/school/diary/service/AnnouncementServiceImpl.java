package org.school.diary.service;


import lombok.AllArgsConstructor;
import org.school.diary.dao.AnnouncementRepository;
import org.school.diary.model.Announcement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnnouncementServiceImpl implements  AnnouncementService{

    private final AnnouncementRepository announcementRepository;

    @Override
    public void saveAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    @Override
    public List<Announcement> listAnnouncements() {
        return announcementRepository.findAll();
    }

    @Override
    public void deleteAnnouncement(int announcementId) {
        announcementRepository.deleteById(announcementId);
    }
}
