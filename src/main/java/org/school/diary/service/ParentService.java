package org.school.diary.service;

import org.school.diary.model.Director;
import org.school.diary.model.Parent;

public interface ParentService {

    public void save(Parent parent);

    public void deleteParent(long id);
}
