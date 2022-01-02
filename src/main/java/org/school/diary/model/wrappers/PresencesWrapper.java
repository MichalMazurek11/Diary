package org.school.diary.model.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.school.diary.model.Presence;

import java.util.List;

//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class PresencesWrapper<T> {
    private List<T> values;

    public PresencesWrapper(List<T> values) {
        this.values = values;
    }

    public PresencesWrapper() {
    }
}
