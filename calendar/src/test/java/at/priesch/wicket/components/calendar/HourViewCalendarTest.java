package at.priesch.wicket.components.calendar;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HourViewCalendarTest
    extends WicketTester
{
    private static final Logger logger  = LogManager.getLogger (HourViewCalendarTest.class);
    
    public List<String> rowLables       = new ArrayList<String> ();
    public List<String> columnLables    = new ArrayList<String> ();
    
    public int  rows                    = 15;
    public int  columns                 = 7;
    
    public Date start                   = new Date (System.currentTimeMillis ());
    public Date end                     = new Date (System.currentTimeMillis ());
    
    @Before
    public void setUp ()
    {
        for (int i = 0; i < rows; i++)
        {
            rowLables.add ("Row" + i);
        }
        
        for (int i = 0; i < columns; i++)
        {
            columnLables.add ("Column" + i);
        }
    }
    
    @After
    public void tearDown ()
    {
        
    }

    @Test
    public void testHourViewCalendarInitialization ()
    {
        HourViewCalendar calendar = new HourViewCalendar ("calendar", start, end, rows, columns, true, true, rowLables, columnLables, null);
        
        assertTrue ("Calendar is null!", null != calendar);
    }
    
    @Test
    public void testHourViewCalendarGetterSetter ()
    {
        HourViewCalendar calendar = new HourViewCalendar ("calendar");
        
        calendar.setCalendarParts (new RepeatingView ("parts"));
        calendar.setCalendarEntries (new CalendarPart [columns][rows]);
        calendar.setXlabels (new RepeatingView ("xLabels"));
        calendar.setYlabels (new RepeatingView ("yLabels"));
        calendar.setColumnLabels (columnLables);
        calendar.setRowLabels (rowLables);
        calendar.setColumnsToDisplay (columns -1);
        calendar.setRowsToDisplay (rows -1);
        calendar.setDisplayRowLabels (true);
        calendar.setDisplayColumnLabels (true);
        
        assertTrue ("Calendar is null!", null != calendar);
        assertTrue (calendar.isDisplayColumnLabels () == true);
        assertTrue (calendar.isDisplayRowLabels () == true);      
        assertTrue (calendar.getColumnsToDisplay () == columns -1);
        assertTrue (calendar.getRowsToDisplay () == rows -1);
        assertTrue (calendar.getRowLabels () == rowLables);
        assertTrue (calendar.getColumnLabels () == columnLables);
        assertTrue (calendar.getCalendarParts ().getId ().equals ("parts"));
        assertTrue (calendar.getXlabels ().getId ().equals ("xLabels"));
        assertTrue (calendar.getYlabels ().getId ().equals ("yLabels"));
    }
    
    @Test
    public void testHourViewCalendarRendering ()
    {
        HourViewCalendar calendar = null;
        rowLables.remove (rows -1);
        columnLables.remove (columns -1);
        calendar = new HourViewCalendar ("calendar", start, end, rows, columns, true, true, rowLables, columnLables, null);
        calendar = (HourViewCalendar)this.startComponentInPage (calendar);
        
        assertTrue ("Calendar is null!", null != calendar);   
    }
    
    @Test
    public void testHourViewCalendarPlaceEntry ()
    {
        HourViewCalendar    calendar        = null;
        GregorianCalendar   calendarStart   = null;
        GregorianCalendar   calendarEnd     = null;
        GregorianCalendar   entryStart      = null;
        GregorianCalendar   entryEnd        = null;
        
        rowLables.remove (rows -1);
        columnLables.remove (columns -1);
        
        calendarStart = new GregorianCalendar ();
        calendarStart.setTime (start);
        calendarStart.set (java.util.Calendar.HOUR_OF_DAY, 8);
        calendarStart.set (java.util.Calendar.MINUTE, 0);
        calendarStart.set (java.util.Calendar.SECOND, 0);
        calendarStart.set (java.util.Calendar.MILLISECOND, 0);
        
        calendarEnd = new GregorianCalendar ();
        calendarEnd.setTime (end);
        calendarEnd.set (java.util.Calendar.HOUR_OF_DAY, 22);
        calendarEnd.set (java.util.Calendar.MINUTE, 0);
        calendarEnd.set (java.util.Calendar.SECOND, 0);
        calendarEnd.set (java.util.Calendar.MILLISECOND, 0);
        
        if (calendarStart.get (java.util.Calendar.DAY_OF_WEEK) == 1)
        {
            calendarStart.add (java.util.Calendar.DATE, -6);
            calendarEnd.add (java.util.Calendar.DATE, 0);
        }
        else
        {
            calendarStart.add (java.util.Calendar.DATE, ((calendarStart.get (java.util.Calendar.DAY_OF_WEEK) - 2) * -1));
            calendarEnd.add (java.util.Calendar.DATE, 7 - ((calendarEnd.get (java.util.Calendar.DAY_OF_WEEK) - 1)));
        }
        
        entryStart = new GregorianCalendar ();
        entryStart.set (calendarStart.get (java.util.Calendar.YEAR), calendarStart.get (java.util.Calendar.MONTH), calendarStart.get (java.util.Calendar.DATE) + 3, 12, 0, 59);
        entryStart.set (java.util.Calendar.MINUTE, 0);
        entryStart.set (java.util.Calendar.SECOND, 59);
        entryStart.set (java.util.Calendar.MILLISECOND, 0);
        
        entryEnd = new GregorianCalendar ();
        entryEnd.set (calendarStart.get (java.util.Calendar.YEAR), calendarStart.get (java.util.Calendar.MONTH), calendarStart.get (java.util.Calendar.DATE) + 3, 12, 0, 59);
        entryEnd.set (java.util.Calendar.MINUTE, 0);
        entryEnd.set (java.util.Calendar.SECOND, 59);
        entryEnd.set (java.util.Calendar.MILLISECOND, 0);
        
        
        calendar = new HourViewCalendar ("calendar", calendarStart.getTime (), calendarEnd.getTime (), rows, columns, true, true, rowLables, columnLables, null);
                
        assertTrue (calendar.placeCalendarEntry (new CalendarPart ("part"), entryStart.getTime (), entryEnd.getTime ()));
        
        calendar = (HourViewCalendar)this.startComponentInPage (calendar);
        
        assertTrue ("Calendar is null!", null != calendar);      
    }
    
    @Test
    public void testHourViewCalendarEntryStartBeforeNowAndAfterOffset ()
    {
        HourViewCalendar    calendar        = null;
        GregorianCalendar   calendarStart   = null;
        GregorianCalendar   calendarEnd     = null;
        
        rowLables.remove (rows -1);
        columnLables.remove (columns -1);
        
        calendarStart = new GregorianCalendar ();
        calendarStart.setTime (start);
        calendarStart.set (java.util.Calendar.HOUR_OF_DAY, 8);
        calendarStart.set (java.util.Calendar.MINUTE, 0);
        calendarStart.set (java.util.Calendar.SECOND, 0);
        calendarStart.set (java.util.Calendar.MILLISECOND, 0);
        
        calendarEnd = new GregorianCalendar ();
        calendarEnd.setTime (end);
        calendarEnd.set (java.util.Calendar.HOUR_OF_DAY, 22);
        calendarEnd.set (java.util.Calendar.MINUTE, 0);
        calendarEnd.set (java.util.Calendar.SECOND, 0);
        calendarEnd.set (java.util.Calendar.MILLISECOND, 0);
        
        if (calendarStart.get (java.util.Calendar.DAY_OF_WEEK) == 1)
        {
            calendarStart.add (java.util.Calendar.DATE, -6);
            calendarEnd.add (java.util.Calendar.DATE, 0);
        }
        else
        {
            calendarStart.add (java.util.Calendar.DATE, ((calendarStart.get (java.util.Calendar.DAY_OF_WEEK) - 2) * -1));
            calendarEnd.add (java.util.Calendar.DATE, 7 - ((calendarEnd.get (java.util.Calendar.DAY_OF_WEEK) - 1)));
        }
        
        calendar = new HourViewCalendar ("calendar", calendarStart.getTime (), calendarEnd.getTime (), rows, columns, true, true, rowLables, columnLables, null);
        
        logger.trace (calendar.isEntryStartBeforeNowOrAfterOffset (6, 2, 24));
    }
    
    @Test
    public void testHourViewCalendarGetStartTime ()
    {
        HourViewCalendar    calendar        = null;
        GregorianCalendar   calendarStart   = null;
        GregorianCalendar   calendarEnd     = null;
        
        rowLables.remove (rows -1);
        columnLables.remove (columns -1);
        
        calendarStart = new GregorianCalendar ();
        calendarStart.setTime (start);
        calendarStart.set (java.util.Calendar.HOUR_OF_DAY, 8);
        calendarStart.set (java.util.Calendar.MINUTE, 0);
        calendarStart.set (java.util.Calendar.SECOND, 0);
        calendarStart.set (java.util.Calendar.MILLISECOND, 0);
        
        calendarEnd = new GregorianCalendar ();
        calendarEnd.setTime (end);
        calendarEnd.set (java.util.Calendar.HOUR_OF_DAY, 22);
        calendarEnd.set (java.util.Calendar.MINUTE, 0);
        calendarEnd.set (java.util.Calendar.SECOND, 0);
        calendarEnd.set (java.util.Calendar.MILLISECOND, 0);
        
        if (calendarStart.get (java.util.Calendar.DAY_OF_WEEK) == 1)
        {
            calendarStart.add (java.util.Calendar.DATE, -6);
            calendarEnd.add (java.util.Calendar.DATE, 0);
        }
        else
        {
            calendarStart.add (java.util.Calendar.DATE, ((calendarStart.get (java.util.Calendar.DAY_OF_WEEK) - 2) * -1));
            calendarEnd.add (java.util.Calendar.DATE, 7 - ((calendarEnd.get (java.util.Calendar.DAY_OF_WEEK) - 1)));
        }
        
        calendar = new HourViewCalendar ("calendar", calendarStart.getTime (), calendarEnd.getTime (), rows, columns, true, true, rowLables, columnLables, null);
        
        logger.trace (calendar.getStartTime (6, 2));
    }
}
