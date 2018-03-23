package com.example.sibweather.model.forecast;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class DbTables {
    //------------------------------------------------------------------------------------------------
    @DatabaseTable(tableName = Forecast.TABLE)
    public static class Forecast {
        public static final String TABLE = "forecasts";

        public static class Column {
            public static final String CITY = "city";
            public static final String LOCAL_DATE = "local_date";
            public static final String DETAILS = "details";
        }

        @DatabaseField(generatedId = true, unique = true)
        private int id;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.CITY)
        private String city;

        @DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Column.LOCAL_DATE)
        private long localDate;

        @ForeignCollectionField(columnName = Column.DETAILS, eager = true)
        private Collection<DayDetailForecast> details;

        public Forecast() {/**/}

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public long getLocalDate() {
            return localDate;
        }

        public void setLocalDate(long mLocalDate) {
            this.localDate = mLocalDate;
        }

        public Collection<DayDetailForecast> getDetails() {
            return details;
        }

        public void setDetails(Collection<DayDetailForecast> mDetails) {
            this.details = mDetails;
        }
    }

    //------------------------------------------------------------------------------------------------
    @DatabaseTable(tableName = DayDetailForecast.TABLE)
    public static class DayDetailForecast {
        public static final String TABLE = "day_detail_forecasts";

        public static class Column {
            public static final String FORECAST = "forecast";

            public static final String DAY_PERIOD = "day_period";
            public static final String TEMP_MIN = "temp_min";
            public static final String TEMP_MAX = "temp_max";
            public static final String TEMP_AVG = "temp_avg";
            public static final String PRESS_MIN = "press_min";
            public static final String PRESS_MAX = "press_max";
            public static final String PRESS_AVG = "press_avg";
            public static final String WIND_MIN = "wind_min";
            public static final String WIND_MAX = "wind_max";
            public static final String WIND_AVG = "wind_avg";
            public static final String WIND_DIR_TITLE = "wind_dir_title";
            public static final String WIND_DIR_VAL = "wind_dir_val";
            public static final String WIND_DIR_TITLE_LETTER = "wind_dir_title_letter";
            public static final String WIND_DIR_TITLE_SHORT = "wind_dir_title_short";
            public static final String ICON_PATH = "icon_path";
            public static final String BIG_ICON_PATH = "big_icon_path";
        }

        @DatabaseField(generatedId = true)
        private int mId;

        @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, columnName = Column.FORECAST)
        private Forecast forecast;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.DAY_PERIOD)
        private String dayPeriod;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.TEMP_MIN)
        private int tempMin;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.TEMP_MAX)
        private int tempMax;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.TEMP_AVG)
        private int tempAvg;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.PRESS_MIN)
        private int pressMin;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.PRESS_MAX)
        private int pressMax;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.PRESS_AVG)
        private int pressAvg;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.WIND_MIN)
        private int windMin;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.WIND_MAX)
        private int windMax;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.WIND_AVG)
        private int windAvg;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.WIND_DIR_TITLE)
        private String windTitle;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.WIND_DIR_VAL)
        private String windVal;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.WIND_DIR_TITLE_LETTER)
        private String windTitleLetter;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.WIND_DIR_TITLE_SHORT)
        private String windTitleShort;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.ICON_PATH)
        private String iconPath;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.BIG_ICON_PATH)
        private String bigIconPath;

        public DayDetailForecast() {/**/}

        public int getId() {
            return mId;
        }

        public void setId(int mId) {
            this.mId = mId;
        }

        public Forecast getForecast() {
            return forecast;
        }

        public void setForecast(Forecast mForecast) {
            this.forecast = mForecast;
        }

        public String getDayPeriod() {
            return dayPeriod;
        }

        public void setDayPeriod(String mDayPeriod) {
            this.dayPeriod = mDayPeriod;
        }

        public int getTempMin() {
            return tempMin;
        }

        public void setTempMin(int mTempMin) {
            this.tempMin = mTempMin;
        }

        public int getTempMax() {
            return tempMax;
        }

        public void setTempMax(int mTempMax) {
            this.tempMax = mTempMax;
        }

        public int getTempAvg() {
            return tempAvg;
        }

        public void setTempAvg(int mTempAvg) {
            this.tempAvg = mTempAvg;
        }

        public int getPressMin() {
            return pressMin;
        }

        public void setPressMin(int mPressMin) {
            this.pressMin = mPressMin;
        }

        public int getPressMax() {
            return pressMax;
        }

        public void setPressMax(int mPressMax) {
            this.pressMax = mPressMax;
        }

        public int getPressAvg() {
            return pressAvg;
        }

        public void setPressAvg(int mPressAvg) {
            this.pressAvg = mPressAvg;
        }

        public int getWindMin() {
            return windMin;
        }

        public void setWindMin(int mWindMin) {
            this.windMin = mWindMin;
        }

        public int getWindMax() {
            return windMax;
        }

        public void setWindMax(int mWindMax) {
            this.windMax = mWindMax;
        }

        public int getWindAvg() {
            return windAvg;
        }

        public void setWindAvg(int mWindAvg) {
            this.windAvg = mWindAvg;
        }

        public String getWindTitle() {
            return windTitle;
        }

        public void setWindTitle(String mWindTitle) {
            this.windTitle = mWindTitle;
        }

        public String getWindVal() {
            return windVal;
        }

        public void setWindVal(String mWindVal) {
            this.windVal = mWindVal;
        }

        public String getWindTitleLetter() {
            return windTitleLetter;
        }

        public void setWindTitleLetter(String mWindTitleLetter) {
            this.windTitleLetter = mWindTitleLetter;
        }

        public String getWindTitleShort() {
            return windTitleShort;
        }

        public void setWindTitleShort(String mWindTitleShort) {
            this.windTitleShort = mWindTitleShort;
        }

        public String getIconPath() {
            return iconPath;
        }

        public void setIconPath(String mIconPath) {
            this.iconPath = mIconPath;
        }

        public String getBigIconPath() {
            return bigIconPath;
        }

        public void setBigIconPath(String bigIconPath) {
            this.bigIconPath = bigIconPath;
        }
    }
    //------------------------------------------------------------------------------------------------

}
