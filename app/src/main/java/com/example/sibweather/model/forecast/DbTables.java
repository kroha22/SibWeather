package com.example.sibweather.model.forecast;

import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.espresso.core.deps.guava.collect.Lists;

import com.example.sibweather.model.City;
import com.example.sibweather.model.DayPeriod;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.jetbrains.annotations.NotNull;
import org.joda.time.Instant;
import org.joda.time.LocalDate;

import java.util.Collection;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class DbTables {

    @DatabaseTable(tableName = Forecast.TABLE)
    public static class Forecast {
        public static final String TABLE = "forecasts";

        public static class Column {
            public static final String CITY = "city";
            public static final String LOCAL_DATE = "local_date";
            public static final String DETAILS = "details";
        }

        @DatabaseField(generatedId = true, unique = true)
        private int mId;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.CITY)
        private String mCity;

        @DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Column.LOCAL_DATE)
        private long mLocalDate;

        @ForeignCollectionField(columnName = Column.DETAILS, eager = true)
        private Collection<DayDetailForecast> mDetails;

        public Forecast() {/**/}

        public Forecast(@NotNull City city, @NotNull DayForecast forecast) {
            mCity = city.name();
            mLocalDate = new Instant(forecast.getDate()).getMillis();
            mDetails = Lists.newArrayList(Iterables.transform(forecast.getDetail(), detail -> new DayDetailForecast(this, detail)));
        }

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public long getLocalDate() {
            return mLocalDate;
        }

        public void setLocalDate(long mLocalDate) {
            this.mLocalDate = mLocalDate;
        }

        public Collection<DayDetailForecast> getDetails() {
            return mDetails;
        }

        public void setDetails(Collection<DayDetailForecast> mDetails) {
            this.mDetails = mDetails;
        }

        public DayForecast asDayForecast() {
            return new DayForecast(
                    new LocalDate(mLocalDate),
                    Lists.newArrayList(Iterables.transform(mDetails, DayDetailForecast::asDetailForecast))
            );
        }
    }

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
        }

        @DatabaseField(generatedId = true)
        private int mId;

        @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, columnName = Column.FORECAST)
        private Forecast mForecast;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.DAY_PERIOD, unique = true)
        private String mDayPeriod;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.TEMP_MIN)
        private int mTempMin;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.TEMP_MAX)
        private int mTempMax;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.TEMP_AVG)
        private int mTempAvg;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.PRESS_MIN)
        private int mPressMin;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.PRESS_MAX)
        private int mPressMax;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.PRESS_AVG)
        private int mPressAvg;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.WIND_MIN)
        private int mWindMin;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.WIND_MAX)
        private int mWindMax;

        @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Column.WIND_AVG)
        private int mWindAvg;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.WIND_DIR_TITLE)
        private String mWindTitle;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.WIND_DIR_VAL)
        private String mWindVal;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.WIND_DIR_TITLE_LETTER)
        private String mWindTitleLetter;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.WIND_DIR_TITLE_SHORT)
        private String mWindTitleShort;

        @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Column.ICON_PATH)
        private String mIconPath;

        public DayDetailForecast() {/**/}

        public DayDetailForecast(@NotNull Forecast common, @NotNull DetailForecast forecast) {
            this.mForecast = common;

            this.mDayPeriod = forecast.getDayPeriod().name();
            this.mTempMin = forecast.getTemperature().getMin();
            this.mTempMax = forecast.getTemperature().getMax();
            this.mTempAvg = forecast.getTemperature().getAvg();
            this.mPressMin = forecast.getPressure().getMin();
            this.mPressMax = forecast.getPressure().getMax();
            this.mPressAvg = forecast.getPressure().getAvg();
            this.mWindMin = forecast.getWind().getSpeed().getMin();
            this.mWindMax = forecast.getWind().getSpeed().getMax();
            this.mWindAvg = forecast.getWind().getSpeed().getAvg();
            this.mWindTitle = forecast.getWind().getDirection().getTitle();
            this.mWindVal = forecast.getWind().getDirection().getValue();
            this.mWindTitleLetter = forecast.getWind().getDirection().getTitleLetter();
            this.mWindTitleShort = forecast.getWind().getDirection().getTitleShort();
            this.mIconPath = forecast.getIconPath();
        }

        public int getId() {
            return mId;
        }

        public void setId(int mId) {
            this.mId = mId;
        }

        public Forecast getForecast() {
            return mForecast;
        }

        public void setForecast(Forecast mForecast) {
            this.mForecast = mForecast;
        }

        public String getDayPeriod() {
            return mDayPeriod;
        }

        public void setDayPeriod(String mDayPeriod) {
            this.mDayPeriod = mDayPeriod;
        }

        public int getTempMin() {
            return mTempMin;
        }

        public void setTempMin(int mTempMin) {
            this.mTempMin = mTempMin;
        }

        public int getTempMax() {
            return mTempMax;
        }

        public void setTempMax(int mTempMax) {
            this.mTempMax = mTempMax;
        }

        public int getTempAvg() {
            return mTempAvg;
        }

        public void setTempAvg(int mTempAvg) {
            this.mTempAvg = mTempAvg;
        }

        public int getPressMin() {
            return mPressMin;
        }

        public void setPressMin(int mPressMin) {
            this.mPressMin = mPressMin;
        }

        public int getPressMax() {
            return mPressMax;
        }

        public void setPressMax(int mPressMax) {
            this.mPressMax = mPressMax;
        }

        public int getPressAvg() {
            return mPressAvg;
        }

        public void setPressAvg(int mPressAvg) {
            this.mPressAvg = mPressAvg;
        }

        public int getWindMin() {
            return mWindMin;
        }

        public void setWindMin(int mWindMin) {
            this.mWindMin = mWindMin;
        }

        public int getWindMax() {
            return mWindMax;
        }

        public void setWindMax(int mWindMax) {
            this.mWindMax = mWindMax;
        }

        public int getWindAvg() {
            return mWindAvg;
        }

        public void setWindAvg(int mWindAvg) {
            this.mWindAvg = mWindAvg;
        }

        public String getWindTitle() {
            return mWindTitle;
        }

        public void setWindTitle(String mWindTitle) {
            this.mWindTitle = mWindTitle;
        }

        public String getWindVal() {
            return mWindVal;
        }

        public void setWindVal(String mWindVal) {
            this.mWindVal = mWindVal;
        }

        public String getWindTitleLetter() {
            return mWindTitleLetter;
        }

        public void setWindTitleLetter(String mWindTitleLetter) {
            this.mWindTitleLetter = mWindTitleLetter;
        }

        public String getWindTitleShort() {
            return mWindTitleShort;
        }

        public void setWindTitleShort(String mWindTitleShort) {
            this.mWindTitleShort = mWindTitleShort;
        }

        public String getIconPath() {
            return mIconPath;
        }

        public void setIconPath(String mIconPath) {
            this.mIconPath = mIconPath;
        }

        public DetailForecast asDetailForecast() {
            return new DetailForecast(
                    DayPeriod.valueOf(mDayPeriod),
                    new Property(mTempMin, mTempMax, mTempAvg),
                    new Property(mPressMin, mPressMax, mPressAvg),
                    new Wind(new Property(mWindMin, mWindMax, mWindAvg), new Direction(mWindTitle, mWindVal, mWindTitleLetter, mWindTitleShort)),
                    mIconPath
            );
        }
    }

}
