package at.priesch.wicket.components.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

public abstract class AbstractCalendar
    extends Panel
    implements Calendar
{
    private static final long serialVersionUID = -7718976842554794463L;
    
    protected Date                      start                   = null;
    protected Date                      end                     = null;
    protected int                       rowsToDisplay           = 0;   
    protected int                       columnsToDisplay        = 0;    
    protected boolean                   displayRowLabels        = false;    
    protected boolean                   displayColumnLabels     = false;    
    protected List<String>              rowLabels               = new ArrayList<String> ();    
    protected List<String>              columnLabels            = new ArrayList<String> ();   
    protected RepeatingView             xlabels                 = null;    
    protected RepeatingView             ylabels                 = null;    
    protected RepeatingView             calendarParts           = null;    
    protected CalendarPart [][]         calendarEntries         = null;                
    
    public AbstractCalendar (final String id)
    {
        super (id);
    }
    
    public AbstractCalendar (final String id, final Date start, final Date end, final int rowsToDisplay, final int columnsToDisplay, final boolean displayRowLabels, final boolean displayColumnLables, final List<String> rowLabels, final List<String> columnLabels, final CalendarPart [][] calendarEntries)
    {
        super (id);
        this.start                  = start;
        this.end                    = end;
        this.rowsToDisplay          = rowsToDisplay;
        this.columnsToDisplay       = columnsToDisplay;
        this.displayRowLabels       = displayRowLabels;
        this.displayColumnLabels    = displayColumnLables;
        this.rowLabels              = rowLabels;
        this.columnLabels           = columnLabels;
        this.calendarEntries        = calendarEntries;
    }
    
    abstract public void updateCalendarParts ();
    
    abstract public boolean placeCalendarEntry (final CalendarPart calendarPart, final Date entryStartDate, final Date entryEndDate);

    abstract public boolean isEntryStartBeforeNowOrAfterOffset (final int row, final int column, final int offset);
    
    abstract public Date getStartTime (final int row, final int column);
    
    abstract public Date getEndTime (final int row, final int column, final int duration);
    
    @Override
    public boolean getStatelessHint ()
    {
        return true;
    }
    
    @Override
    public Date getStart ()
    {
        return start;
    }

    @Override
    public void setStart (final Date start)
    {
        this.start = start;
    }

    @Override
    public Date getEnd ()
    {
        return end;
    }

    @Override
    public void setEnd (final Date end)
    {
        this.end = end;
    }

    @Override
    public int getRowsToDisplay ()
    {
        return rowsToDisplay;
    }

    @Override
    public void setRowsToDisplay (final int rowsToDisplay)
    {
        this.rowsToDisplay = rowsToDisplay;
    }

    @Override
    public int getColumnsToDisplay ()
    {
        return columnsToDisplay;
    }

    @Override
    public void setColumnsToDisplay (final int columnsToDisplay)
    {
        this.columnsToDisplay = columnsToDisplay;
    }

    @Override
    public boolean isDisplayRowLabels ()
    {
        return displayRowLabels;
    }

    @Override
    public void setDisplayRowLabels (final boolean displayRowLabels)
    {
        this.displayRowLabels = displayRowLabels;
    }

    @Override
    public boolean isDisplayColumnLabels ()
    {
        return displayColumnLabels;
    }

    @Override
    public void setDisplayColumnLabels (final boolean displayColumnLables)
    {
        this.displayColumnLabels = displayColumnLables;
    }

    @Override
    public List<String> getRowLabels ()
    {
        return rowLabels;
    }

    @Override
    public void setRowLabels (final List<String> rowLabels)
    {
        this.rowLabels = rowLabels;
    }

    @Override
    public List<String> getColumnLabels ()
    {
        return columnLabels;
    }

    @Override
    public void setColumnLabels (final List<String> columnLabels)
    {
        this.columnLabels = columnLabels;
    }

    @Override
    public RepeatingView getXlabels ()
    {
        return xlabels;
    }

    @Override
    public void setXlabels (final RepeatingView xlabels)
    {
        this.xlabels = xlabels;
    }

    @Override
    public RepeatingView getYlabels ()
    {
        return ylabels;
    }

    @Override
    public void setYlabels (final RepeatingView ylabels)
    {
        this.ylabels = ylabels;
    }

    @Override
    public RepeatingView getCalendarParts ()
    {
        return calendarParts;
    }

    @Override
    public void setCalendarParts (final RepeatingView calendarParts)
    {
        this.calendarParts = calendarParts;
    }

    @Override
    public CalendarPart[][] getCalendarEntries ()
    {
        return calendarEntries;
    }

    @Override
    public void setCalendarEntries (final CalendarPart[][] calendarEntries)
    {
        this.calendarEntries = calendarEntries;
    }
}
