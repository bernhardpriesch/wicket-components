package at.priesch.wicket.components.calendar;

import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.Date;
import java.util.List;

public interface Calendar
{
    Date getStart ();
    
    void setStart (final Date start);
    
    Date getEnd ();
    
    void setEnd (final Date end);
    
    int getRowsToDisplay ();
    
    void setRowsToDisplay (final int rowsToDisplay);
    
    int getColumnsToDisplay ();
    
    void setColumnsToDisplay (final int columnsToDisplay);
    
    boolean isDisplayRowLabels ();
    
    void setDisplayRowLabels (final boolean displayRowLabels);
    
    boolean isDisplayColumnLabels ();
    
    void setDisplayColumnLabels (final boolean displayColumnLables);
    
    List<String> getRowLabels ();
    
    void setRowLabels (final List<String> rowLabels);
    
    List<String> getColumnLabels ();
    
    void setColumnLabels (final List<String> columnLabels);
    
    RepeatingView getXlabels ();
    
    void setXlabels (final RepeatingView xlabels);
    
    RepeatingView getYlabels ();
    
    void setYlabels (final RepeatingView ylabels);
    
    RepeatingView getCalendarParts ();
    
    void setCalendarParts (final RepeatingView calendarParts);
    
    CalendarPart[][] getCalendarEntries ();
    
    void setCalendarEntries (final CalendarPart[][] calendarEntries);
}
