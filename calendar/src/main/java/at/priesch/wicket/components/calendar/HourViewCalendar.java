package at.priesch.wicket.components.calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HourViewCalendar
    extends AbstractCalendar
{
    /**
     * 
     */
    private static final long   serialVersionUID    = 1L;
    
    private static final Logger logger = LogManager.getLogger (HourViewCalendar.class);
    
    public HourViewCalendar (final String id)
    {
        super (id);
        logger.traceEntry ();
        init ();
        logger.traceExit ();
    }
    
    public HourViewCalendar (final String id, final Date start, final Date end, final int rowsToDisplay, final int columnsToDisplay, final boolean displayRowLabels, final boolean displayColumnLables, final List<String> rowLabels, final List<String> columnLabels, final CalendarPart [][] calendarEntries)
    {
        super (id, start, end, rowsToDisplay, columnsToDisplay, displayRowLabels, displayColumnLables, rowLabels, columnLabels, calendarEntries);
        logger.traceEntry ();
        init ();
        logger.debug ("Created calendar with start[{}] and end[{}]", start, end);
        logger.traceExit ();
    }
    
    private void init ()
    {
        RowCalendarPart row     = null;
        RepeatingView   rowView = null;

        logger.traceEntry ();

        xlabels = new RepeatingView ("xlabel");
        ylabels = new RepeatingView ("ylabel");
        calendarParts = new RepeatingView ("columpart");
        
        if (null == calendarEntries)
        {
            calendarEntries = new CalendarPart [rowsToDisplay][columnsToDisplay];
        }
        
        for (int i = 0; i < columnsToDisplay; i++)
        {
            xlabels.add (new CalendarLabelEntry (String.valueOf (i), columnLabels.size () > (columnsToDisplay -1) ? columnLabels.get (i) : null));
        }
        
        for (int i = 0; i < rowsToDisplay; i++)
        {
            ylabels.add (new CalendarLabelEntry (String.valueOf (i), rowLabels.size () > (rowsToDisplay -1) ? rowLabels.get (i) : null));
        }
        
        for (int i = 0; i < rowsToDisplay; i++)
        {
            row = new RowCalendarPart (String.valueOf (i));
            rowView = new RepeatingView ("rowpart");
            for (int y = 0; y < columnsToDisplay; y++)
            {
                rowView.add (calendarEntries[i][y] != null ? calendarEntries[i][y] : new CalendarPart (String.valueOf (y)));
                logger.trace ("Added CalendarPart [{}] [{}]", String.valueOf (i), String.valueOf (y));
            }
            row.add (rowView);
            calendarParts.add (row);
        }
        
        if(!displayColumnLabels)
        {
            xlabels.setVisible (false);
        }
        if(!displayRowLabels)
        {
            ylabels.setVisible (false);
        }
        add (xlabels);
        add (ylabels);
        add (calendarParts);

        logger.traceExit ();
    }
    
    @Override
    public void updateCalendarParts ()
    {
        RowCalendarPart row     = null;
        RepeatingView   rowView = null;

        logger.traceEntry ();

        for (int i = 0; i < rowsToDisplay; i++)
        {
            row = new RowCalendarPart (String.valueOf (i));
            rowView = new RepeatingView ("rowpart");
            for (int y = 0; y < columnsToDisplay; y++)
            {
                rowView.add (calendarEntries[i][y] != null ? calendarEntries[i][y] : new CalendarPart (String.valueOf (y)));
                logger.trace ("Added CalendarPart [{}] [{}]", String.valueOf (i), String.valueOf (y));
            }
            row.add (rowView);
            calendarParts.replace (row);
        }

        logger.traceExit ();
    }

    @Override
    public boolean placeCalendarEntry (final CalendarPart calendarPart, final Date entryStartDate, final Date entryEndDate)
    {
        GregorianCalendar calendarStart      = null;
        GregorianCalendar calendarEnd        = null;
        GregorianCalendar currentCalendarEnd = null;
        GregorianCalendar entryStart         = null;
        GregorianCalendar entryEnd           = null;
        int               hourOfDayStart     = 0;
        boolean           successful         = false;

        logger.traceEntry ();

        calendarStart = new GregorianCalendar ();
        calendarStart.setTime (start);
        hourOfDayStart = calendarStart.get (Calendar.HOUR_OF_DAY);
        
        calendarEnd = new GregorianCalendar ();
        calendarEnd.setTime (end);

        currentCalendarEnd = new GregorianCalendar ();
        currentCalendarEnd.setTime (start);
        currentCalendarEnd.add (Calendar.HOUR_OF_DAY, 1);
        
        entryStart = new GregorianCalendar ();
        entryStart.setTime (entryStartDate);
        
        entryEnd = new GregorianCalendar ();
        entryEnd.setTime (entryEndDate);
        
        if (entryStart.equals (calendarStart) || (entryStart.after (calendarStart) && entryEnd.before (calendarEnd)))
        {
            for (int i = 0; i < columnsToDisplay; i++)
            {
                for (int y = 0; y < rowsToDisplay; y++)
                {
                    if ((calendarStart.equals (entryStart) || entryStart.after (calendarStart)) && (entryStart.before (currentCalendarEnd)))
                    {
                        calendarEntries[y][i] = calendarPart;
                        successful = true;
                        logger.debug ("Placed calendar entry with start[{}] and end[{}] at column[{}] and row[{}], calendarStart[{}], currentCalendarEnd[{}]", entryStart.getTime ().toString (), entryEnd.getTime ().toString (), i, y, calendarStart.getTime ().toString (), currentCalendarEnd.getTime ().toString ());
                        break;
                    }
                    calendarStart.add (Calendar.HOUR_OF_DAY, 1);
                    if (calendarEnd.get (Calendar.HOUR_OF_DAY) > currentCalendarEnd.get (Calendar.HOUR_OF_DAY))
                    {
                        currentCalendarEnd.add (Calendar.HOUR_OF_DAY, 1);
                    }
                }
                calendarStart.set (Calendar.HOUR_OF_DAY, hourOfDayStart);
                calendarStart.add (Calendar.DATE, 1);
                currentCalendarEnd.set (Calendar.HOUR_OF_DAY, hourOfDayStart);
                currentCalendarEnd.add (Calendar.DATE, 1);
                currentCalendarEnd.add (Calendar.HOUR_OF_DAY, 1);
            }
        }
        
        if (!successful)
        {
            logger.warn ("CalendarEntry with start[{}] and end[{}] is not between calendar start[{}] and end[{}]", entryStart.getTime ().toString (), entryEnd.getTime ().toString (), calendarStart.getTime ().toString (), calendarEnd.getTime ().toString ());
        }

        logger.traceExit ();
        
        return successful;
    }
    
    @Override
    public boolean isEntryStartBeforeNowOrAfterOffset (final int row, final int column, final int offset)
    {
        boolean             retValue        = false;
        GregorianCalendar   entryStart   = null;
        GregorianCalendar   endBoundary     = null;
        Date                now             = null;

        logger.traceEntry ();

        now = new Date (System.currentTimeMillis ());
        
        entryStart = new GregorianCalendar ();
        entryStart.setTime (start);
        entryStart.set (java.util.Calendar.MINUTE, 0);
        entryStart.set (java.util.Calendar.SECOND, 0);
        entryStart.set (java.util.Calendar.MILLISECOND, 0);
        
        entryStart.add (java.util.Calendar.DATE, column);
        entryStart.add (java.util.Calendar.HOUR_OF_DAY, row);
        
        endBoundary = new GregorianCalendar ();
        endBoundary.setTime (now);
        endBoundary.add (java.util.Calendar.HOUR_OF_DAY, offset);
        
        if (entryStart.getTime ().before (now) || entryStart.after (endBoundary))
        {
            retValue = true;
        }
        
        logger.debug ("Returning [{}] for column [{}]; row [{}]; offset [{}]; entryStart [{}]; endBoundary [{}]; now [{}]", retValue, column, row, offset, entryStart.getTime (), endBoundary.getTime (), now);

        logger.traceExit ();
        
        return retValue;
    }
    
    @Override
    public Date getStartTime (final int row, final int column)
    {
        Date                retValue    = null;
        GregorianCalendar   entryStart  = null;

        logger.traceEntry ();

        entryStart = new GregorianCalendar ();
        entryStart.setTime (start);
        entryStart.set (java.util.Calendar.MINUTE, 0);
        entryStart.set (java.util.Calendar.SECOND, 0);
        entryStart.set (java.util.Calendar.MILLISECOND, 0);
        
        entryStart.add (java.util.Calendar.DATE, column);
        entryStart.add (java.util.Calendar.HOUR_OF_DAY, row);
        
        retValue = entryStart.getTime ();
        
        logger.debug ("Returning startTime [{}]", retValue);

        logger.traceExit ();
        
        return retValue;
    }
    
    @Override
    public Date getEndTime (final int row, final int column, final int duration)
    {
        Date                retValue    = null;
        GregorianCalendar   entryEnd    = null;

        logger.traceEntry ();

        entryEnd = new GregorianCalendar ();
        entryEnd.setTime (start);
        entryEnd.set (java.util.Calendar.MINUTE, 0);
        entryEnd.set (java.util.Calendar.SECOND, 0);
        entryEnd.set (java.util.Calendar.MILLISECOND, 0);
        
        entryEnd.add (java.util.Calendar.DATE, column);
        entryEnd.add (java.util.Calendar.HOUR_OF_DAY, row);
        
        entryEnd.add (java.util.Calendar.MINUTE, duration);
        
        retValue = entryEnd.getTime ();
        
        logger.debug ("Returning endTime [{}]", retValue);

        logger.traceExit ();
        
        return retValue;
    }
    
}
