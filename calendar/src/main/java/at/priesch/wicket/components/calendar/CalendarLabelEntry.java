package at.priesch.wicket.components.calendar;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class CalendarLabelEntry
    extends Panel
{
    private static final long serialVersionUID = -5218669507580713836L;

    public CalendarLabelEntry (final String id, final String text)
    {
        super (id);
        add (new Label ("text", text));   
    }

}
