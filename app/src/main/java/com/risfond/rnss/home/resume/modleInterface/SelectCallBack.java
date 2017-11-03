package com.risfond.rnss.home.resume.modleInterface;

import java.util.List;

/**
 * Created by Abbott on 2017/8/14.
 */

public interface SelectCallBack {
    void onPositionConfirm(List<String> positions, List<String> names);

    void onExperienceConfirm(String from, String to);

    void onEducationConfirm(List<String> educations, List<String> educationName);

    void onMoreConfirm(List<String> recommends, String age_from, String age_to, List<String> sexs,
                       String salary_from, String salary_to, List<String> languages, String page);

    void onOutside();
}
