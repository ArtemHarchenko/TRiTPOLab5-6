import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.util.ArrayList;

public class TasksTableModel extends AbstractTableModel {
    private final static int COLUMN_COUNT = 4;

    private ArrayList<String[]> dataArrayList;

    public TasksTableModel() {
        dataArrayList = new ArrayList<String[]>();
        for (int i = 0; i < dataArrayList.size(); i++) {
            dataArrayList.add(new String[(getColumnCount())]);
        }
    }

    public int getRowCount() {
        return dataArrayList.size();
    }

    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = dataArrayList.get(rowIndex);
        return rows[columnIndex];
    }

    public void add(TODOlist list) {
        String[] rowTable = getStrings(list);
        dataArrayList.add(rowTable);
        fireTableDataChanged();
    }

    public void remove(TODOlist list) {
        int index = getRowNumber(list);
        dataArrayList.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public void update(TODOlist list, int index) {
        String[] rowSrc = getStrings(list);
        dataArrayList.set(index, rowSrc);
        fireTableRowsUpdated(index, index);
    }

    public void removeAll() {
        int size = dataArrayList.size();
        if (dataArrayList.size() > 0)
            for (int i = size - 1; i >= 0; i--) {
                dataArrayList.remove(i);
            }
        else return;
    }

    public void ShowData(Connection con, String date) {
        TODOlistDAO dao = new TODOlistDAO(con);
        ArrayList<TODOlist> result = dao.getTaskList(date);
        removeAll();
        for (int i = 0; i < result.size(); i ++)
            add(result.get(i));
        fireTableDataChanged();
    }

    public int getRowNumber(TODOlist list) {
        String[] rowDest;
        String[] rowSrc = getStrings(list);
        int index = 0;
        for (int i = 0; i < dataArrayList.size(); i++) {
            rowDest = dataArrayList.get(i);
            if (rowDest[0].compareTo(rowSrc[0]) == 0 &&
                    rowDest[1].compareTo(rowSrc[1]) == 0 &&
                    rowDest[2].compareTo(rowSrc[2]) == 0 &&
                    rowDest[3].compareTo(rowSrc[3]) == 0) {
                index = i;
                break;
            }
        }
        return index;
    }

    private String[] getStrings(TODOlist list) {
        String[] rowSrc = new String[getColumnCount()];
        rowSrc[0] = list.getTime().toString();
        rowSrc[1] = list.getPlace();
        rowSrc[2] = list.getDefinition();
        rowSrc[3] = list.getImportance();
        return rowSrc;
    }
}

