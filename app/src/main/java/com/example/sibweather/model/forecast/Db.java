package com.example.sibweather.model.forecast;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.espresso.core.deps.guava.collect.Lists;

import com.example.sibweather.model.City;
import com.example.sibweather.model.DayPeriod;
import com.example.sibweather.utils.TimeUtils;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class Db {
    //--------------------------------------------------------------------------------------------
    private static final int DB_VERSION = 1;
    //--------------------------------------------------------------------------------------------
    public static class DbHelper extends OrmLiteSqliteOpenHelper {
        private static final String DB_NAME = "sib_weather";

        private static DbHelper helper;

        private Dao<DbTables.Forecast, Integer> forecastsDao;
        private Dao<DbTables.DayDetailForecast, Integer> detailForecastsDao;

        @NotNull
        public static DbHelper getInstance(@NotNull Context context) {
            if (helper == null) {
                synchronized (DbHelper.class) {
                    if (helper == null) {
                        helper = OpenHelperManager.getHelper(context, DbHelper.class);
                    }
                }
            }

            return helper;
        }

        public DbHelper(@NotNull Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);

            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onCreate(@NotNull SQLiteDatabase db, @NotNull ConnectionSource connectionSource) {
            try {
                TableUtils.createTable(connectionSource, DbTables.Forecast.class);
                TableUtils.createTable(connectionSource, DbTables.DayDetailForecast.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(@NotNull SQLiteDatabase db, @NotNull ConnectionSource connectionSource, int oldVersion, int newVersion) {/**/}

        @NotNull
        Dao<DbTables.Forecast, Integer> getForecastsDao() throws SQLException, java.sql.SQLException {
            if (forecastsDao == null) {
                forecastsDao = getDao(DbTables.Forecast.class);
            }
            return forecastsDao;
        }

        @NotNull
        Dao<DbTables.DayDetailForecast, Integer> getDetailForecastsDao() throws SQLException, java.sql.SQLException {
            if (detailForecastsDao == null) {
                detailForecastsDao = getDao(DbTables.DayDetailForecast.class);
            }
            return detailForecastsDao;
        }

    }
    //--------------------------------------------------------------------------------------------

    public final static class DAO {
        private final @NotNull DbHelper mDbHelper;

        public DAO(@NotNull Context context) {
            mDbHelper = DbHelper.getInstance(context);
        }

        public void saveForecasts(@NotNull City city, @NotNull List<DayForecast> dayForecasts) {
            try {
                final Dao<DbTables.Forecast, Integer> forecastsDao = mDbHelper.getForecastsDao();
                forecastsDao.deleteBuilder().delete();

                for (DayForecast f : dayForecasts) {
                    final ForeignCollection<DbTables.DayDetailForecast> details = forecastsDao.getEmptyForeignCollection(DbTables.Forecast.Column.DETAILS);
                    final DbTables.Forecast forecast = new DbTables.Forecast();
                    forecast.setCity(city.name());
                    forecast.setLocalDate(TimeUtils.getMillis(f.getDate()));
                    forecast.setDetails(details);
                    forecastsDao.createOrUpdate(forecast);

                    for (DetailForecast d : f.getDetail()){
                        final DbTables.DayDetailForecast dayDetailForecast = new DbTables.DayDetailForecast();
                        dayDetailForecast.setForecast(forecast);
                        initDayDetailForecast(dayDetailForecast, d);
                        details.add(dayDetailForecast);
                    }
                }
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }

        public List<DayForecast> getForecasts(@NotNull City city) {
            try {
                final Map<String, Object> select = new HashMap<>();
                select.put(DbTables.Forecast.Column.CITY, city.name());
                final List<DbTables.Forecast> forecasts = mDbHelper.getForecastsDao().queryForFieldValues(select);
                return Lists.newArrayList(Iterables.transform(forecasts, DAO::asDayForecast));

            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        private static void initDayDetailForecast(@NotNull DbTables.DayDetailForecast dayDetailForecast, @NotNull DetailForecast forecast) {
            dayDetailForecast.setDayPeriod(forecast.getDayPeriod().name());
            dayDetailForecast.setTempMin(forecast.getTemperature().getMin());
            dayDetailForecast.setTempMax(forecast.getTemperature().getMax());
            dayDetailForecast.setTempAvg(forecast.getTemperature().getAvg());
            dayDetailForecast.setPressMin(forecast.getPressure().getMin());
            dayDetailForecast.setPressMax(forecast.getPressure().getMax());
            dayDetailForecast.setPressAvg(forecast.getPressure().getAvg());
            dayDetailForecast.setWindMin(forecast.getWind().getSpeed().getMin());
            dayDetailForecast.setWindMax(forecast.getWind().getSpeed().getMax());
            dayDetailForecast.setWindAvg(forecast.getWind().getSpeed().getAvg());
            dayDetailForecast.setWindTitle(forecast.getWind().getDirection().getTitle());
            dayDetailForecast.setWindVal(forecast.getWind().getDirection().getValue());
            dayDetailForecast.setWindTitleLetter(forecast.getWind().getDirection().getTitleLetter());
            dayDetailForecast.setWindTitleShort(forecast.getWind().getDirection().getTitleShort());
            dayDetailForecast.setIconPath(forecast.getIconPath());
            dayDetailForecast.setBigIconPath(forecast.getBigIconPath());
        }

        private static DayForecast asDayForecast(@NotNull DbTables.Forecast forecast) {
            return new DayForecast(
                    TimeUtils.fromMillis(forecast.getLocalDate()),
                    Lists.newArrayList(Iterables.transform(forecast.getDetails(), DAO::asDetailForecast))
            );
        }

        private static DetailForecast asDetailForecast(@NotNull DbTables.DayDetailForecast dayDetailForecast) {
            return new DetailForecast(
                    DayPeriod.valueOf(dayDetailForecast.getDayPeriod()),
                    new Property(dayDetailForecast.getTempMin(), dayDetailForecast.getTempMax(), dayDetailForecast.getTempAvg()),
                    new Property(dayDetailForecast.getPressMin(), dayDetailForecast.getPressMax(), dayDetailForecast.getPressAvg()),
                    new Wind(
                            new Property(dayDetailForecast.getWindMin(), dayDetailForecast.getWindMax(), dayDetailForecast.getWindAvg()),
                            new Direction(dayDetailForecast.getWindTitle(), dayDetailForecast.getWindVal(), dayDetailForecast.getWindTitleLetter(), dayDetailForecast.getWindTitleShort())),
                    dayDetailForecast.getIconPath(),
                    dayDetailForecast.getBigIconPath()
            );
        }

    }
}
