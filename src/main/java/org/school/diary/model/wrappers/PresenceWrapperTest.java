package org.school.diary.model.wrappers;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.school.diary.model.Presence;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresenceWrapperTest {

    private ArrayList<Presence> presenceList;



    @Override
    public String toString() {
        return "PresenceWrapperTest{" +
                "presenceList=" + presenceList +
                '}';
    }
}
