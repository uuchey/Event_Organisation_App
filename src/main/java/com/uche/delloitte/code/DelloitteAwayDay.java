package com.uche.delloitte.code;

import com.uche.delloitte.code.exception.ActivityHandlerException;
import com.uche.delloitte.code.exception.ActivityParseException;
import com.uche.delloitte.code.model.Activity;
import com.uche.delloitte.code.model.Team;
import com.uche.delloitte.code.scheduler.ActivityScheduler;
import com.uche.delloitte.code.util.ActivityConsolePrinter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.uche.delloitte.code.util.ActivityConsolePrinter.print;

/**
 * Created by uuchey on 23/05/2016.
 */
public class DelloitteAwayDay {

    private static final String DEFAULT_FILE_LOCATION = "src/main/resource/upload.txt";
    private static SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
    private static String START_TIME = "9:00 am";
    private static final int NO_OF_TEAMS = 2;

    public static void main(String[] args) throws ActivityHandlerException {
        String fileLocation = DEFAULT_FILE_LOCATION;
        if (!new File(fileLocation).exists()) {
            System.out.printf("Unable to start up delloitte away day,  File location '%s' is invalid.", fileLocation);
            return;
        }
        List<Team> teams = new ActivityScheduler().schedule(NO_OF_TEAMS, DEFAULT_FILE_LOCATION);
        teams.forEach(t -> printSchedule(t));
    }


    public static void printSchedule(Team team) {

        try {
            Date d = df.parse(START_TIME);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);


            System.out.println("Activities for " + team.getName());
            //Print out morning activities
            team.getMorningActivities().forEach(a -> {
                String stTime = df.format(cal.getTime());
                cal.add(Calendar.MINUTE, (int) a.getDuration().toMinutes());
                print(stTime, a);
            });

            //lunch time
            Activity lunch = new Activity("Lunch", Duration.ofMinutes(60));
            String lunchStTime = df.format(cal.getTime());
            cal.add(Calendar.MINUTE, (int) lunch.getDuration().toMinutes());
            print(lunchStTime, lunch);

            //print out afternoon activities
            team.getAfternoonActivities().forEach(a -> {
                String afEventTime = df.format(cal.getTime());
                cal.add(Calendar.MINUTE, (int) a.getDuration().toMinutes());
                print(afEventTime, a);
            });

            //presentation time
            Activity presentation = new Activity("Staff Motivation Presentation", Duration.ofMinutes(60));
            String prTime = df.format(cal.getTime());
            cal.add(Calendar.MINUTE, (int) lunch.getDuration().toMinutes());
            print(prTime, presentation);
            System.out.println("\n\n");
        } catch (ParseException e) {
            throw new ActivityParseException("Error parsing activity time ", e);
        }
    }
}
