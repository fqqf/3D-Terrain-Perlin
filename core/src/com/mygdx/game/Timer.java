

import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class Timer {

    private long startTime;
    private static long currentTime;
    private long time;

    private boolean isChecked;

    private static ArrayList <Timer>timerList;

    private static Timer currentTimer;

    static {
        timerList = new ArrayList();
    }

    {
        timerList.add(this);
    }

    private Timer() {}

    public static void updTimer() {



    }

    public static long getCurrentTime() { return currentTime; }

    public boolean isDone() {

        if ((currentTime - startTime) > time) {

            isChecked = true;
            return true;


        } else return false;

    }


    public static Timer createTimer(float sec) {

        for (int i = 0; i < timerList.size(); i++) {

            if (timerList.get(i).isChecked) {

                timerList.get(i).startTime = TimeUtils.nanoTime();
                timerList.get(i).time = (long)(sec * 1000000000L);

                return timerList.get(i);

            }

        }

        currentTimer = new Timer();

        currentTimer.startTime = TimeUtils.nanoTime();
        currentTimer.time = (long)(sec * 1000000000L);

        return currentTimer;

    }

    public static ArrayList<Timer> getTimerList() {
        return timerList;
    }

}
