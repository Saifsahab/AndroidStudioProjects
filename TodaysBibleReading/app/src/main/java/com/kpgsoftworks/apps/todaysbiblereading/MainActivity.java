package com.kpgsoftworks.apps.todaysbiblereading;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "tbr.MainActivity";
    private static final String READING_MSG1 = "Read 3 Chapters daily Monday - Friday";
    private static final String READING_MSG2 = "Read 4 Chapters daily Saturday - Sunday";
    private static final String SPACE = " ";
    private static final String DELIMITER = "/";

    private static final int TOTAL_CHAPTERS = 1189;
    private static final int TOTAL_BOOKS = 66;

    // Initialize array of Bible Books
    private static final String[] BIBLE_BOOKS = new String[]{
            "Genesis", "Exodus", "Leviticus",
            "Numbers", "Deuteronomy", "Joshua",
            "Judges", "Ruth", "1 Samuel",
            "2 Samuel", "1 Kings", "2 Kings",
            "1 Chronicles", "2 Chronicles", "Ezra",
            "Nehemiah", "Esther", "Job",
            "Psalm", "Proverbs", "Ecclesiastes",
            "Song of Solomon", "Isaiah", "Jeremiah",
            "Lamentations", "Ezekiel", "Daniel",
            "Hosea", "Joel", "Amos",
            "Obadiah", "Jonah", "Micah",
            "Nahum", "Habakkuk", "Zephaniah",
            "Haggai", "Zechariah", "Malachi",
            "Matthew", "Mark", "Luke",
            "John", "Acts", "Romans",
            "1 Corinthians", "2 Corinthians", "Galatians",
            "Ephesians", "Philippians", "Colossians",
            "1 Thessalonians", "2 Thessalonians", "1 Timothy",
            "2 Timothy", "Titus", "Philemon",
            "Hebrews", "James", "1 Peter",
            "2 Peter", "1 John", "2 John",
            "3 John", "Jude", "Revelation"
    };

    // Initialize array of count of Chapters per Bible Book
    private static final int[] BIBLE_CHAPTERS = new int[]{
            50, 40, 27,
            36, 34, 24,
            21, 4, 31,
            24, 22, 25,
            29, 36, 10,
            13, 10, 42,
            150, 31, 12,
            8, 66, 52,
            5, 48, 12,
            14, 3, 9,
            1, 4, 7,
            3, 3, 3,
            2, 14, 4,
            28, 16, 24,
            21, 28, 16,
            16, 13, 6,
            6, 4, 4,
            5, 3, 6,
            4, 3, 1,
            13, 5, 5,
            3, 5, 1,
            1, 1, 22
    };

    private static final Random randomGenerator = new Random();

    private static Calendar today;
    private static Calendar BOY; // Beginning Of Year
    private static Calendar selectedDate;

    private static Calendar EOY; // End Of Year

    private static TextView readingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.tbr_launcher));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openCalendar(view);
                    Snackbar.make(view, getString(R.string.app_name_long), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        LinearLayout viewResult = (LinearLayout )findViewById(R.id.view_result);
        if (viewResult != null) {
            viewResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openCalendar(view);
                    Snackbar.make(view, getString(R.string.app_name_long), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        readingTextView = (TextView) findViewById(R.id.reading);

        // Initialize today's date, first day of January, and last day of December
        today = Calendar.getInstance();
        BOY = (Calendar) today.clone();
        EOY = (Calendar) today.clone();
        selectedDate = (Calendar) today.clone();
        BOY.set(BOY.get(Calendar.YEAR), 0, 1);
        EOY.set(EOY.get(Calendar.YEAR), 12 - 1, 31);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        int year = today.get(Calendar.YEAR);
        int month = Integer.parseInt(v.getTag().toString());
        int day = today.get(Calendar.DAY_OF_MONTH);
        Calendar calendar = (Calendar) today.clone();
        calendar.set(year, month, 1);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day > days) {
            day = days;
        }
        DatePickerDialog dialog = new DatePickerDialog(
                MainActivity.this,
                R.style.MyDatePickerDialogTheme,
                new mDateSetListener(),
                year,
                month,
                day
        );
        dialog.show();
    }

    public void openCalendar(View v) {
        getTodaysChapter(today);
    }

    private void logDebug(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, msg);
        }
    }

    private String toDateString(Calendar inputDate) {
        return inputDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) +
                SPACE +
                (inputDate.get(Calendar.MONTH) + 1) + DELIMITER +
                inputDate.get(Calendar.DAY_OF_MONTH) + DELIMITER +
                inputDate.get(Calendar.YEAR);
    }

    private void setReading(String output) {
        logDebug("Reading:\n" + output);
        readingTextView.setText(output);
    }

    /**
     * Outputs today's reading assignment of the Bible based on reading:
     *   - 3 chapters a day Monday through Friday
     *   - 4 chapters a day Saturday through Sunday
     *
     * @param date Selected Date
     */
    private void getTodaysChapter(Calendar date) {
        String strToday;
        int day = date.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
            case Calendar.SATURDAY:
                strToday = "Read 4 chapters:\n";
                break;
            default:
                strToday = "Read 3 chapters:\n";
        }

        String strPace = READING_MSG1 + "\n" + READING_MSG2 + "\n" + "\n" + toDateString(date) + "\n" + "\n";
        // Get starting chapter
        String strChapter = strPace + strToday + getChapter(date);

        if (date.get(Calendar.DAY_OF_YEAR) == EOY.get(Calendar.DAY_OF_YEAR)) {
            setReading(strChapter.replace("chapters:\n", "chapters starting from "));
        } else {
            String strChapter2 = getChapter(date, true);

            if (!strChapter2.isEmpty()) {
                setReading(strChapter + " to " + strChapter2);
            } else {
                // Handle end of Bible
                int chapter = Integer.parseInt(strChapter.substring(strChapter.lastIndexOf(SPACE) + 1));
                if ((chapter >= 20) && (day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
                    switch (chapter) {
                        case 20:
                            setReading(strChapter.replace("4 chapters:\n", "3 chapters starting from "));
                            break;
                        case 21:
                            setReading(strChapter.replace("4 chapters:\n", "2 chapters starting from "));
                            break;
                        default:
                            setReading(strChapter.replace("4 chapters:\n", "1 chapter starting from "));
                    }
                } else if ((chapter >= 21) && (day != Calendar.SUNDAY && day != Calendar.SATURDAY)) {
                    switch (chapter) {
                        case 21:
                            setReading(strChapter.replace("3 chapters:\n", "2 chapters starting from "));
                            break;
                        default:
                            setReading(strChapter.replace("3 chapters:\n", "1 chapter starting from "));
                    }
                } else {
                    setReading(strChapter.replace("chapters:\n", "chapters starting from "));
                }
            }
        }
    }

    /**
     * Returns Bible chapter based on input date:
     * - 3 chapters a day Monday through Friday
     * - 4 chapters a day Saturday through Sunday
     *
     * @param inputDate Date to calculate reading
     * @return Assigned chapter to read
     */
    private String getChapter(Calendar inputDate) {
        return getChapter(inputDate, false);
    }

    /**
     * Returns Bible chapter based on input date:
     * - 3 chapters a day Monday through Friday
     * - 4 chapters a day Saturday through Sunday
     *
     * @param inputDate Date to calculate reading
     * @param secondCall true if second call for ending chapter
     * @return Assigned chapter to read
     */
    private String getChapter(Calendar inputDate, boolean secondCall) {
        int day = inputDate.get(Calendar.DAY_OF_WEEK);
        int intChapter;
        if (inputDate.get(Calendar.DAY_OF_YEAR) == 1) {
            intChapter = 1;
        } else {
            // Calculate yesterday's ending chapter
            Calendar yesterdaysDate = (Calendar) inputDate.clone();
            yesterdaysDate.add(Calendar.DAY_OF_YEAR, -1);
            intChapter = (yesterdaysDate.get(Calendar.DAY_OF_YEAR) * 3) + countSatSun(yesterdaysDate, BOY);
            // Add one for Saturday if today is Sunday or Add one for Sunday if today is Monday
            if (day == Calendar.SUNDAY || day == Calendar.MONDAY) {
                intChapter++;
            }
            // Increment yesterday's ending chapter by 1
            intChapter++;
        }

        if (secondCall) {
            // Add one for Sunday or Saturday
            if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
                intChapter += 3;
            } else {
                intChapter += 2;
            }
        }

        String strChapter = "";
        if (intChapter <= TOTAL_CHAPTERS) {
            int intChapterSum = 0;
            int count = BIBLE_CHAPTERS.length - 1;
            for (int i = 0; i <= count; i++) {
                if (intChapter <= BIBLE_CHAPTERS[i]) {
                    strChapter = BIBLE_BOOKS[i] + SPACE + intChapter;
                    break;
                } else {
                    if (intChapter < (intChapterSum + BIBLE_CHAPTERS[i])) {
                        strChapter = BIBLE_BOOKS[i] + SPACE + (intChapter - intChapterSum);
                        break;
                    } else if (intChapter > (intChapterSum + BIBLE_CHAPTERS[i])) {
                        intChapterSum = intChapterSum + BIBLE_CHAPTERS[i];
                    } else {
                        strChapter = BIBLE_BOOKS[i] + SPACE + BIBLE_CHAPTERS[i];
                        break;
                    }
                }
            }
        } else {
            if (!secondCall) {
                int book = randomGenerator.nextInt(TOTAL_BOOKS);
                int chapter = randomGenerator.nextInt(BIBLE_CHAPTERS[book]) + 1;
                // Ensure no chapters after Revelation 19
                while (book == 65 && chapter > 19) {
                    chapter = randomGenerator.nextInt(BIBLE_CHAPTERS[book]) + 1;
                    logDebug("getChapter Revelation: " + chapter);
                }
                strChapter = BIBLE_BOOKS[book] + SPACE + chapter;
            }
        }
        logDebug("getChapter: " + strChapter);
        return strChapter;
    }

    /**
     * Count the number of Saturdays and Sundays between start and end dates
     *
     * @param endDate Date of end of interval
     * @param startDate Date of start of interval
     * @return Count of Saturdays and Sundays
     */
    private int countSatSun(Calendar endDate, Calendar startDate) {
        int endDOY = endDate.get(Calendar.DAY_OF_YEAR);
        int startDOY = startDate.get(Calendar.DAY_OF_YEAR);

        if (endDOY < startDOY) {
            return 0;        // avoid infinite loop
        }

        int count = 0;

        Calendar date = (Calendar) startDate.clone();
        for (int i = startDOY; i < endDOY; i++) {
            int day = date.get(Calendar.DAY_OF_WEEK);
            if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
                count++;
            }
            date.add(Calendar.DAY_OF_WEEK, 1);
        }
        logDebug("countSatSun: " + count);
        return count;
    }

    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            selectedDate.set(year, monthOfYear, dayOfMonth);
            logDebug("Selected Date: " + toDateString(selectedDate));
            getTodaysChapter(selectedDate);
        }
    }
}
