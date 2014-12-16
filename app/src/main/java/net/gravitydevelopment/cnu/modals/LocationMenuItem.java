package net.gravitydevelopment.cnu.modals;

public class LocationMenuItem {

    private String mStartTime;
    private String mEndTime;
    private String mSummary;
    private String mDescription;

    public LocationMenuItem(String startTime, String endTime, String summary, String description) {
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mSummary = summary;
        this.mDescription = description;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public String getSummary() {
        return mSummary;
    }

    public String getDescription() {
        return mDescription;
    }
}