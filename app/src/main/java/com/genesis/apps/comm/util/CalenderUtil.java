/*
 * Copyright 2015 Vikram Kakkar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.genesis.apps.comm.util;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appeaser.sublimepickerlibrary.SublimePicker;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.genesis.apps.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CalenderUtil extends DialogFragment {
    // Date & Time formatter used for formatting
    // text on the switcher button
    DateFormat mDateFormatter, mTimeFormatter;

    // Picker
    SublimePicker mSublimePicker;

    // Callback to activity
    Callback mCallback;

    SublimeListenerAdapter mListener = new SublimeListenerAdapter() {
        @Override
        public void onCancelled() {
            if (mCallback != null) {
                mCallback.onCancelled();
            }

            // Should actually be called by activity inside `Callback.onCancelled()`
            dismiss();
        }

        @Override
        public void onDateTimeRecurrenceSet(SublimePicker sublimeMaterialPicker,
                                            SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {
            if (mCallback != null) {
                mCallback.onDateTimeRecurrenceSet(selectedDate,
                        hourOfDay, minute, recurrenceOption, recurrenceRule);
            }

            // Should actually be called by activity inside `Callback.onCancelled()`
            dismiss();
        }

        // You can also override 'formatDate(Date)' & 'formatTime(Date)'
        // to supply custom formatters.
    };

    public CalenderUtil() {
        // Initialize formatters
        mDateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        mTimeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
        mTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT+9"));
    }

    // Set activity callback
    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*try {
            //getActivity().getLayoutInflater()
                    //.inflate(R.layout.sublime_recurrence_picker, new FrameLayout(getActivity()), true);
            getActivity().getLayoutInflater()
                    .inflate(R.layout.sublime_date_picker, new FrameLayout(getActivity()), true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }*/

        mSublimePicker = (SublimePicker) getActivity()
                .getLayoutInflater().inflate(R.layout.sublime_picker, container);

        // Retrieve SublimeOptions
        Bundle arguments = getArguments();
        SublimeOptions options = null;

        // Options can be null, in which case, default
        // options are used.
        if (arguments != null) {
            options = arguments.getParcelable("SUBLIME_OPTIONS");
        }

        mSublimePicker.initializePicker(options, mListener);
        return mSublimePicker;
    }

    // For communicating with the activity
    public interface Callback {
        void onCancelled();

        void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                     int hourOfDay, int minute,
                                     SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                     String recurrenceRule);
    }


    public Pair<Boolean, SublimeOptions> getOptions(int type,boolean isRange, long limitStartDate, long limitEndDate, Calendar baseDate) {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;
        displayOptions |= type;
        switch (type){
            case SublimeOptions.ACTIVATE_DATE_PICKER:
                options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
                break;
            case SublimeOptions.ACTIVATE_TIME_PICKER:
                options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
                break;
            case SublimeOptions.ACTIVATE_RECURRENCE_PICKER:
                options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
                break;
        }
        options.setDisplayOptions(displayOptions);

        // Enable/disable the date range selection feature
        options.setCanPickDateRange(isRange);

        options.setDateRange(limitStartDate, limitEndDate);

        options.setAnimateLayoutChanges(true);
        if(baseDate!=null) {
            options.setDateParams(baseDate);
            options.setTimeParams(baseDate.get(Calendar.HOUR_OF_DAY), baseDate.get(Calendar.MINUTE), true);
        }
        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);

        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }


    public static long getDateMils(int day){
        long mils=-1L;

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        if(day!=0){
            calendar.add(Calendar.DAY_OF_MONTH, day);
        }
        mils = calendar.getTimeInMillis();

        return mils;
    }

}
