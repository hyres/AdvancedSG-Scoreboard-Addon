
package scha.efer.sbaddon.boards;

import java.util.List;

public class Board1
{
    private String title;
    private List<String> lines;
    
    public Board1(final String title, final List<String> lines) {
        this.title = title;
        this.lines = lines;
        while (this.lines.size() > 15) {
            this.lines.remove(this.lines.size() - 1);
        }
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public List<String> getLines() {
        return this.lines;
    }
}
