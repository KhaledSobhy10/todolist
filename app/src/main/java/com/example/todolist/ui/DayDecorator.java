package com.example.todolist.ui;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;

public class DayDecorator implements DayViewDecorator {
    private final int color;
    private final HashSet<CalendarDay> calendarDays;
    public DayDecorator(int color, HashSet<CalendarDay> calendarDays) {
        this.color = color;
        this.calendarDays = calendarDays;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return calendarDays.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        // You can set background for Decorator via drawable here
//        view.setBackgroundDrawable(drawable);
        view.addSpan(new DotSpan(20,color));

    }}
