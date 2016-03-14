package logbook.internal.gui;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * TableViewに関係するメソッドを集めたクラス
 *
 */
public class TableTool {

    private static final String SEPARATOR = "\t"; //$NON-NLS-1$

    private static final String NL = "\n"; //$NON-NLS-1$

    /**
     * 行をヘッダ付きで文字列にします
     *
     * @param <T> -
     * @param table テーブル
     * @param rows 行
     * @return ヘッダ付きの文字列
     */
    public static <T> String toString(TableView<T> table, List<T> rows) {
        StringJoiner joiner = new StringJoiner(NL);
        String header = table.getColumns()
                .stream()
                .map(TableColumn::getText)
                .collect(Collectors.joining(SEPARATOR));
        String body = rows.stream()
                .map(Object::toString)
                .collect(Collectors.joining(NL));
        return joiner.add(header)
                .add(body)
                .toString();
    }

    /**
     * 選択行をヘッダ付きで文字列にします
     *
     * @param <T> -
     * @param table テーブル
     * @return ヘッダ付きの文字列
     */
    public static <T> String selectionToString(TableView<T> table) {
        return toString(table, table.getSelectionModel()
                .getSelectedItems());
    }

    /**
     * キーボードイベントのハンドラー(Ctrl+AとCtrl+Cを実装)
     *
     * @param event キーボードイベント
     */
    public static void defaultOnKeyPressedHandler(KeyEvent event) {
        if (event.getSource() instanceof TableView<?>) {
            TableView<?> table = (TableView<?>) event.getSource();

            // Selection All
            if (event.isControlDown() && event.getCode() == KeyCode.A) {
                table.getSelectionModel()
                        .selectAll();
            }
            // Copy
            if (event.isControlDown() && event.getCode() == KeyCode.C) {
                ClipboardContent content = new ClipboardContent();
                content.putString(selectionToString(table));
                Clipboard.getSystemClipboard()
                        .setContent(content);
            }
        }
    }
}