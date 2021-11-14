package org.school.diary.service;

import org.school.diary.model.Parent;
import org.school.diary.model.common.PersonRelatedWithSchool;

public interface ParentService {

    public void save(PersonRelatedWithSchool parent);

    public void deleteParent(long id);
}
